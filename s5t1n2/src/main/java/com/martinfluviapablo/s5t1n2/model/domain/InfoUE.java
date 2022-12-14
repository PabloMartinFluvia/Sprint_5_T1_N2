//data from https://ca.wikipedia.org/wiki/Estats_membres_de_la_Uni%C3%B3_Europea#Dades_dels_estats_membres

package com.martinfluviapablo.s5t1n2.model.domain;

import org.springframework.stereotype.Component;
import java.text.Collator;
import java.util.HashSet;
import java.util.Set;

@Component
public class InfoUE {

    private final Set<String> paisos;

    //to sort, compare, equals, etc... Strings ignoring accents and uppercase -> Á = a
    private final Collator collator;

    public InfoUE(){
        paisos = new HashSet<>(Set.of(
                "Alemanya","Àustria","Bèlgica","Bulgària","Croàcia","Dinamarca","Eslovàquia",
                "Eslovènia","Espanya","Estònia","Finlàndia","França","Grècia","Hongria",
                "Irlanda","Itàlia","Letònia","Lituània","Luxemburg","República de Malta",
                "Països Baixos","Polònia","Portugal","República Txeca","Romania","Suècia","Xipre")
        );
        collator = Collator.getInstance();
        collator.setStrength(Collator.PRIMARY);
    }

    public boolean isMember(String pais){
        return  paisos.stream().anyMatch(paisUE ->collator.equals(paisUE,pais));
    }
}
