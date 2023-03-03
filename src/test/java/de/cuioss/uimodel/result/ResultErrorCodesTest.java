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
     * Test method for {@link de.cuioss.uimodel.result.ResultErrorCodes#parseHttpCode(int)}.
     */
    @Test
    void testParseHttpCode() {
        assertEquals(RUNTIME_ERROR, ResultErrorCodes.parseHttpCode(0));
        assertEquals(RUNTIME_ERROR, ResultErrorCodes.parseHttpCode(500));

        assertEquals(BAD_REQUEST, ResultErrorCodes.parseHttpCode(400));
        assertEquals(NOT_AUTHENTICATED, ResultErrorCodes.parseHttpCode(401));
        assertEquals(NOT_AUTHORIZED, ResultErrorCodes.parseHttpCode(403));

        assertEquals(NOT_FOUND, ResultErrorCodes.parseHttpCode(404));
        assertEquals(SERVICE_NOT_AVAILABLE, ResultErrorCodes.parseHttpCode(503));
    }

}
