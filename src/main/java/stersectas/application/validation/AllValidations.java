package stersectas.application.validation;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/** Marker interface defining the order of all validation groups. */
@GroupSequence({ Default.class, ExtendedValidations.class })
public interface AllValidations {

}
