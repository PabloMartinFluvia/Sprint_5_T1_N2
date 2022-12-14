package com.martinfluviapablo.s5t1n2.model.dto;

import javax.validation.constraints.Null;

public class FlorDto {

    @Null(message = "{flor.nullid}", groups = AddGroup.class)
    @ValidId(groups = ReplaceGroup.class) //compounded constraint
    private Integer pk_FlorID;

    @ValidString //compounded constraint
    private String nomFlor;

    @ValidString //compounded constraint
    private String paisFlor;

    @Null(message = "{flor.nullstring}")
    private String tipusFlor;

    public Integer getPk_FlorID() {
        return pk_FlorID;
    }

    public void setPk_FlorID(Integer pk_FlorID) {
        this.pk_FlorID = pk_FlorID;
    }

    public String getNomFlor() {
        return nomFlor;
    }

    public void setNomFlor(String nomFlor) {
        this.nomFlor = nomFlor;
    }

    public String getPaisFlor() {
        return paisFlor;
    }

    public void setPaisFlor(String paisFlor) {
        this.paisFlor = paisFlor;
    }

    public String getTipusFlor() {
        return tipusFlor;
    }

    public void setTipusFlor(String tipusFlor) {
        this.tipusFlor = tipusFlor;
    }
}
