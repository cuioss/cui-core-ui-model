package de.cuioss.uimodel.application;

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Simple class providing credentials for form-based login
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode(exclude = "password", doNotUseGetters = true)
@ToString(exclude = "password", doNotUseGetters = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginCredentials implements Serializable {

    private static final long serialVersionUID = -5554252114058479008L;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String userStore;

    @Getter
    @Setter
    private boolean rememberLoginCredentials;

    /**
     * Checks whether the given credentials are syntactically correct, saying {@link #getUsername()}
     * and {@link #getPassword()} are neither null nor empty.
     *
     * @return boolean indicating the completeness of the configuration
     */
    public boolean isComplete() {
        return !isEmpty(getUsername()) && !isEmpty(getPassword());
    }

}
