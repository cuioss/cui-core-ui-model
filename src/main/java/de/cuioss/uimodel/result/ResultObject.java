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

import static de.cuioss.tools.collect.MoreCollections.isEmpty;
import static de.cuioss.tools.string.MoreStrings.nullToEmpty;
import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Function;

import de.cuioss.tools.logging.CuiLogger;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * <h2>Generic ResultObject</h2> ResultObject provide Builder which supports a
 * handy object creation.
 *
 * See example implementation for provider:
 *
 * <pre>
 *
 * public ResultObject&lt;Patient&gt; retrievePatient(final SearchParameter param) {
 *
 *     final Builder&lt;Patient&gt; builder = new ResultObject.Builder<>();
 *     builder.validDefaultResult(new Patient());
 *
 *     try {
 *         builder.result(service.lookupPatient(param)).state(ResultState.VALID);
 *     } catch (final NoSuchPatientException e) {
 *         private IDisplayNameProvider&lt;?&gt; detail = new DisplayName("No patient found");
 *         builder.resultDetail(new ResultDetail(detail, e)).state(ResultState.ERROR);
 *     }
 *
 *     return builder.build();
 * }
 * </pre>
 *
 * See example implementation for consumer:
 *
 * <pre>
 *
 * public void buttonClicked() {
 *
 *     final ResultObject&lt;Patient&gt; result = service.getPatient();
 *     if (!result.isValid()) {
 *         new MessageProducerAccessor().getValue().addGlobalMessage(resolve(content), FacesMessage.SEVERITY_WARN);
 *     } else {
 *         this.patient = result.getResult();
 *     }
 * }
 * </pre>
 *
 * @param <T> bounded result type, should implement {@link Serializable}
 *
 * @author Eugen Fischer
 */
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true)
public class ResultObject<T> implements Serializable {

    private static final String STATE_MESSAGE = "state";

    private static final String RESULT_MESSAGE = "result";

    private static final long serialVersionUID = -80595591786771138L;

    private static final String HANDLE_EXCEPTION_FIRST = "ResultObject include error which you must handle first. See";

    private static final String REQUEST_RESULT_DETAIL_IS_MANDATORY = "Result state is [%s], so request result detail is mandatory";

    /**
     * provide non null result
     */
    @SuppressWarnings("squid:S1948") // T should implement {@link Serializable}
    private final T result;

    /**
     * Represents the state of result
     */
    @Getter
    private final ResultState state;

    /**
     * optional additional details
     */
    private final ResultDetail resultDetail;

    /**
     * optional error code
     */
    private final Enum<?> errorCode;

    /**
     * flag remember at least one time access to stateDetail
     */
    private boolean resultDetailPrompted = false;

    /**
     * flag remember at least one time access to errorCode
     */
    private boolean errorCodePrompted = false;

    /**
     * @param result       is mandatory
     * @param state        {@linkplain ResultState} is mandatory
     * @param resultDetail is mandatory if state is not
     *                     {@linkplain ResultState#VALID}
     *
     * @throws IllegalArgumentException if any condition is violated
     */
    public ResultObject(final T result, final ResultState state, final ResultDetail resultDetail) {
        this(result, state, resultDetail, null);
    }

    /**
     * @param result       is mandatory
     * @param state        {@linkplain ResultState} is mandatory
     * @param resultDetail is mandatory if state is not
     *                     {@linkplain ResultState#VALID}
     * @param errorCode    optional errorCode for backend calls
     *
     * @throws IllegalArgumentException if any condition is violated
     */
    public ResultObject(final T result, final ResultState state, final ResultDetail resultDetail,
            final Enum<?> errorCode) {

        this.result = checkArgumentNotNull(result, RESULT_MESSAGE);
        this.state = checkArgumentNotNull(state, STATE_MESSAGE);
        this.resultDetail = resultDetail;
        this.errorCode = errorCode;

        if (!isValid() && null == resultDetail) {
            throw new IllegalArgumentException(REQUEST_RESULT_DETAIL_IS_MANDATORY.formatted(this.state));
        }
    }

    static <T> T checkArgumentNotNull(final T toBeChecked, final String attributeName) {
        if (null == toBeChecked) {
            throw new IllegalArgumentException("Attribute with name " + attributeName + " must not be null");
        }
        return toBeChecked;
    }

    /**
     * Copy constructor. For the result it either maps the existing result using the
     * given <code>mapper</code> or it uses the <code>validDefault</code>, if the
     * <code>previousResult</code> is not {@link #isValid()}.
     *
     * @param previousResult to be copied
     * @param mapper         to map previousResult to the outcome type of this
     *                       method
     * @param validDefault   to be used if previousResult is not
     *                       {@link ResultObject#isValid()}
     * @param <R>            result type
     */
    public <R> ResultObject(final ResultObject<R> previousResult, final Function<R, T> mapper, final T validDefault) {
        if (previousResult.isValid()) {
            result = mapper.apply(previousResult.result);
        } else {
            result = validDefault;
        }
        state = previousResult.state;
        resultDetail = previousResult.resultDetail;
        errorCode = previousResult.errorCode;
    }

    /**
     * @param result is mandatory
     * @param state  {@linkplain ResultState} is mandatory
     *
     * @throws IllegalArgumentException if any condition is violated
     */
    public ResultObject(final T result, final ResultState state) {
        this(result, state, null, null);
    }

    /**
     * @param result   is mandatory
     * @param copyFrom to copy state and detail and error from
     */
    public ResultObject(final T result, final ResultObject<?> copyFrom) {
        this(result, copyFrom.state, copyFrom.resultDetail, copyFrom.errorCode);
    }

    /**
     * To be used from extending classes with specific handling of the result.
     *
     * @param state
     * @param resultDetail
     * @param errorCode
     */
    ResultObject(final ResultState state, final ResultDetail resultDetail, final Enum<?> errorCode) {

        result = null;

        this.state = checkArgumentNotNull(state, STATE_MESSAGE);

        if (ResultState.VALID != this.state && null == resultDetail) {
            throw new IllegalArgumentException(REQUEST_RESULT_DETAIL_IS_MANDATORY.formatted(this.state));
        }

        // null object for Detail supported here
        this.resultDetail = resultDetail;

        this.errorCode = errorCode;
    }

    /**
     * Checks the state.
     *
     * @return true if state == RequestResultState.VALID.
     */
    public boolean isValid() {
        return ResultState.VALID.equals(state);
    }

    /**
     * @return typed result object
     * @throws UnsupportedOperationException if RequestResultObject has state which
     *                                       must be handled before
     */
    public T getResult() {
        if (ResultState.MUST_BE_HANDLED.contains(state) && !resultDetailPrompted && !errorCodePrompted) {
            throw new UnsupportedOperationException(HANDLE_EXCEPTION_FIRST, resultDetail.getCause().orElse(null));
        }

        return result;
    }

    /**
     *
     * @return {@linkplain Optional} containing a {@linkplain ResultDetail} if
     *         available, {@linkplain Optional#empty()} otherwise
     */
    public Optional<ResultDetail> getResultDetail() {
        resultDetailPrompted = true;
        return Optional.ofNullable(resultDetail);
    }

    /**
     * @return {@linkplain Optional} containing a {@linkplain Enum} as error code if
     *         available, {@linkplain Optional#empty()} otherwise
     */
    @SuppressWarnings("java:S1452") // owolff: currently we allow any enum here
    public Optional<Enum<?>> getErrorCode() {
        errorCodePrompted = true;
        return Optional.ofNullable(errorCode);
    }

    /**
     * @param logPrefix to be appended to the log message. no additional space is
     *                  added between prefix and message.
     * @param log       the logger
     */
    public void logDetail(final String logPrefix, final CuiLogger log) {
        logDetail(logPrefix, state, resultDetail, log);
        resultDetailPrompted = true;
    }

    protected static void logDetail(final String logPrefix, final ResultState state, final ResultDetail detail,
            final CuiLogger log) {
        if (null != detail) {
            final var msg = nullToEmpty(logPrefix) + detail.getDetail();
            if (null != state) {
                final var throwable = detail.getCause().orElse(null);
                switch (state) {
                case VALID:
                    log.debug(msg, throwable);
                    break;
                case INFO:
                    log.info(msg, throwable);
                    break;
                case WARNING:
                    log.warn(msg, throwable);
                    break;
                case ERROR:
                    log.error(msg, throwable);
                    break;
                }
            } else {
                log.debug(msg, detail.getCause());
            }
        }
    }

    /**
     * Shorthand for checking whether the given requestResultObject contains the
     * given strategy
     *
     * @param errorCode to be checked
     *
     * @return an Boolean indicating whether the given requestResultObject contains
     *         the given strategy
     */
    public boolean containsErrorCode(final Enum<?>... errorCode) {
        if (isEmpty((Object[]) errorCode)) {
            return false;
        }
        for (final Enum<?> aErrorCode : errorCode) {
            if (aErrorCode == this.errorCode) {
                return true;
            }
        }
        return false;
    }

    /**
     * Builder factory method
     *
     * @param <R> the result type
     *
     * @return new created typed builder
     */
    public static <R> ResultObject.Builder<R> builder() {
        return new ResultObject.Builder<>();
    }

    /**
     * ResultObject Builder
     *
     * @param <S> bounded result type
     */
    public static class Builder<S> {

        private static final CuiLogger log = new CuiLogger(ResultObject.Builder.class);

        private static final String ALREADY_FAILED = "Already failed: ";

        private static final String THE_RESULT_DETAIL_IS_MANDATORY = "The ResultDetail is mandatory if state is not VALID.";

        private static final String STATE_IS_NOT_AVAILABLE = "It make no sense to continue because state is not available!";

        private static final String NO_RESULTS_AVAILABLE = "It make no sense to continue because no results available. Use setResult or setValidDefaultResult as fallback.";

        private S tempResult;

        private S tempValidDefaultResult;

        private ResultState tempState;

        private ResultDetail tempRequestResultDetail;

        private Enum<?> tempErrorCode;

        /**
         * Valid default result will be automatically used if no result exists
         *
         * @param result is mandatory
         *
         * @return {@linkplain ResultObject.Builder} in fluent api style
         */
        public ResultObject.Builder<S> validDefaultResult(final S result) {
            tempValidDefaultResult = checkArgumentNotNull(result, RESULT_MESSAGE);
            return this;
        }

        /**
         * @param result is mandatory
         *
         * @return {@linkplain ResultObject.Builder} in fluent api style
         */
        public ResultObject.Builder<S> result(final S result) {
            tempResult = checkArgumentNotNull(result, RESULT_MESSAGE);
            return this;
        }

        /**
         * @param state is mandatory
         *
         * @return {@linkplain ResultObject.Builder} in fluent api style
         */
        public ResultObject.Builder<S> state(final ResultState state) {
            tempState = checkArgumentNotNull(state, STATE_MESSAGE);
            return this;
        }

        /**
         * @param resultDetail is optional if state is {@linkplain ResultState#VALID}
         *
         * @return {@linkplain ResultObject.Builder} in fluent api style
         */
        public ResultObject.Builder<S> resultDetail(final ResultDetail resultDetail) {
            logDetail(ALREADY_FAILED, tempState, tempRequestResultDetail, log);
            tempRequestResultDetail = resultDetail;
            return this;
        }

        /**
         * @param errorCode is optional if state is {@linkplain ResultState#VALID}
         *
         * @return {@linkplain ResultObject.Builder} in fluent api style
         */
        public ResultObject.Builder<S> errorCode(final Enum<?> errorCode) {
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
        public ResultObject.Builder<S> extractStateAndDetailsAndErrorCodeFrom(final ResultObject<?> previousResult) {
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
        public ResultObject<S> build() {

            if (null == tempResult && null == tempValidDefaultResult) {
                throwUnsupportedOperationExceptionAndSaveThePreviousError(NO_RESULTS_AVAILABLE);
            }

            if (null == tempState) {
                throwUnsupportedOperationExceptionAndSaveThePreviousError(STATE_IS_NOT_AVAILABLE);
            }

            if (!ResultState.VALID.equals(tempState) && null == tempRequestResultDetail) {
                throwUnsupportedOperationExceptionAndSaveThePreviousError(THE_RESULT_DETAIL_IS_MANDATORY);
            }

            // only fallback available
            if (null == tempResult) {

                if (ResultState.VALID.equals(tempState)) {
                    log.debug("Are you really sure you want to use fallback value as result for valid response?");
                }

                return new ResultObject<>(tempValidDefaultResult, tempState, tempRequestResultDetail, tempErrorCode);
            }

            return new ResultObject<>(tempResult, tempState, tempRequestResultDetail, tempErrorCode);

        }

        @SuppressWarnings("java:S3655") // owolff: False positive: isPresent is called
        private void throwUnsupportedOperationExceptionAndSaveThePreviousError(final String errorMsg) {
            if (null != tempRequestResultDetail && tempRequestResultDetail.getCause().isPresent()) {
                final var newErrorMsg = errorMsg + "\nThere exits additional error occurs before :";
                throw new UnsupportedOperationException(newErrorMsg, tempRequestResultDetail.getCause().get());
            }
            throw new UnsupportedOperationException(errorMsg);
        }
    }
}
