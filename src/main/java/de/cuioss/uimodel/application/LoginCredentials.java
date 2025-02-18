/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.uimodel.application;

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import java.io.Serial;
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

    @Serial
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
     * Checks whether the given credentials are syntactically correct, saying
     * {@link #getUsername()} and {@link #getPassword()} are neither null nor empty.
     *
     * @return boolean indicating the completeness of the configuration
     */
    public boolean isComplete() {
        return !isEmpty(getUsername()) && !isEmpty(getPassword());
    }

}
