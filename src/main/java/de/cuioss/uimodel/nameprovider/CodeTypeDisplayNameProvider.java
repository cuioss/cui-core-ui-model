package de.cuioss.uimodel.nameprovider;

import de.cuioss.uimodel.model.code.CodeType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Instance of IDisplayNameProvider which indicates to provide display key that implements
 * {@link CodeType}.
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class CodeTypeDisplayNameProvider implements IDisplayNameProvider<CodeType> {

    private static final long serialVersionUID = -4376110565562089715L;

    @Getter
    @NonNull
    private final CodeType content;

}
