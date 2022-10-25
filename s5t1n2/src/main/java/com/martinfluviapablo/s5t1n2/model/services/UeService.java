package com.martinfluviapablo.s5t1n2.model.services;

import com.martinfluviapablo.s5t1n2.model.domain.InfoUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UeService {

    private final InfoUE infoUE;

    @Autowired
    public UeService(InfoUE ueMembers) {
        this.infoUE = ueMembers;
    }

    public String getMembershipInfo(String pais){
        if(infoUE.isMember(pais)){
            return "UE";
        }else{
            return "Fora UE";
        }
    }
}
