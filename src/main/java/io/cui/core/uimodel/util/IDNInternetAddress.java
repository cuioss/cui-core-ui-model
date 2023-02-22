package io.cui.core.uimodel.util;

import java.net.IDN;
import java.util.regex.Pattern;

import io.cui.core.uimodel.security.Sanitizer;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * <p>
 * Utility class to handle IDN email addresses.
 * </p>
 * <p>
 * see
 * <a href="https://docs.oracle.com/javase/tutorial/i18n/network/idn.html">https://docs.oracle.com/
 * javase/tutorial/i18n/network/idn.html</a><br />
 * see
 * <a href="https://de.wikipedia.org/wiki/Internationalisierter_Domainname">https://de.wikipedia.org
 * /wiki/Internationalisierter_Domainname</a><br />
 * see
 * <a href="https://en.wikipedia.org/wiki/Internationalized_domain_name">https://en.wikipedia.org/
 * wiki/Internationalized_domain_name</a><br />
 * </p>
 *
 * @author Matthias Walliczek
 */
@UtilityClass
public class IDNInternetAddress {

    private static final Pattern addressPatternWithDisplayName =
        Pattern.compile("(.{0,64})<(.{1,64})@(.{1,64})>(.{0,64})");

    private static final Pattern addressPattern = Pattern.compile("(.{1,64})@(.{1,64})");

    /**
     * Encode the domain part of an email address
     *
     * @param completeAddress the address to encode in RFC822 format
     * @return the encoded address in RFC822 format
     */
    public static String encode(@NonNull final String completeAddress) {
        return encode(completeAddress, untrustedHtml -> untrustedHtml);
    }

    /**
     * Encodes the given address and sanitizes the elements with the provided sanitizer. It takes
     * care on the special elements like {@code <>} by not trying to sanitize them.
     *
     * @param completeAddress
     * @param sanitizer
     * @return the sanitized and encoded address.
     */
    public static String encode(@NonNull final String completeAddress, Sanitizer sanitizer) {
        var matcher = addressPatternWithDisplayName.matcher(completeAddress);
        if (matcher.matches() && matcher.groupCount() == 4) {
            return sanitizer.sanitize(matcher.group(1)) + "<" + sanitizer.sanitize(matcher.group(2)) + "@" + sanitizer
                    .sanitize(IDN.toASCII(matcher.group(3))) + ">" + sanitizer.sanitize(matcher.group(4));
        }
        matcher = addressPattern.matcher(completeAddress);
        if (matcher.matches() && matcher.groupCount() == 2) {
            return sanitizer.sanitize(matcher.group(1)) + "@" + sanitizer.sanitize(IDN.toASCII(matcher.group(2)));
        }
        return sanitizer.sanitize(completeAddress);
    }

    /**
     * Decode the domain part of an email address
     *
     * @param completeAddress the address to decode in RFC822 format
     * @return the decoded address in RFC822 format
     */
    public static String decode(@NonNull final String completeAddress) {
        return decode(completeAddress, untrustedHtml -> untrustedHtml);
    }

    /**
     * Decodes the given address and sanitizes the elements with the provided sanitizer. It takes
     * care on the special elements like <> by not trying to sanitize them.
     *
     * @param completeAddress
     * @param sanitizer
     * @return the sanitized and decoded address.
     */
    public static String decode(@NonNull final String completeAddress, Sanitizer sanitizer) {
        var matcher = addressPatternWithDisplayName.matcher(completeAddress);
        if (matcher.matches() && matcher.groupCount() == 4) {
            return sanitizer.sanitize(matcher.group(1)) + "<" + sanitizer.sanitize(matcher.group(2)) + "@" + sanitizer
                    .sanitize(IDN.toUnicode(matcher.group(3))) + ">" + sanitizer.sanitize(matcher.group(4));
        }
        matcher = addressPattern.matcher(completeAddress);
        if (matcher.matches() && matcher.groupCount() == 2) {
            return sanitizer.sanitize(matcher.group(1)) + "@" + sanitizer.sanitize(IDN.toUnicode(matcher.group(2)));
        }
        return sanitizer.sanitize(completeAddress);
    }
}
