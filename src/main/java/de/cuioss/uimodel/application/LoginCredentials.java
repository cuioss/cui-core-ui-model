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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

import static de.cuioss.tools.string.MoreStrings.isEmpty;

/**
 * Represents login credentials for form-based authentication, providing a structured
 * way to handle username, password, and related authentication information. This class
 * is designed to be used with web forms and authentication services.
 *
 * <p>Features:
 * <ul>
 *   <li>Username and password storage with proper security considerations</li>
 *   <li>Optional user store support for multi-tenant applications</li>
 *   <li>"Remember me" functionality support</li>
 *   <li>Built-in validation for credential completeness</li>
 *   <li>Secure toString/equals implementations excluding password</li>
 * </ul>
 *
 * <p>Security Notes:
 * <ul>
 *   <li>Password is excluded from toString() and equals()/hashCode() for security</li>
 *   <li>The class is serializable but should be handled with care in distributed systems</li>
 *   <li>Passwords should be cleared from memory when no longer needed</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * // Create credentials using builder
 * LoginCredentials credentials = LoginCredentials.builder()
 *     .username("john.doe")
 *     .password("secretPassword")
 *     .userStore("LDAP")
 *     .rememberLoginCredentials(true)
 *     .build();
 *
 * // Verify credentials are complete
 * if (credentials.isComplete()) {
 *     authenticationService.login(credentials);
 * }
 *
 * // Alternative construction using no-args constructor
 * LoginCredentials altCredentials = new LoginCredentials();
 * altCredentials.setUsername("jane.doe");
 * altCredentials.setPassword("anotherPassword");
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@EqualsAndHashCode(exclude = "password", doNotUseGetters = true)
@ToString(exclude = "password", doNotUseGetters = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginCredentials implements Serializable {

    @Serial
    private static final long serialVersionUID = -5554252114058479008L;

    /**
     * The user's password. This field is excluded from toString() and equals()/hashCode()
     * calculations for security reasons.
     */
    @Getter
    @Setter
    private String password;

    /**
     * The username for authentication. This is typically the user's login identifier
     * and must not be empty for valid credentials.
     */
    @Getter
    @Setter
    private String username;

    /**
     * Optional identifier for the authentication store or domain. This can be used
     * in multi-tenant applications or when supporting multiple authentication providers.
     */
    @Getter
    @Setter
    private String userStore;

    /**
     * Flag indicating whether the credentials should be remembered for future sessions.
     * Implementation of the actual persistence mechanism is handled by the authentication system.
     */
    @Getter
    @Setter
    private boolean rememberLoginCredentials;

    /**
     * Validates the completeness of the credentials by ensuring both username and
     * password are provided. This is a syntactic validation only and does not verify
     * the actual validity of the credentials with any authentication system.
     *
     * <p>Note: A return value of {@code true} only indicates that the required fields
     * are present, not that the credentials are valid for authentication.
     *
     * @return {@code true} if both username and password are non-empty,
     *         {@code false} otherwise
     */
    public boolean isComplete() {
        return !isEmpty(getUsername()) && !isEmpty(getPassword());
    }
}
