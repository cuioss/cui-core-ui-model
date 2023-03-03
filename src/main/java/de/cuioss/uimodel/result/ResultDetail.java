package de.cuioss.uimodel.result;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.Optional;

import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
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
        this.cause = cause;
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

}
