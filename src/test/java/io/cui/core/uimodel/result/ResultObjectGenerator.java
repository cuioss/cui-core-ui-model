package io.cui.core.uimodel.result;

import io.cui.test.generator.Generators;
import io.cui.test.generator.TypedGenerator;

@SuppressWarnings({ "javadoc", "rawtypes" })
public class ResultObjectGenerator implements TypedGenerator<ResultObject> {

    private static final ResultDetailGenerator DETAIL_GENERATOR =
        new ResultDetailGenerator();

    @Override
    public ResultObject next() {
        return createAnyValid();
    }

    @Override
    public Class<ResultObject> getType() {
        return ResultObject.class;
    }

    private static ResultObject<?> createAnyValid() {

        return new ResultObject.Builder<>()
                .result(Generators.nonEmptyStrings().next())
                .state(ResultState.VALID)
                .resultDetail(DETAIL_GENERATOR.next())
                .build();
    }
}
