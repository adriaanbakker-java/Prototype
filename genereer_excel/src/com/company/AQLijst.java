package com.company;

import java.util.HashMap;
import java.util.Set;

public class AQLijst {
    HashMap<String, AQ> AQlijst = new HashMap<>();

    public void addAQ(String aName, AQ aq) {
        AQlijst.put(aName, aq);
    }

    public boolean hasAQ(String aName) {
        Set<String> keys = AQlijst.keySet();
        boolean r1 = keys.contains(aName);
        boolean r =  (AQlijst.containsKey(aName));
        return r;
    }

    public AQ geefAQ(String aName) {
        AQ result = AQlijst.get(aName);
        return result;
    }
}
