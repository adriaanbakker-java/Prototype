package com.company;

import java.util.ArrayList;

public class Informatieservice {

    int Servicenr;
    ArrayList<String> AQlijst = new ArrayList<>();

    public Informatieservice(int servicenr, String aAQstring) throws Exception {
        Servicenr = servicenr;
        aAQstring = aAQstring.trim();
        aAQstring = aAQstring.replace("\"", "");
        String[] elements = aAQstring.split(",");

        for (int i = 0; i <= elements.length - 1; i++) {
            String next = elements[i].trim();
            if (next.startsWith("A")) {
                AQlijst.add(elements[i]);
            }
        }
    }


    public static int isServiceNr(String element) {
        try {
            int result = Integer.parseInt(element);
            if (result > 0)
                return result;
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    public static Informatieservice leesInformatieService(String line) throws Exception {
        String[] elements = line.split(";");
        int len = elements.length;
        //System.out.println("Gelezen:" + line);
        //System.out.println("aantal kolommen:" + len);
        if (len < 3)
            throw new Exception("Verwacht aantal kolommen 3, is gelijk aan:" + len);
        int iServicenr = isServiceNr(elements[0]);
        if (iServicenr < 0) {
            throw new Exception("service nr verwacht, in plaats daarvan gelezen:" + elements[0]);
        }
        Informatieservice is = new Informatieservice(iServicenr, elements[2]);
        return is;
    }

    public String print() {
        String result = "informatieservice nr:" + Servicenr + " bevat AQ's:";
        String aqs = "";
        for (int i = 1; i <= AQlijst.size(); i++)
            aqs +=  " " + AQlijst.get(i-1);
        return result + aqs;
    }
}
