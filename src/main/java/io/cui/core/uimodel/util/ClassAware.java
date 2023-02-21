package io.cui.core.uimodel.util;

import java.util.Arrays;
import java.util.List;

import io.cui.tools.collect.CollectionBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

/**
 * Provide functionality to verify object by using reflection information
 *
 * @author Eugen Fischer
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClassAware {

    /**
     * @param expected class type of Object instance
     * @param obj Object instance which should be checked
     * @return <code>true</code> if <em>obj</em> is not null and conforms expected class or
     *         implements the needed interface or has conform expected class as ancestors,
     *         <code>false</code> otherwise
     */
    public static boolean isProperType(final Class<?> expected, final Object obj) {
        if (null != obj && (expected.isAssignableFrom(obj.getClass())
                || getClassDerivativeInfo(obj.getClass()).contains(expected))) {
            return true;
        }
        return false;
    }

    /**
     * @param clazz {@linkplain Class} meta data
     * @return List of classes and implemented interfaces include also ancestors when parameter is
     *         not <code>null</code>, empty List otherwise
     */
    private static List<Class<?>> getClassDerivativeInfo(final Class<?> clazz) {
        final var builder = new CollectionBuilder<Class<?>>();
        if (null != clazz) {
            builder.add(clazz);
            if (null != clazz.getSuperclass()) {
                // recursive call for ancestor
                builder.add(getClassDerivativeInfo(clazz.getSuperclass()));
            }
            val interfaces = Arrays.asList(clazz.getInterfaces());
            for (val tempInterface : interfaces) {
                builder.add(collectInterfaceDerivativeInfo(tempInterface));
            }
        }
        return builder.toImmutableList();
    }

    /**
     * @param clazz {@linkplain Class} meta data
     * @return List of all interfaces include also ancestors when parameter is not <code>null</code>
     *         , empty List otherwise
     */
    private static List<Class<?>> collectInterfaceDerivativeInfo(final Class<?> clazz) {
        final var builder = new CollectionBuilder<Class<?>>();
        if (null != clazz && clazz.isInterface()) {
            builder.add(clazz);
            val interfaces = Arrays.asList(clazz.getInterfaces());
            for (val tempInterface : interfaces) {
                // recursive call
                builder.add(collectInterfaceDerivativeInfo(tempInterface));
            }
        }
        return builder.toImmutableList();
    }
}
