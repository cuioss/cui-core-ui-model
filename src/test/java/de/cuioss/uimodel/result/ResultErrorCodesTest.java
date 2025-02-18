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

import static de.cuioss.uimodel.result.ResultErrorCodes.BAD_REQUEST;
import static de.cuioss.uimodel.result.ResultErrorCodes.NOT_AUTHENTICATED;
import static de.cuioss.uimodel.result.ResultErrorCodes.NOT_AUTHORIZED;
import static de.cuioss.uimodel.result.ResultErrorCodes.NOT_FOUND;
import static de.cuioss.uimodel.result.ResultErrorCodes.RUNTIME_ERROR;
import static de.cuioss.uimodel.result.ResultErrorCodes.SERVICE_NOT_AVAILABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

class ResultErrorCodesTest {

    /**
     * Test method for
     * {@link de.cuioss.uimodel.result.ResultErrorCodes#parseHttpCode(int)}.
     */
    @Test
    void parseHttpCode() {
        assertEquals(RUNTIME_ERROR, ResultErrorCodes.parseHttpCode(0));
        assertEquals(RUNTIME_ERROR, ResultErrorCodes.parseHttpCode(500));

        assertEquals(BAD_REQUEST, ResultErrorCodes.parseHttpCode(400));
        assertEquals(NOT_AUTHENTICATED, ResultErrorCodes.parseHttpCode(401));
        assertEquals(NOT_AUTHORIZED, ResultErrorCodes.parseHttpCode(403));

        assertEquals(NOT_FOUND, ResultErrorCodes.parseHttpCode(404));
        assertEquals(SERVICE_NOT_AVAILABLE, ResultErrorCodes.parseHttpCode(503));
    }

}
