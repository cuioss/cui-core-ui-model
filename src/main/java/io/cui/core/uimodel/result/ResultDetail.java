package io.cui.core.uimodel.result;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.Optional;

import io.cui.core.uimodel.nameprovider.IDisplayNameProvider;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Request Result Detail
 *
 * @author Eugen Fischer
 */
@EqualsAndHashCode(exclude = "cause")
@ToString
@Builder
public class ResultDetail implements Serializable {

    private static final long serialVersionUID = -6313940088570202322L;

    @Getter
    private final IDisplayNameProvider<?> detail;

    private final Throwable cause;

    private final Enum<?> handlingStrategy;

    /**
     * @param detail any {@linkplain IDisplayNameProvider} must not be {@code null}
     */
    public ResultDetail(final IDisplayNameProvider<?> detail) {
        this(detail, null);
    }

    /**
     * @param detail any {@linkplain IDisplayNameProvider} must not be {@code null}
     * @param cause any Throwable
     */
    public ResultDetail(final IDisplayNameProvider<?> detail, final Throwable cause) {
        this(detail, cause, null);
    }

    /**
     * @param detail any {@linkplain IDisplayNameProvider} must not be {@code null}
     * @param cause any Throwable
     * @param handlingStrategy any enumeration which could provide additional info how to handle the
     *            corresponding result
     *
     * @deprecated use {@link ResultObject#containsErrorCode(Enum[])}
     */
    @Deprecated
    public ResultDetail(final IDisplayNameProvider<?> detail, final Throwable cause,
            final Enum<?> handlingStrategy) {
        this.cause = cause;
        this.handlingStrategy = handlingStrategy;
        this.detail = requireNonNull(detail, "detail");
    }

    /**
     * Non mandatory
     *
     * @return the optional cause
     */
    public Optional<Throwable> getCause() {
        return Optional.ofNullable(cause);
    }

    /**
     * @return
     * @deprecated use {@link ResultObject#containsErrorCode(Enum[])}
     */
    @Deprecated
    public Optional<Enum<?>> getHandlingStrategy() {
        return Optional.ofNullable(handlingStrategy);
    }

}
