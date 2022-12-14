package com.martinfluviapablo.s5t1n2.model.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "flors")
public class FlorEntity {

    @Id
    @Column(name="flor_id")
    @GeneratedValue
    private Integer pk_FlorID;

    @Column(name = "nom", nullable = false) //default length 255, can be blank ""
    private String nomFlor;

    @Column(name = "pais", nullable = false) //default length 255, can be blank ""
    private String paisFlor;

    public FlorEntity() {
        //required no args constructor due @Entity (JPA specification)
    }

    @Override
    public String toString() {
        return "FlorEntity: {" +
                " pk_FlorID=" + pk_FlorID +
                "; nomFlor='" + nomFlor +
                "; paisFlor='" + paisFlor +
                " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlorEntity flor = (FlorEntity) o;
        return pk_FlorID.equals(flor.getPk_FlorID())
                && nomFlor.equals(flor.getNomFlor())
                && paisFlor.equals(flor.getPaisFlor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk_FlorID, nomFlor, paisFlor);
    }
}
