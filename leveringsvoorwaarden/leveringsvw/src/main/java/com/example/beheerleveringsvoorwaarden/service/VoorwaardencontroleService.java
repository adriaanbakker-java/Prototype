package com.example.beheerleveringsvoorwaarden.service;

import com.example.beheerleveringsvoorwaarden.repo.Voorwaarde;
import com.example.checkleveringsvoorwaarden.model.Answer;
import com.example.checkleveringsvoorwaarden.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VoorwaardencontroleService {
    @Autowired
    private VoorwaardenbeheerService voorwaardenbeheerService;
    
    public Answer checkRequest(Request request) {
        Answer answer = new Answer();
        answer.setResult(true);
        answer.setFoutmelding("");

        List<Voorwaarde> voorwaardeList = (List<Voorwaarde>) voorwaardenbeheerService.getLijstVoorwaardenGesorteerd();
        List<Voorwaarde> vwBijBericht = 
                filterOpBericht(voorwaardeList, request.getBerichtnaam());

        List<Voorwaarde> vwBijDoel =
                filterOpDoel(voorwaardeList, request.getLeveringsdoel());

        if (vwBijBericht.isEmpty()) {
            answer.setResult(false);
            answer.setFoutmelding("Geen leveringsvoorwaarden bij bericht " + request.getBerichtnaam());
            return answer;
        }

        if (vwBijDoel.isEmpty()) {
            answer.setResult(false);
            answer.setFoutmelding("Geen leveringsvoorwaarden bij leveringsdoel " + request.getLeveringsdoel());
            return answer;
        }
        List<Voorwaarde> vwBijBerichtEnDoel =
                filterOpDoel(voorwaardeList, request.getLeveringsdoel());

        if (vwBijBerichtEnDoel.isEmpty()) {
            answer.setResult(false);
            answer.setFoutmelding("Geen leveringsvoorwaarden bij bericht " + request.getBerichtnaam() +
                    " en leveringsdoel " + request.getLeveringsdoel());
            return answer;
        }

        ArrayList<String> geldigepaden = new ArrayList<>();
        for (Voorwaarde vw: vwBijBerichtEnDoel) {
            geldigepaden.add(vw.getPadnaargegeven());
        }

        for (String pad: request.getBerichtelementen()) {
            if (!(geldigepaden.contains(pad))) {
                answer.setResult(false);
                answer.setFoutmelding("Berichtelement " + pad + " niet toegestaan bij bericht " +
                        request.getBerichtnaam() + " en leveringsdoel " + request.getLeveringsdoel() );
                return answer;
            }
        }
        return answer;
    }

    private List<Voorwaarde> filterOpDoel(List<Voorwaarde> voorwaardeList, String doel) {
        List<Voorwaarde> result = new ArrayList<>();
        for (Voorwaarde vw: voorwaardeList) {
            if (vw.getLeveringsdoel().equals(doel))
                result.add(vw);
        }
        return result;
    }

    private List<Voorwaarde> filterOpBericht(List<Voorwaarde> voorwaardeList, String berichtnaam) {
        List<Voorwaarde> result = new ArrayList<>();
        for (Voorwaarde vw: voorwaardeList) {
            if (vw.getBerichtnaam().equals(berichtnaam))
                result.add(vw);
        }
        return result;
    }
}
