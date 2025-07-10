/**
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.uimodel.result;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.test.juli.TestLogLevel;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import de.cuioss.uimodel.nameprovider.DisplayName;
import org.junit.jupiter.api.Test;

import static de.cuioss.test.generator.Generators.nonEmptyStrings;
import static de.cuioss.test.juli.LogAsserts.assertLogMessagePresent;
import static de.cuioss.test.juli.LogAsserts.assertSingleLogMessagePresent;
import static de.cuioss.uimodel.UiModelLogMessages.INFO;
import static de.cuioss.uimodel.UiModelLogMessages.WARN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@EnableTestLogger
@EnableGeneratorController
@PropertyReflectionConfig(skip = true)
class ResultObjectLoggingTest extends ValueObjectTest<ResultObject<String>> {

    private static final TypedGenerator<String> STRINGS = nonEmptyStrings();

    @Test
    void shouldLogResultCreation() {
        // given
        final var result = STRINGS.next();

        // when
        ResultObject.<String>builder()
                .result(result)
                .state(ResultState.VALID)
                .build();

        // then
        assertSingleLogMessagePresent(TestLogLevel.INFO,
                INFO.RESULT_CREATED.format(ResultState.VALID));
    }

    @Test
    void shouldLogInvalidResultAccess() {
        // given
        final var result = STRINGS.next();
        final var resultObject = ResultObject.<String>builder()
                .result(result)
                .state(ResultState.ERROR)
                .resultDetail(new ResultDetail(new DisplayName("Error")))
                .build();

        // when
        var thrown = assertThrows(UnsupportedOperationException.class, resultObject::getResult);

        // then
        assertLogMessagePresent(TestLogLevel.WARN,
                WARN.INVALID_RESULT_ACCESS.format(ResultState.ERROR));
        assertEquals("ResultObject include error which you must handle first. See", thrown.getMessage());
    }

    @Test
    void shouldLogResultMapping() {
        // given
        final var result = STRINGS.next();
        final ResultObject<String> validResult = ResultObject.<String>builder()
                .result(result)
                .state(ResultState.VALID)
                .build();

        // when
        new ResultObject<>(validResult, String::toUpperCase, "default");

        // then
        assertLogMessagePresent(TestLogLevel.INFO,
                INFO.RESULT_MAPPED.format("String", "String"));
    }

    @Test
    void shouldLogMissingResultDetail() {
        // given
        final var result = STRINGS.next();

        // when
        ResultObject.Builder<String> state = ResultObject.<String>builder()
                .result(result)
                .state(ResultState.ERROR);
        var thrown = assertThrows(UnsupportedOperationException.class, state::build);

        // then
        assertLogMessagePresent(TestLogLevel.WARN,
                WARN.MISSING_RESULT_DETAIL.format(ResultState.ERROR));
        assertEquals("The ResultDetail is mandatory if state is not VALID.", thrown.getMessage());
    }

    @Override
    protected ResultObject<String> anyValueObject() {
        return ResultObject.<String>builder()
                .result(STRINGS.next())
                .state(ResultState.VALID)
                .build();
    }
}
