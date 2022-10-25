package com.martinfluviapablo.s5t1n2.model.dto;

import javax.validation.groups.Default;

/*
Validation applied to this group implies validate:
    specific constraints of this group
    AND
    constraints without group defined (Default group)
 */
public interface AddGroup extends Default { //important: extends Default
}
