package de.cuioss.uimodel.nameprovider;

import java.io.Serializable;

/**
 * <p>
 * Interface IDisplayNameProvider indicates entity which provide content that need to be presented
 * on ui.
 * </p>
 * The resolving of the display name is not part of the entity itself.
 *
 * It's provide just the information that it should be resolved somehow and deliver the content
 * which need to be resolved.
 *
 * If for example the source data is distributed over several attributes but needed as compound
 * object it could be solved by creating a additional compound object which implements the interface
 * IDisplayNameProvider. How this content should be displayed is a decision of converter which is
 * registered for the compound type. In this way a lose coupling is reached by using standard
 * mechanism of JSF.
 *
 * @author Eugen Fischer
 * @param <T> bounded content parameter
 */
@FunctionalInterface
public interface IDisplayNameProvider<T> extends Serializable {

    /**
     * @return actual content
     */
    T getContent();

}
