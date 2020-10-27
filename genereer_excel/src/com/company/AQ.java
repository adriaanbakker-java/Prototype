package com.company;

import java.util.ArrayList;

import static com.company.Informatieservice.converteerNaarVolgnummer;

public class AQ {
    int volgnr;
    ArrayList<String> berichten = new ArrayList<>();

    public AQ(int volgnr, ArrayList<String> berichten) {
        this.volgnr = volgnr;
        this.berichten = berichten;
    }

    public String geefNaam() {
        String result = "AQ" + volgnr;
        return result.trim();
    }



    // Genereer een AQ object vanuit het invoerbestand
    // invoerregels bevat puntkomma gescheiden de elementen van 1 AQ
    public AQ(String invoerregels) {
        String[] elements = invoerregels.split(";");
        volgnr = converteerNaarVolgnummer(elements[0]);
        if ((volgnr > 0) && (elements.length >= 4)) { // AQ gevonden, verwerk tot AQ

            String sBerichten = elements[3];

            sBerichten.replaceAll("\\&", " ");
            sBerichten.replaceAll("\\*", " ");
            String[] berichtElements = sBerichten.split(" ");
            for (int i = 1; i <= berichtElements.length; i++) {
                berichten.add(berichtElements[i-1]);
            }
        }
    }

    public String print() {
        String sB = "";
        int i=1;
        for (String s: berichten) {
            sB +=   s;
            i++;
            if (i < berichten.size())
                sB += ",";
        }
        return " AQ:" + volgnr + " berichten:" + sB;
    }
}
