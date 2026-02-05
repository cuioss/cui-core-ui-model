/*
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

import de.cuioss.tools.logging.CuiLogger;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * A specialized {@link ResultObject} that integrates Java's {@link Optional} pattern
 * for handling nullable results in a type-safe manner. This class is particularly
 * useful for domain objects that may legitimately be absent, such as optional
 * relationships or nullable attributes.
 *
 * <h2>Key Features</h2>
 * <ul>
 *   <li>Optional-based result handling</li>
 *   <li>Type-safe operations</li>
 *   <li>Serialization support</li>
 *   <li>Result transformation capabilities</li>
 *   <li>Builder pattern support</li>
 * </ul>
 *
 * <h2>Usage Patterns</h2>
 *
 * <h3>1. Basic Optional Result</h3>
 * <pre>
 * ResultOptional&lt;User&gt; result = service.findUser(id);
 * result.getResult()
 *       .ifPresent(user -> displayUser(user));
 * </pre>
 *
 * <h3>2. Using Builder Pattern</h3>
 * <pre>
 * ResultOptional&lt;Document&gt; result = ResultOptional.&lt;Document&gt;optionalBuilder()
 *     .result(document)
 *     .state(ResultState.VALID)
 *     .build();
 * </pre>
 *
 * <h3>3. Result Transformation</h3>
 * <pre>
 * ResultOptional&lt;User&gt; userResult = service.findUser(id);
 * ResultOptional&lt;String&gt; nameResult =
 *     new ResultOptional&lt;&gt;(userResult, User::getName);
 * </pre>
 *
 * <h3>4. Error Handling</h3>
 * <pre>
 * ResultOptional&lt;Resource&gt; result = service.findResource(id);
 * if (!result.isValid()) {
 *     handleError(result.getResultDetail());
 * } else {
 *     result.getResult()
 *           .ifPresentOrElse(
 *               this::processResource,
 *               () -> handleNotFound()
 *           );
 * }
 * </pre>
 *
 * <h2>Implementation Notes</h2>
 * <ul>
 *   <li><strong>Serialization:</strong>
 *     <ul>
 *       <li>Stores result as nullable object internally</li>
 *       <li>Transforms to Optional on access</li>
 *       <li>Type parameter must be Serializable</li>
 *     </ul>
 *   </li>
 *   <li><strong>State Management:</strong>
 *     <ul>
 *       <li>Maintains result state independent of presence</li>
 *       <li>Supports all ResultState values</li>
 *       <li>Error details available even for empty results</li>
 *     </ul>
 *   </li>
 *   <li><strong>Thread Safety:</strong>
 *     <ul>
 *       <li>Immutable after construction</li>
 *       <li>Safe for concurrent access</li>
 *       <li>No side effects in accessors</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <h2>Best Practices</h2>
 * <ul>
 *   <li>Use for naturally optional domain relationships</li>
 *   <li>Prefer builder pattern for complex construction</li>
 *   <li>Handle both validity and presence separately</li>
 *   <li>Leverage transformation for type conversion</li>
 *   <li>Consider error states for absent values</li>
 * </ul>
 *
 * @param <T> The type of the optional result. Must implement {@link Serializable}
 *            for proper serialization support.
 * @author Eugen Fischer
 * @see ResultObject
 * @see Optional
 * @see Serializable
 * @since 1.0
 */
@ToString(callSuper = true, of = "result", doNotUseGetters = true)
@EqualsAndHashCode(callSuper = true, of = "result", doNotUseGetters = true)
public class ResultOptional<T extends Serializable> extends ResultObject<Optional<T>> {

    @Serial
    private static final long serialVersionUID = 4619738393641630076L;

    /**
     * The actual result value, which may be null. This is transformed into
     * an Optional when accessed via {@link #getResult()}.
     */
    private final T result;

    /**
     * Creates a new ResultOptional with the specified result and state details.
     *
     * @param result       The result value, may be null
     * @param state        The state of the result, must not be null
     * @param resultDetail Required if state is not VALID
     * @param errorCode    Optional error code for additional context
     * @throws IllegalArgumentException if required parameters are invalid
     */
    public ResultOptional(final T result, final ResultState state, final ResultDetail resultDetail,
            final Enum<?> errorCode) {
        super(state, resultDetail, errorCode);
        this.result = result;
    }

    /**
     * Creates a new ResultOptional by transforming an existing result using the
     * provided mapping function.
     *
     * @param <R>            The type of the previous result
     * @param previousResult The result to transform from
     * @param mapper         The function to transform the result
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
     * Creates a new ResultOptional with the specified result and state.
     *
     * @param result The result value, may be null
     * @param state  The state of the result, must not be null
     * @throws IllegalArgumentException if state is null
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
         * @return {@linkplain de.cuioss.uimodel.result.ResultOptional.Builder} in
         * fluent api style
         */
        public ResultOptional.Builder<S> result(final S result) {
            tempResult = result;
            return this;
        }

        /**
         * @param state is mandatory
         * @return {@linkplain de.cuioss.uimodel.result.ResultOptional.Builder} in
         * fluent api style
         */
        public ResultOptional.Builder<S> state(final ResultState state) {
            tempState = requireNonNull(state, "state");
            return this;
        }

        /**
         * @param resultDetail is optional if state is {@linkplain ResultState#VALID}
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
