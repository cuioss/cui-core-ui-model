package io.cui.core.uimodel.result;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import io.cui.core.uimodel.nameprovider.DisplayName;
import io.cui.core.uimodel.nameprovider.IDisplayNameProvider;
import io.cui.test.valueobjects.ValueObjectTest;
import io.cui.test.valueobjects.api.property.PropertyReflectionConfig;

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
