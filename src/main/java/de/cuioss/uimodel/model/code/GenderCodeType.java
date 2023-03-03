package de.cuioss.uimodel.model.code;

import de.cuioss.uimodel.model.Gender;

/**
 * Typed code type for person gender
 *
 * @author Eugen Fischer
 */
public interface GenderCodeType extends CodeType {

    /**
     * Expected person gender always available
     *
     * @return {@linkplain Gender}
     */
    Gender getGender();

}
