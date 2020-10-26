package com.company;

import java.io.*;
import java.util.ServiceConfigurationError;

import static com.company.Informatieservice.isServiceNr;

public class Main {
    String globalLine = "";

    public static void main(String[] args) {
	// write your code here
        System.out.println("hello world");
        Main main = new Main();
        main.leesEnVerwerkInformatieservices();
    }





    private  void leesEnVerwerkInformatieservices() {
        try {
            String filename = "./informatieservices_aqs.csv";
            File myFile = new File(filename);
            if (!myFile.exists()) {
                throw new Exception("bestand niet gevonden:" + filename);
            } else {
                System.out.println("file exists");

                InputStream inputStream = new FileInputStream(myFile);
                try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                    String regel;
                    boolean eof = ((regel = br.readLine()) == null);
                    if (eof) {
                        throw new Exception("onverwachte eind bestand");
                    }
                    int iServicenr = -1;
                    globalLine = "";
                    Informatieservice is = null;
                    boolean eersteKeer = true;
                    do {
                       String s = leesTotVolgendeService(br, eersteKeer);
                       if (s.trim().length() == 0) {
                           is = null;
                       } else {
                           eersteKeer = false;
                           is = Informatieservice.leesInformatieService(s);
                           if (is != null)
                               System.out.println(is.print());
                       }
                    } while (is != null);
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }  catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private  String leesTotVolgendeService(BufferedReader br, boolean eersteKeer) throws Exception {
        boolean eof = false;
        boolean found = false;
        String line = "";
        String result = globalLine;
        do {
            eof = ((line = br.readLine()) == null);
            if (eof) {
                globalLine = "";
                return result;
            }
            String[] elements = line.split(";");
            if (elements.length < 1) {
                throw new Exception("elementen verwacht, geen lege regel");
            }
            found = (isServiceNr(elements[0]) > 0); // is alweer de volgende service
            if (eersteKeer && found) {
                found = false;
                eersteKeer = false;
            }
            if (!found) {
                result += line;
            } else {
                globalLine = line;
            }
        } while (!found);
        return result;
    }

}
