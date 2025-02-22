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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("ResultErrorCodes Tests")
class ResultErrorCodesTest {

    @Nested
    @DisplayName("HTTP Code Parsing Tests")
    class ParseHttpCodeTest {

        @ParameterizedTest(name = "HTTP code {0} should map to {1}")
        @CsvSource({
            "400, BAD_REQUEST",
            "401, NOT_AUTHENTICATED",
            "403, NOT_AUTHORIZED",
            "404, NOT_FOUND",
            "503, SERVICE_NOT_AVAILABLE",
            "500, RUNTIME_ERROR",
            "0, RUNTIME_ERROR"
        })
        void parseHttpCode(int httpCode, ResultErrorCodes expected) {
            // Act
            ResultErrorCodes actual = ResultErrorCodes.parseHttpCode(httpCode);

            // Assert
            assertEquals(expected, actual);
        }
    }
}
