package com.example.beheerleveringsvoorwaarden.service;

import com.example.beheerleveringsvoorwaarden.repo.Voorwaarde;
import com.example.beheerleveringsvoorwaarden.repo.VoorwaardenRepository;
import com.example.checkleveringsvoorwaarden.model.Answer;
import com.example.checkleveringsvoorwaarden.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VoorwaardencontroleService {
    @Autowired
    VoorwaardenRepository repository;

    public Object getLijstVoorwaardenGesorteerd() {
        return (List<Voorwaarde>) repository.getListVoorwaardenlijstGesorteerd();
    }

    public List<Voorwaarde> getLijstVoorwaarden(String berichtnaam, String leveringsdoel) {
        return  repository.getLeveringsvoorwaarden(berichtnaam, leveringsdoel);
    }


    public Answer checkRequest(Request request) {
        Answer answer = new Answer();
        answer.setResult(true);
        answer.setFoutmelding("");

        List<Voorwaarde> voorwaardeList =  getLijstVoorwaarden(request.getBerichtnaam(), request.getLeveringsdoel());

        if (voorwaardeList.isEmpty()) {
            answer.setResult(false);
            voorwaardeList = getLijstVoorwaarden(request.getBerichtnaam(), "");
            if (voorwaardeList.isEmpty())  {
                answer.setFoutmelding("Bericht niet bekend in de leveringsvoorwaarden repository:" + request.getBerichtnaam());
                return answer;
            }
            voorwaardeList = getLijstVoorwaarden("", request.getLeveringsdoel());

            if (voorwaardeList.isEmpty())  {
                answer.setFoutmelding("Leveringsdoel niet bekend in de leveringsvoorwaarden repository:" + request.getLeveringsdoel());
                return answer;
            }
            answer.setFoutmelding("Geen leveringsvoorwaarden gevonden bij bericht " + request.getBerichtnaam() +
                    " en leveringsdoel " + request.getLeveringsdoel() );
            return answer;
        }

        ArrayList<String> geldigepaden = new ArrayList<>();
        for (Voorwaarde vw: voorwaardeList) {
            geldigepaden.add(vw.getPadnaargegeven());
        }

        for (String pad: request.getBerichtelementen()) {
            if (!(geldigepaden.contains(pad))) {
                answer.setResult(false);
                answer.setFoutmelding("Pad naar berichtelement " + pad + " niet toegestaan bij bericht " +
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
