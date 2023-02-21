package io.cui.core.uimodel.result;

import io.cui.core.uimodel.nameprovider.DisplayName;
import io.cui.core.uimodel.nameprovider.IDisplayNameProvider;
import io.cui.core.uimodel.nameprovider.LabeledKey;
import io.cui.test.generator.Generators;
import io.cui.test.generator.TypedGenerator;

@SuppressWarnings("javadoc")
public class ResultDetailGenerator implements TypedGenerator<ResultDetail> {

    public enum DummyHandlingStrategy {
        DO_NOTHING,
        DO_FUSSY,
        DO_EXPLODE
    }

    @Override
    public ResultDetail next() {
        return ResultDetail.builder()
                .detail(anyDisplayNameProvider())
                .cause(Generators.throwables().next())
                .handlingStrategy(anyHandlingStrategy())
                .build();
    }

    @Override
    public Class<ResultDetail> getType() {
        return ResultDetail.class;
    }

    private static IDisplayNameProvider<?> anyDisplayNameProvider() {
        return Generators.booleans().next()
                ? new DisplayName(Generators.letterStrings(5, 50).next())
                : new LabeledKey(Generators.letterStrings(5, 50).next());
    }

    private static Enum<?> anyHandlingStrategy() {
        return Generators.booleans().next()
                ? Generators.enumValues(DummyHandlingStrategy.class).next()
                : null;
    }
}
