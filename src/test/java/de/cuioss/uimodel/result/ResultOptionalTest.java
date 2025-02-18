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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import de.cuioss.test.valueobjects.contract.SerializableContractImpl;
import de.cuioss.uimodel.nameprovider.DisplayName;

class ResultOptionalTest {

    @Test
    void empty() {
        var result = new ResultOptional<String>(null, ResultState.VALID);
        assertTrue(result.isValid());
        assertFalse(result.getResult().isPresent());
        assertFalse(result.getResultDetail().isPresent());
        assertFalse(result.getErrorCode().isPresent());
        assertEquals(result, SerializableContractImpl.serializeAndDeserialize(result));
    }

    @Test
    void builder() {
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
    void builderWithoutState() {
        var resultBuilder = ResultOptional.optionalBuilder().result("Test");
        assertThrows(UnsupportedOperationException.class, () -> resultBuilder.build());
    }

    @Test
    void builderWithErrorAndWithoutDetail() {
        var resultBuilder = ResultOptional.optionalBuilder().result("Test").state(ResultState.ERROR);
        assertThrows(UnsupportedOperationException.class, () -> resultBuilder.build());
    }

    @Test
    void builderWithDetailAndErrorCode() {
        ResultOptional.Builder<String> resultBuilder = ResultOptional.optionalBuilder();
        var result = resultBuilder.state(ResultState.ERROR).resultDetail(new ResultDetail(new DisplayName("Test")))
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
    void builderWithTwoDetails() {
        ResultOptional.Builder<String> resultBuilder = ResultOptional.optionalBuilder();
        var result = resultBuilder.state(ResultState.ERROR).resultDetail(new ResultDetail(new DisplayName("Test")))
                .resultDetail(new ResultDetail(new DisplayName("Test2"))).build();
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
        var copy = new ResultOptional<>(expected, Function.identity());
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
        var copy = new ResultOptional<>(expected, Function.identity());
        assertEquals(expected.getResult(), copy.getResult());
        assertEquals(expected.getErrorCode(), copy.getErrorCode());
        assertEquals(expected.getState(), copy.getState());
        assertEquals(expected.getResultDetail(), copy.getResultDetail());
    }

    @Test
    void shouldHandleCopyBuilderForValid() {
        ResultOptional.Builder<String> resultBuilder = ResultOptional.optionalBuilder();
        var expected = resultBuilder.result("Test").state(ResultState.VALID).build();
        var copy = ResultOptional.optionalBuilder().extractStateAndDetailsAndErrorCodeFrom(expected).result("Test")
                .build();
        assertEquals(expected.getResult(), copy.getResult());
        assertEquals(expected.getErrorCode(), copy.getErrorCode());
        assertEquals(expected.getState(), copy.getState());
        assertEquals(expected.getResultDetail(), copy.getResultDetail());
    }
}
