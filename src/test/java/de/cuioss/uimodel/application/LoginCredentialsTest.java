package de.cuioss.uimodel.application;

import static de.cuioss.test.generator.Generators.nonEmptyStrings;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyBuilder;
import de.cuioss.test.valueobjects.api.object.ObjectTestConfig;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@VerifyBuilder
@PropertyReflectionConfig(of = { "username", "password", "userStore", "rememberLoginCredentials" })
@ObjectTestConfig(equalsAndHashCodeExclude = "password")
class LoginCredentialsTest extends ValueObjectTest<LoginCredentials> {

    @Test
    void shouldDetectCompletness() {
        assertTrue(LoginCredentials.builder().username(nonEmptyStrings().next()).password(nonEmptyStrings().next())
                .build().isComplete());
        assertFalse(LoginCredentials.builder().password(nonEmptyStrings().next())
                .build().isComplete());
        assertFalse(LoginCredentials.builder().username(nonEmptyStrings().next())
                .build().isComplete());
        assertFalse(LoginCredentials.builder().build().isComplete());
    }
}
