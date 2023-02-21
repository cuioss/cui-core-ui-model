package io.cui.core.uimodel.nameprovider;

import java.time.temporal.TemporalAccessor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Displays instances of {@link TemporalAccessor}
 *
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@SuppressWarnings("squid:S1948") // owolff: all known implementation are Serializable
public class TemporalAccessorDisplayNameProvider implements IDisplayNameProvider<TemporalAccessor> {

    private static final long serialVersionUID = -1148355576864804121L;

    @Getter
    @NonNull
    private final TemporalAccessor content;
}
