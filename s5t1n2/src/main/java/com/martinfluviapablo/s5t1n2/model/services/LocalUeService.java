package com.martinfluviapablo.s5t1n2.model.services;

import com.martinfluviapablo.s5t1n2.model.domain.InfoUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalUeService implements UeService{

    private final InfoUE infoUE;

    private final String MEMBER = "UE";

    private final String NOT_MEMBER = "Fora UE";

    @Autowired
    public LocalUeService(InfoUE ueMembers) {
        this.infoUE = ueMembers;
    }

    @Override
    public String getMembershipInfo(String pais){
        if(infoUE.isMember(pais)){
            return MEMBER;
        }else{
            return NOT_MEMBER;
        }
    }
}
