package io.cui.core.uimodel.result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import io.cui.core.uimodel.nameprovider.DisplayName;
import io.cui.test.valueobjects.contract.SerializableContractImpl;

class ResultOptionalTest {

    @Test
    void testEmpty() {
        var result = new ResultOptional<String>(null, ResultState.VALID);
        assertTrue(result.isValid());
        assertFalse(result.getResult().isPresent());
        assertFalse(result.getResultDetail().isPresent());
        assertFalse(result.getErrorCode().isPresent());
        assertEquals(result, SerializableContractImpl.serializeAndDeserialize(result));
    }

    @Test
    void testBuilder() {
        ResultOptional.Builder<String> resultBuilder = ResultOptional.optionalBuilder();
        var result = resultBuilder.result("Test").state(ResultState.VALID).build();
        assertTrue(result.isValid());
        assertTrue(result.getResult().isPresent());
        assertEquals("Test", result.getResult().get());
        assertFalse(result.getResultDetail().isPresent());
        assertFalse(result.getErrorCode().isPresent());
        assertEquals(result, SerializableContractImpl.serializeAndDeserialize(result));
    }

    @Test
    void testBuilderWithoutState() {
        var resultBuilder = ResultOptional.optionalBuilder().result("Test");
        assertThrows(UnsupportedOperationException.class, () -> resultBuilder.build());
    }

    @Test
    void testBuilderWithErrorAndWithoutDetail() {
        var resultBuilder = ResultOptional.optionalBuilder().result("Test").state(ResultState.ERROR);
        assertThrows(UnsupportedOperationException.class, () -> resultBuilder.build());
    }

    @Test
    void testBuilderWithDetailAndErrorCode() {
        ResultOptional.Builder<String> resultBuilder = ResultOptional.optionalBuilder();
        var result =
            resultBuilder.state(ResultState.ERROR).resultDetail(new ResultDetail(new DisplayName("Test")))
                    .errorCode(ResultErrorCodes.NOT_FOUND).build();
        assertFalse(result.isValid());
        assertFalse(result.getResult().isPresent());
        assertTrue(result.getResultDetail().isPresent());
        assertEquals(new ResultDetail(new DisplayName("Test")), result.getResultDetail().get());
        assertTrue(result.getErrorCode().isPresent());
        assertEquals(ResultErrorCodes.NOT_FOUND, result.getErrorCode().get());
        assertEquals(result, SerializableContractImpl.serializeAndDeserialize(result));
    }

    @Test
    void testBuilderWithTwoDetails() {
        ResultOptional.Builder<String> resultBuilder = ResultOptional.optionalBuilder();
        var result =
            resultBuilder.state(ResultState.ERROR).resultDetail(new ResultDetail(new DisplayName("Test")))
                    .resultDetail(new ResultDetail(new DisplayName("Test2")))
                    .build();
        assertFalse(result.isValid());
        assertFalse(result.getResult().isPresent());
        assertTrue(result.getResultDetail().isPresent());
        assertEquals(new ResultDetail(new DisplayName("Test2")), result.getResultDetail().get());
        assertFalse(result.getErrorCode().isPresent());
        assertEquals(result, SerializableContractImpl.serializeAndDeserialize(result));
    }

    @Test
    void shouldHandleCopyConstructorForValid() {
        ResultOptional.Builder<String> resultBuilder = ResultOptional.optionalBuilder();
        var expected = resultBuilder.result("Test").state(ResultState.VALID).build();
        var copy = new ResultOptional<String>(expected, Function.identity());
        assertEquals(expected.getResult(), copy.getResult());
        assertEquals(expected.getErrorCode(), copy.getErrorCode());
        assertEquals(expected.getState(), copy.getState());
        assertEquals(expected.getResultDetail(), copy.getResultDetail());
    }

    @Test
    void shouldHandleCopyConstructorForError() {
        ResultOptional.Builder<String> resultBuilder = ResultOptional.optionalBuilder();
        var expected = resultBuilder.state(ResultState.ERROR).resultDetail(new ResultDetail(new DisplayName("Test")))
                .errorCode(ResultErrorCodes.NOT_FOUND).build();
        var copy = new ResultOptional<String>(expected, Function.identity());
        assertEquals(expected.getResult(), copy.getResult());
        assertEquals(expected.getErrorCode(), copy.getErrorCode());
        assertEquals(expected.getState(), copy.getState());
        assertEquals(expected.getResultDetail(), copy.getResultDetail());
    }

    @Test
    void shouldHandleCopyBuilderForValid() {
        ResultOptional.Builder<String> resultBuilder = ResultOptional.optionalBuilder();
        var expected = resultBuilder.result("Test").state(ResultState.VALID).build();
        var copy =
            ResultOptional.optionalBuilder().extractStateAndDetailsAndErrorCodeFrom(expected).result("Test").build();
        assertEquals(expected.getResult(), copy.getResult());
        assertEquals(expected.getErrorCode(), copy.getErrorCode());
        assertEquals(expected.getState(), copy.getState());
        assertEquals(expected.getResultDetail(), copy.getResultDetail());
    }
}
