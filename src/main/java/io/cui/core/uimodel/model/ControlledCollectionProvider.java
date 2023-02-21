package io.cui.core.uimodel.model;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

/**
 * Provider does manage big data collections.
 * <p>
 * On initialization only subset of available data get retrieved.
 * Provider support action "show all" to remove restriction afterwards.
 * <b>Attention</b> Expectation is that on initialization all available data is be available. There
 * is no lazy loading mechanism prepared!
 * </p>
 *
 *
 * @author Eugen Fischer
 * @param <T> bounded content type
 */
@Data
public class ControlledCollectionProvider<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 915820500771225796L;

    @Getter(value = AccessLevel.PRIVATE)
    private final List<T> fullContent;

    @Getter(value = AccessLevel.PRIVATE)
    private final List<T> subContent;

    private boolean fullContentDisplayed;

    /**
     * Initialization of ControlledCollectionProvider
     *
     * @param source must not be {@code null}
     * @param initialSizeLimitation count of sub list
     */
    public ControlledCollectionProvider(final List<T> source, final int initialSizeLimitation) {
        this.fullContent = requireNonNull(source, "Source should not be null.");
        if (this.fullContent.size() > initialSizeLimitation) {
            this.subContent = fullContent.stream().limit(initialSizeLimitation).collect(toList());
            this.fullContentDisplayed = false;
        } else {
            this.fullContentDisplayed = true;
            this.subContent = emptyList();
        }
    }

    /**
     * @return content depend on flag if full content or just sub list should be displayed
     */
    public List<T> getContent() {
        if (fullContentDisplayed) {
            return fullContent;
        }
        return subContent;
    }

    /**
     * On execute action restriction will get removed and full content will be returned on
     * {@linkplain #getContent()}
     */
    public void removeRestriction() {
        fullContentDisplayed = true;
    }

    /**
     * @return empty instance for {@linkplain ControlledCollectionProvider}
     */
    public static final <V extends Serializable> ControlledCollectionProvider<V> emptyInstance() {
        return new ControlledCollectionProvider<>(emptyList(), 0);
    }

}
