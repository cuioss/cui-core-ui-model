package io.cui.core.uimodel.nameprovider;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Instance of IDisplayNameProvider which indicates to provide display name without need of
 * resolving.
 *
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class DisplayName implements IDisplayNameProvider<String> {

    private static final long serialVersionUID = -5827322353134669923L;

    @Getter
    @NonNull
    private final String content;

}
