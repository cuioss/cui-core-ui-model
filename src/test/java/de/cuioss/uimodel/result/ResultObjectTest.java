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
package de.cuioss.uimodel.result;

import static de.cuioss.uimodel.result.ResultState.VALID;
import static de.cuioss.uimodel.result.ResultState.WARNING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Serializable;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.juli.LogAsserts;
import de.cuioss.test.juli.TestLogLevel;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.uimodel.nameprovider.DisplayName;
import de.cuioss.uimodel.nameprovider.LabeledKey;

@PropertyReflectionConfig(skip = true)
@EnableTestLogger
class ResultObjectTest extends ValueObjectTest<ResultObject<?>> {

    private static final CuiLogger log = new CuiLogger(ResultObjectTest.class);

    private static final ResultObject<String> SERVICE_NOT_AVAILABLE = new ResultObject<>("Test", ResultState.ERROR,
            new ResultDetail(new DisplayName("Test")), ExampleErrorCodes.SERVICE_NOT_AVAILABLE);

    private final ResultDetailGenerator detailGenerator = new ResultDetailGenerator();

    private final ResultObjectGenerator generator = new ResultObjectGenerator();

    @Override
    protected ResultObject<?> anyValueObject() {
        return generator.next();
    }

    @Test
    void shouldProvideResult() {
        final ResultObject<?> target = generator.next();

        assertNotNull(target.getResult());

        var resultState = Generators.enumValues(ResultState.class).next();
        var detail = detailGenerator.next();

        // verify requestResult is mandatory
        assertThrows(IllegalArgumentException.class, () -> new ResultObject<Serializable>(null, resultState, detail));

    }

    @Test
    void shouldProvideState() {

        // verify requestResult is mandatory
        var result = Generators.nonEmptyStrings().next();

        assertThrows(IllegalArgumentException.class, () -> new ResultObject<>(result, ResultState.ERROR));
    }

    @Test
    void shouldAllowOptionalResultDetail() {
        final ResultObject<?> target = new ResultObject<>(Generators.nonEmptyStrings().next(), VALID);
        assertThat(target.getResultDetail().isPresent(), is(Boolean.FALSE));
    }

    @SuppressWarnings("unchecked")
    @Test
    void shouldHandleCopyConstructorWithMapper() {
        final ResultObject<String> expected = generator.next();
        var copy = new ResultObject<>(expected, Function.identity(), "");
        assertEquals(expected.getResult(), copy.getResult());
    }

    @SuppressWarnings("unchecked")
    @Test
    void shouldHandleCopyConstructor() {
        final ResultObject<String> expected = generator.next();
        var copy = new ResultObject<>(expected.getResult(), expected);
        assertEquals(expected.getResult(), copy.getResult());
        assertEquals(expected.getErrorCode(), copy.getErrorCode());
        assertEquals(expected.getState(), copy.getState());
        assertEquals(expected.getResultDetail(), copy.getResultDetail());
    }

    @Test
    void shouldPreventIgnoreError() {

        final ResultObject<?> target = new ResultObject<>(Generators.nonEmptyStrings().next(), ResultState.ERROR,
                detailGenerator.next());

        try {
            target.getResult();
            fail("must throw IllegalArgumentException before");
        } catch (final UnsupportedOperationException e) {
            assertThat(e, is(notNullValue()));
        }

        assertThat(target.getResultDetail(), is(notNullValue()));
        assertThat(target.getResult(), is(notNullValue()));
    }

    @Test
    void builderShouldSupportValidResult() {
        final var builder = new ResultObject.Builder<Serializable>();
        final var anyResult = new ResultObject<>(Generators.nonEmptyStrings().next(), ResultState.WARNING,
                detailGenerator.next());
        builder.validDefaultResult(anyResult).state(VALID);
        final var result = builder.build();
        assertTrue(result.isValid());
    }

    @Test
    void builderShouldFailWithInValidState() {
        final var builder = new ResultObject.Builder<Serializable>();
        final var anyResult = new ResultObject<>(Generators.nonEmptyStrings().next(), ResultState.WARNING,
                detailGenerator.next());
        builder.validDefaultResult(anyResult);
        assertThrows(UnsupportedOperationException.class, builder::build);

    }

    @Test
    void builderShouldSupportApiUser() {
        final var builder = new ResultObject.Builder<Serializable>();

        try {
            // nothing set
            builder.build();
        } catch (final UnsupportedOperationException e) {
            assertThat(e, is(notNullValue()));
            assertThat(e.getMessage(), containsString("Use setResult or setValidDefaultResult as fallback"));
        }

        try {
            // nothing set
            builder.build();
        } catch (final UnsupportedOperationException e) {
            assertThat(e, is(notNullValue()));
        }
    }

    @Test
    void shouldVerifyParameterOnReusePreviousResult() {
        final var builder = new ResultObject.Builder<Serializable>();
        assertThrows(NullPointerException.class, () -> builder.extractStateAndDetailsAndErrorCodeFrom(null));
    }

    @Test
    void shouldProvidePossibilityToReusePreviousResult() {
        final var builder = new ResultObject.Builder<Boolean>();
        builder.validDefaultResult(Boolean.FALSE);
        final var anyResultDetail = detailGenerator.next();
        final var anyResult = new ResultObject<>(Generators.nonEmptyStrings().next(), ResultState.WARNING,
                anyResultDetail);

        builder.extractStateAndDetailsAndErrorCodeFrom(anyResult);
        final var buildResult = builder.build();

        assertThat(buildResult.getState(), is(ResultState.WARNING));
        assertThat(buildResult.getResultDetail().get(), is(equalTo(anyResultDetail)));
    }

    @Test
    void shouldExtractStrategy() {
        assertTrue(SERVICE_NOT_AVAILABLE.getErrorCode().isPresent());
    }

    @Test
    void shouldCheckForStrategy() {
        assertTrue(SERVICE_NOT_AVAILABLE.containsErrorCode(ExampleErrorCodes.SERVICE_NOT_AVAILABLE));

        assertFalse(SERVICE_NOT_AVAILABLE.containsErrorCode(ExampleErrorCodes.TEST));

        assertTrue(SERVICE_NOT_AVAILABLE.containsErrorCode(ExampleErrorCodes.TEST,
                ExampleErrorCodes.SERVICE_NOT_AVAILABLE));
    }

    @Test
    void shouldHandleMultipleResultDetailsWithoutState() {
        final var builder = new ResultObject.Builder<Boolean>();

        builder.validDefaultResult(Boolean.FALSE);
        builder.resultDetail(new ResultDetail(new DisplayName("Test1")));
        builder.resultDetail(new ResultDetail(new DisplayName("Test2")));

        final var buildResult = builder.state(VALID).build();

        assertThat(buildResult.getResultDetail().get(), is(equalTo(new ResultDetail(new DisplayName("Test2")))));
    }

    @Test
    void shouldHandleMultipleResultDetailsWithState() {
        final var builder = new ResultObject.Builder<Boolean>();

        builder.validDefaultResult(Boolean.FALSE).state(ResultState.ERROR);
        builder.resultDetail(new ResultDetail(new DisplayName("Test1")));
        builder.resultDetail(new ResultDetail(new DisplayName("Test2")));

        LogAsserts.assertLogMessagePresentContaining(TestLogLevel.ERROR, "Already failed");

        final var buildResult = builder.build();

        assertThat(buildResult.getResultDetail().get(), is(equalTo(new ResultDetail(new DisplayName("Test2")))));
    }

    @Test
    void shouldLogDetail() {
        final var result = new ResultObject<>("Test", WARNING, new ResultDetail(new DisplayName("Test")));

        result.logDetail("Prefix", log);

        LogAsserts.assertLogMessagePresentContaining(TestLogLevel.WARN, "Prefix");
    }

    @Test
    void shouldLogThrowable() {
        final var result = new ResultObject<>("Test", WARNING,
                new ResultDetail(new LabeledKey("some.key"), new IllegalStateException("b00m")));

        result.logDetail("prefix: ", log);

        LogAsserts.assertLogMessagePresentContaining(TestLogLevel.WARN,
                "prefix: LabeledKey(content=some.key, parameter=[])");
    }
}
