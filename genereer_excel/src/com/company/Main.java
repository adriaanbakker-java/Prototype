package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import static com.company.Informatieservice.converteerNaarVolgnummer;

public class Main {
    final int NRCOLS = 4;  // nr columns in gegevens_per_aq.csv
    // voorbeeld: 1;AQ;Geboortedat;BRPDossierPersoonUWV/v0200;* Aangeleverde peildatum
    String globalLine = "";
    ArrayList<Informatieservice> informatieservices = new ArrayList<>();
    AQLijst aqlijst = new AQLijst();

    public static void main(String[] args) {
	// write your code here
        System.out.println("hello world");
        Main main = new Main();
        main.leesEnVerwerkInformatieservices();
        main.leesEnVerwerkGegevensPerAQ();
        main.invullenBerichten();
        main.printBerichtenPerIS();
    }

    private void printBerichtenPerIS() {
        for (Informatieservice is:informatieservices) {
            System.out.println(is.printMetBerichten());
        }
    }

    private void invullenBerichten() {
        for (Informatieservice is: informatieservices) {
            ArrayList<String> sAqs = is.AQlijst;
            for (String sAq: sAqs) {
                if (!aqlijst.hasAQ(sAq)) {
                    System.out.println("Bij IS " + is.Servicenr + " AQ niet in bestand gevonden:" + sAq);
                } else {
                    AQ aq = aqlijst.geefAQ(sAq);
                    ArrayList<String> sBerichtenlijst = aq.berichten;
                   is.vulBerichten(sBerichtenlijst);
                }
            }
        }
    }


    private String leesTotNdePuntkomma(BufferedReader br, int n) throws Exception {
        String lines = "";
        String regel = "";

        boolean done = false;
        while (!done) {
            boolean eof = ((regel = br.readLine()) == null);
            if (eof) {
                if (!lines.equals(""))
                    throw new Exception("onverwachte eind bestand");
                done = true;
            }
            // lege regels en regels beginnend met ; negeren
            regel = regel.trim();
//            if (regel.equals(""))
//                continue;
//            if (regel.startsWith(";"))
//                continue;


            // tel aantal ";"
            String uitkomst = lines + regel;
            int countSeparators = uitkomst.length() - uitkomst.replaceAll(";", "").length();
            /*if (countSeparators > n) {
                System.out.println(uitkomst);
                throw new Exception("teveel ; scheidingstekens op de regel:" + countSeparators);
            }
*/
            lines = uitkomst;
            done = (countSeparators >= n);
        }
        return lines;
    }



    private void leesEnVerwerkGegevensPerAQ() {
        try {
                String filename = "./gegevens_per_aq.csv";
                File myFile = new File(filename);
                if (!myFile.exists()) {
                    throw new Exception("bestand niet gevonden:" + filename);
                } else {
                    System.out.println("file exists");
                    InputStream inputStream = new FileInputStream(myFile);
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                        String lines = leesTotNdePuntkomma(br, NRCOLS);
                        System.out.println("header:" + lines);

                        do {
                            lines = leesTotNdePuntkomma(br, NRCOLS);
                            //System.out.println("Gelezen:" + lines);
                            VerwerkAQ(lines);
                        } while (!lines.equals(""));

                    } catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                }
            } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }



    // Het bestand van gegevens per AQ bevat telkens per AQ het AQ nummer
    // en de berichtnamen (meestal 1 of 2) bij de AQ
    // Het bestand van de informatieservices geeft de AQ's per informatieservice-nummer
    // We willen natuurlijk de berichten die per informatieservice nodig zijn
    private void VerwerkAQ(String lines) {
        String[] elements = lines.split(";");
        if (elements.length < 2) // ivm nr en woordje AQ
            return;
        if (elements[0].equals(""))
            return;
        if (!elements[1].equals("AQ"))
            return;

        int volgnr = converteerNaarVolgnummer(elements[0]);
        if (volgnr > 0) { // AQ gevonden, verwerk tot AQ
            AQ aq = new AQ(lines);
            if (aq.volgnr > 0) {
                System.out.println(aq.print());
                aqlijst.addAQ(aq.geefNaam(), aq);
                if (aq.volgnr == 302) {
                    System.out.println(aq.volgnr);
                }
            }
        }
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
                           if (is != null) {
                               System.out.println(is.print());
                               informatieservices.add(is);
                           }
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
            found = (converteerNaarVolgnummer(elements[0]) > 0); // is alweer de volgende service
            if (eersteKeer && found) {
                found = false;
                eersteKeer = false;
            }
            if (!found) {
                if (result.equals("")) {
                    result += line;
                } else {
                    result += " " + line;
                }
            } else {
                globalLine = line;
            }
        } while (!found);
        return result;
    }

}
