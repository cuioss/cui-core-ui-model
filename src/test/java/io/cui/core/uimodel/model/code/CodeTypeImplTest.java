package io.cui.core.uimodel.model.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import io.cui.test.valueobjects.ValueObjectTest;
import io.cui.test.valueobjects.api.contracts.VerifyConstructor;
import io.cui.test.valueobjects.api.object.ObjectTestConfig;
import io.cui.test.valueobjects.api.property.PropertyConfig;
import io.cui.tools.property.PropertyReadWrite;

@PropertyConfig(name = CodeTypeImplTest.RESOLVED, propertyClass = String.class,
        propertyReadWrite = PropertyReadWrite.WRITE_ONLY)
@VerifyConstructor(of = CodeTypeImplTest.IDENTIFIER, required = CodeTypeImplTest.IDENTIFIER)
// Resolved is used as identifier
@VerifyConstructor(of = { CodeTypeImplTest.RESOLVED, CodeTypeImplTest.IDENTIFIER },
        required = { CodeTypeImplTest.RESOLVED, CodeTypeImplTest.IDENTIFIER })
@ObjectTestConfig(equalsAndHashCodeOf = CodeTypeImplTest.IDENTIFIER)
class CodeTypeImplTest extends ValueObjectTest<CodeTypeImpl> {

    protected static final String RESOLVED = "resolved";
    protected static final String IDENTIFIER = "identifier";

    @Test
    void shouldCreateWithSimpleConstructor() {
        final var type = new CodeTypeImpl(RESOLVED);
        assertEquals(RESOLVED, type.getResolved(Locale.ENGLISH));
        assertEquals(RESOLVED, type.getIdentifier());
    }

    @Test
    void shouldCreateWithStandardConstructor() {
        final var type = new CodeTypeImpl(RESOLVED, IDENTIFIER);
        assertEquals(RESOLVED, type.getResolved(Locale.ENGLISH));
        assertEquals(IDENTIFIER, type.getIdentifier());
    }

    @Test
    void shouldIgnoreLocale() {
        final var type = new CodeTypeImpl(RESOLVED, IDENTIFIER);
        assertEquals(RESOLVED, type.getResolved(null));
        assertEquals(IDENTIFIER, type.getIdentifier());
    }
}
