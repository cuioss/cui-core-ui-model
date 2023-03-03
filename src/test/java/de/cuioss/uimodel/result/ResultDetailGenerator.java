package de.cuioss.uimodel.result;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.uimodel.nameprovider.DisplayName;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import de.cuioss.uimodel.nameprovider.LabeledKey;

@SuppressWarnings("javadoc")
public class ResultDetailGenerator implements TypedGenerator<ResultDetail> {

    @Override
    public ResultDetail next() {
        return ResultDetail.builder()
                .detail(anyDisplayNameProvider())
                .cause(Generators.throwables().next())
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
}
