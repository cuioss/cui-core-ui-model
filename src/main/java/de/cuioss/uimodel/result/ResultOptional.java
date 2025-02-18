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

import static java.util.Objects.requireNonNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;
import java.util.function.Function;

import de.cuioss.tools.logging.CuiLogger;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A {@link ResultObject} that can store results that are not mandatory from the
 * domain context. E.g. the parent of a node object that can also be a root node
 * without a parent. To allow serialization the result will be stored as
 * nullable object and transformed in an {@link Optional} for
 * {@link ResultObject#getResult()}.
 *
 * @param <T> identifying the type of the result
 */
@ToString(callSuper = true, of = "result", doNotUseGetters = true)
@EqualsAndHashCode(callSuper = true, of = "result", doNotUseGetters = true)
public class ResultOptional<T extends Serializable> extends ResultObject<Optional<T>> {

    @Serial
    private static final long serialVersionUID = 4619738393641630076L;

    private final T result;

    /**
     * @param result       is mandatory
     * @param state        {@linkplain ResultState} is mandatory
     * @param resultDetail is mandatory if requestResultState is not
     *                     {@linkplain ResultState#VALID}
     * @param errorCode
     *
     * @throws IllegalArgumentException if any condition is violated
     */
    public ResultOptional(final T result, final ResultState state, final ResultDetail resultDetail,
            final Enum<?> errorCode) {
        super(state, resultDetail, errorCode);

        this.result = result;
    }

    /**
     * @param <R>            identifying the type of the result
     * @param previousResult to be copied from
     * @param mapper         used for transforming the previous result into the
     *                       current
     */
    public <R extends Serializable> ResultOptional(ResultOptional<R> previousResult, Function<R, T> mapper) {
        super(previousResult.getState(), previousResult.getResultDetail().orElse(null),
                previousResult.getErrorCode().orElse(null));
        if (previousResult.isValid() && previousResult.getResult().isPresent()) {
            result = mapper.apply(previousResult.result);
        } else {
            result = null;
        }
    }

    /**
     * @param result is mandatory
     * @param state  {@linkplain ResultState} is mandatory
     *
     * @throws IllegalArgumentException if any condition is violated
     */
    public ResultOptional(final T result, final ResultState state) {
        this(result, state, null, null);
    }

    @Override
    public Optional<T> getResult() {
        return Optional.ofNullable(result);
    }

    /**
     * Builder factory method
     *
     * @return new created typed builder
     */
    public static <R extends Serializable> ResultOptional.Builder<R> optionalBuilder() {
        return new ResultOptional.Builder<>();
    }

    /**
     * ResultObject Builder
     *
     * @param <S> bounded result type
     */
    public static class Builder<S extends Serializable> {

        private static final String THE_RESULT_DETAIL_IS_MANDATORY = "The ResultDetail is mandatory if state is not VALID.";

        private static final String STATE_IS_NOT_AVAILABLE = "It make no sense to continue because state is not available!";
        private static final String ALREADY_FAILED = "Already failed: ";

        private S tempResult;

        private ResultState tempState;

        private ResultDetail tempRequestResultDetail;

        private Enum<?> tempErrorCode;

        /**
         * @param result is mandatory
         *
         * @return {@linkplain de.cuioss.uimodel.result.ResultOptional.Builder} in
         *         fluent api style
         */
        public ResultOptional.Builder<S> result(final S result) {
            tempResult = result;
            return this;
        }

        /**
         * @param state is mandatory
         *
         * @return {@linkplain de.cuioss.uimodel.result.ResultOptional.Builder} in
         *         fluent api style
         */
        public ResultOptional.Builder<S> state(final ResultState state) {
            tempState = requireNonNull(state, "state");
            return this;
        }

        /**
         * @param resultDetail is optional if state is {@linkplain ResultState#VALID}
         *
         * @return {@linkplain ResultObject.Builder} in fluent api style
         */
        public ResultOptional.Builder<S> resultDetail(final ResultDetail resultDetail) {
            ResultObject.logDetail(ALREADY_FAILED, tempState, tempRequestResultDetail,
                    new CuiLogger(ResultOptional.Builder.class));
            tempRequestResultDetail = resultDetail;
            return this;
        }

        /**
         * @param errorCode is optional if state is {@linkplain ResultState#VALID}
         *
         * @return {@linkplain ResultObject.Builder} in fluent api style
         */
        public ResultOptional.Builder<S> errorCode(Enum<?> errorCode) {
            tempErrorCode = errorCode;
            return this;
        }

        /**
         * Reuse {@linkplain ResultState} and {@linkplain ResultObject} from previous
         * {@linkplain ResultObject}
         *
         * @param previousResult {@linkplain ResultObject} must not be {@code null}
         *
         * @return {@linkplain ResultObject.Builder} in fluent api style
         */
        public ResultOptional.Builder<S> extractStateAndDetailsAndErrorCodeFrom(final ResultObject<?> previousResult) {
            requireNonNull(previousResult, "PreviousResult must not be null");
            return state(previousResult.getState()).resultDetail(previousResult.getResultDetail().orElse(null))
                    .errorCode(previousResult.getErrorCode().orElse(null));
        }

        /**
         * <h4>Preconditions</h4>
         * <ul>
         * <li>result or validDefaultResult as fall-back must be available</li>
         * <li>state must be available</li>
         * <li>if state is not {@linkplain ResultState#VALID} requestResultDetail must
         * be available</li>
         * </ul>
         *
         * @return {@linkplain ResultObject} if possible
         * @throws UnsupportedOperationException if you try to ignore conditions
         */
        public ResultOptional<S> build() {

            if (null == tempState) {
                throwUnsupportedOperationExceptionAndSaveThePreviousError(STATE_IS_NOT_AVAILABLE);
            }

            if (!ResultState.VALID.equals(tempState) && null == tempRequestResultDetail) {
                throwUnsupportedOperationExceptionAndSaveThePreviousError(THE_RESULT_DETAIL_IS_MANDATORY);
            }

            return new ResultOptional<>(tempResult, tempState, tempRequestResultDetail, tempErrorCode);

        }

        @SuppressWarnings("squid:S3655") // owolff: IsPresent is explicitly checked
        private void throwUnsupportedOperationExceptionAndSaveThePreviousError(final String errorMsg) {
            if (null != tempRequestResultDetail && tempRequestResultDetail.getCause().isPresent()) {
                final var newErrorMsg = errorMsg + "\nThere exits additional error occurs before :";
                throw new UnsupportedOperationException(newErrorMsg, tempRequestResultDetail.getCause().get());
            }
            throw new UnsupportedOperationException(errorMsg);
        }
    }
}
