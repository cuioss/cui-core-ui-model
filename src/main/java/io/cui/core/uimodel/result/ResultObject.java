package io.cui.core.uimodel.result;

import static io.cui.tools.string.MoreStrings.nullToEmpty;
import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Function;

import io.cui.tools.logging.CuiLogger;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Generic ResultObject<br/>
 * ResultObject provide Builder which supports a handy object creation.<br/>
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
 *         builder.result(service.lookupPatient(param))
 *                 .state(ResultState.VALID);
 *     } catch (final NoSuchPatientException e) {
 *         private IDisplayNameProvider&lt;?&gt; detail = new DisplayName("No patient found");
 *         builder.resultDetail(new ResultDetail(detail, e))
 *                 .state(ResultState.ERROR);
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

    private static final String HANDLE_EXCEPTION_FIRST =
        "ResultObject include error which you must handle first. See";

    private static final String REQUEST_RESULT_DETAIL_IS_MANDATORY =
        "Result state is [%s], so request result detail is mandatory";

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
     * @param result is mandatory
     * @param state {@linkplain ResultState} is mandatory
     * @param resultDetail is mandatory if state is not {@linkplain ResultState#VALID}
     *
     * @throws IllegalArgumentException if any condition is violated
     */
    public ResultObject(
            final T result,
            final ResultState state,
            final ResultDetail resultDetail) {
        this(result, state, resultDetail, null);
    }

    /**
     * @param result is mandatory
     * @param state {@linkplain ResultState} is mandatory
     * @param resultDetail is mandatory if state is not {@linkplain ResultState#VALID}
     * @param errorCode optional errorCode for backend calls
     *
     * @throws IllegalArgumentException if any condition is violated
     */
    public ResultObject(
            final T result,
            final ResultState state,
            final ResultDetail resultDetail,
            final Enum<?> errorCode) {

        this.result = checkArgumentNotNull(result, RESULT_MESSAGE);
        this.state = checkArgumentNotNull(state, STATE_MESSAGE);
        this.resultDetail = resultDetail;
        this.errorCode = errorCode;

        if (!isValid() && null == resultDetail) {
            throw new IllegalArgumentException(
                    String.format(REQUEST_RESULT_DETAIL_IS_MANDATORY, this.state));
        }
    }

    static <T> T checkArgumentNotNull(final T toBeChecked, final String attributeName) {
        if (null == toBeChecked) {
            throw new IllegalArgumentException(
                    "Attribute with name " + attributeName + " must not be null");
        }
        return toBeChecked;
    }

    /**
     * Copy constructor. For the result it either maps the existing result using the given
     * <code>mapper</code> or it uses the <code>validDefault</code>, if the
     * <code>previousResult</code> is not {@link #isValid()}.
     *
     * @param previousResult to be copied
     * @param mapper to map previousResult to the outcome type of this method
     * @param validDefault to be used if previousResult is not {@link ResultObject#isValid()}
     * @param <R> result type
     */
    public <R> ResultObject(
            final ResultObject<R> previousResult, final Function<R, T> mapper, final T validDefault) {
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
     * @param state {@linkplain ResultState} is mandatory
     *
     * @throws IllegalArgumentException if any condition is violated
     */
    public ResultObject(final T result, final ResultState state) {
        this(result, state, null, null);
    }

    /**
     * @param result is mandatory
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

        this.result = null;

        this.state = checkArgumentNotNull(state, STATE_MESSAGE);

        if (ResultState.VALID.equals(this.state)) {

        } else if (null == resultDetail) {
            throw new IllegalArgumentException(
                    String.format(REQUEST_RESULT_DETAIL_IS_MANDATORY, this.state));
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
        return ResultState.VALID.equals(this.state);
    }

    /**
     * @return typed result object
     * @throws UnsupportedOperationException if RequestResultObject has state which must be handled
     *             before
     */
    public T getResult() {
        if (ResultState.MUST_BE_HANDLED.contains(this.state) && !this.resultDetailPrompted && !this.errorCodePrompted) {
            throw new UnsupportedOperationException(HANDLE_EXCEPTION_FIRST, this.resultDetail.getCause().orElse(null));
        }

        return this.result;
    }

    /**
     *
     * @return {@linkplain Optional} containing a {@linkplain ResultDetail} if available,
     *         {@linkplain Optional#empty()} otherwise
     */
    public Optional<ResultDetail> getResultDetail() {
        this.resultDetailPrompted = true;
        return Optional.ofNullable(this.resultDetail);
    }

    /**
     * @return {@linkplain Optional} containing a {@linkplain Enum} as error code if available,
     *         {@linkplain Optional#empty()} otherwise
     */
    public Optional<Enum<?>> getErrorCode() {
        errorCodePrompted = true;
        return Optional.ofNullable(this.errorCode);
    }

    /**
     * @param logPrefix to be appended to the log message. no additional space is added between
     *            prefix and message.
     * @param log the logger
     */
    public void logDetail(final String logPrefix, final CuiLogger log) {
        logDetail(logPrefix, this.state, this.resultDetail, log);
        this.resultDetailPrompted = true;
    }

    protected static void logDetail(
            final String logPrefix, final ResultState state, final ResultDetail detail, final CuiLogger log) {
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
     * Shorthand for checking whether the given requestResultObject contains the given strategy
     *
     * @param errorCode to be checked
     *
     * @return an Boolean indicating whether the given requestResultObject contains the given
     *         strategy
     */
    public boolean containsErrorCode(final Enum<?>... errorCode) {
        if (null == this.errorCode || null == errorCode || 0 == errorCode.length) {
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

        private static final String THE_RESULT_DETAIL_IS_MANDATORY =
            "The ResultDetail is mandatory if state is not VALID.";

        private static final String STATE_IS_NOT_AVAILABLE =
            "It make no sense to continue because state is not available!";

        private static final String NO_RESULTS_AVAILABLE =
            "It make no sense to continue because no results available. Use setResult or setValidDefaultResult as fallback.";

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
         * @return {@linkplain ResultObject.Builder} in fluent api
         *         style
         */
        public ResultObject.Builder<S> validDefaultResult(final S result) {
            this.tempValidDefaultResult = checkArgumentNotNull(result, RESULT_MESSAGE);
            return this;
        }

        /**
         * @param result is mandatory
         *
         * @return {@linkplain ResultObject.Builder} in fluent api
         *         style
         */
        public ResultObject.Builder<S> result(final S result) {
            this.tempResult = checkArgumentNotNull(result, RESULT_MESSAGE);
            return this;
        }

        /**
         * @param state is mandatory
         *
         * @return {@linkplain ResultObject.Builder} in fluent api
         *         style
         */
        public ResultObject.Builder<S> state(final ResultState state) {
            this.tempState = checkArgumentNotNull(state, STATE_MESSAGE);
            return this;
        }

        /**
         * @param resultDetail is optional if state is
         *            {@linkplain ResultState#VALID}
         *
         * @return {@linkplain ResultObject.Builder} in fluent api
         *         style
         */
        public ResultObject.Builder<S> resultDetail(final ResultDetail resultDetail) {
            logDetail(ALREADY_FAILED, this.tempState, this.tempRequestResultDetail, log);
            this.tempRequestResultDetail = resultDetail;
            return this;
        }

        /**
         * @param errorCode is optional if state is {@linkplain ResultState#VALID}
         *
         * @return {@linkplain ResultObject.Builder} in fluent api
         *         style
         */
        public ResultObject.Builder<S> errorCode(final Enum<?> errorCode) {
            this.tempErrorCode = errorCode;
            return this;
        }

        /**
         * Reuse {@linkplain ResultState} and {@linkplain ResultObject} from previous
         * {@linkplain ResultObject}
         *
         * @param previousResult {@linkplain ResultObject} must not be {@code null}
         *
         * @return {@linkplain ResultObject.Builder} in fluent api
         *         style
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
         * <li>if state is not {@linkplain ResultState#VALID} requestResultDetail must be
         * available</li>
         * </ul>
         *
         * @return {@linkplain ResultObject} if possible
         * @throws UnsupportedOperationException if you try to ignore conditions
         */
        public ResultObject<S> build() {

            if (null == this.tempResult && null == this.tempValidDefaultResult) {
                throwUnsupportedOperationExceptionAndSaveThePreviousError(NO_RESULTS_AVAILABLE);
            }

            if (null == this.tempState) {
                throwUnsupportedOperationExceptionAndSaveThePreviousError(STATE_IS_NOT_AVAILABLE);
            }

            if (!ResultState.VALID.equals(this.tempState)
                    && null == this.tempRequestResultDetail) {
                throwUnsupportedOperationExceptionAndSaveThePreviousError(THE_RESULT_DETAIL_IS_MANDATORY);
            }

            // only fallback available
            if (null == this.tempResult) {

                if (ResultState.VALID.equals(this.tempState)) {
                    log.debug("Are you really sure you want to use fallback value as result for valid response?");
                }

                return new ResultObject<>(this.tempValidDefaultResult, this.tempState,
                        this.tempRequestResultDetail, this.tempErrorCode);
            }

            return new ResultObject<>(this.tempResult, this.tempState, this.tempRequestResultDetail,
                    this.tempErrorCode);

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
