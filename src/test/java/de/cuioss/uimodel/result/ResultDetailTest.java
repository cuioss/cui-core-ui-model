package de.cuioss.uimodel.result;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import de.cuioss.uimodel.nameprovider.DisplayName;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;

@PropertyReflectionConfig(skip = true)
class ResultDetailTest extends ValueObjectTest<ResultDetail> {

    private final ResultDetailGenerator generator = new ResultDetailGenerator();

    @Override
    protected ResultDetail anyValueObject() {
        return generator.next();
    }

    @Test
    void shouldBuildWithConstructors() {
        final IDisplayNameProvider<String> dnProvider = new DisplayName("test");
        final Throwable ex = new Exception("b00m");

        assertDoesNotThrow(() -> new ResultDetail(dnProvider),
                "Constructor call with IDisplayNameProvider failed");

        assertDoesNotThrow(() -> new ResultDetail(dnProvider, ex),
                "Constructor call with Throwable failed");

    }
}
