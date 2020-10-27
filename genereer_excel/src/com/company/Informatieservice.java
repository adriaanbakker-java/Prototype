package com.company;

import java.util.ArrayList;

public class Informatieservice {

    int Servicenr;
    ArrayList<String> AQlijst = new ArrayList<>();
    ArrayList<String> Berichtenlijst = new ArrayList<>();

    public void addBericht(String bericht) {
        boolean aanwezig = false;
        for (String b: Berichtenlijst) {
            if (b.equals(bericht))
                aanwezig = true;
        }
        if (!aanwezig)
            Berichtenlijst.add(bericht);
    }



    public Informatieservice(int servicenr, String aAQstring) throws Exception {
        Servicenr = servicenr;
        aAQstring = aAQstring.trim();
        // remove " quotes
        aAQstring = aAQstring.replace("\"", "");
        // split on comma or space
        String[] elements = aAQstring.split("[, ]");

        for (int i = 0; i <= elements.length - 1; i++) {
            String next = elements[i].trim();
            if (next.startsWith("A")) {
                AQlijst.add(elements[i].trim());
            }
        }
    }


    public static int converteerNaarVolgnummer(String element) {
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
        int iServicenr = converteerNaarVolgnummer(elements[0]);
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

    private static boolean isInLijst(String sInput, ArrayList<String> stringlijst) {
        for (String naam: stringlijst) {
            if (naam.equals(sInput))
                return true;
        }
        return false;
    }

    // gelezen AQs is lijst van uit bestand gelezen AQ objecten
    // Doorloop deze lijst en voor iedere AQ die voorkomt bij deze service
    // de berichten toevoegen aan deze informatieservice
    public void vulBerichten(ArrayList<String> aBerichten) {
        for (String b: aBerichten) {
            b = b.trim();
            if (!isInLijst(b, Berichtenlijst)) {
                Berichtenlijst.add(b);
            }
        }
    } // vulBerichten

    public String printMetBerichten() {
        String result = "informatieservice;" +  Servicenr + ";";
        String bs = "";
        for (int i = 1; i <= Berichtenlijst.size(); i++) {
            bs +=  Berichtenlijst.get(i-1);
            if (i < Berichtenlijst.size())
                bs += ";";
        }
        return result + bs;
    }
}
