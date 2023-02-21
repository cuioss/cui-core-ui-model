package io.cui.core.uimodel.security;

/**
 * Allows to sanitize any given string to prevent code injection / cross side scripting.
 */
public interface Sanitizer {

    /**
     * @param untrustedHtml the string to sanitize
     * @return the sanitized result
     */
    String sanitize(String untrustedHtml);
}
