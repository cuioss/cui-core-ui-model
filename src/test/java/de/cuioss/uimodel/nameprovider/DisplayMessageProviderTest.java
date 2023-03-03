package de.cuioss.uimodel.nameprovider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.Generators;

class DisplayMessageProviderTest {

    private DisplayMessageProvider target;

    @Test
    void shouldVerifyMandatoryParameter() {
        assertThrows(NullPointerException.class, () -> {
            new DisplayMessageProvider(null);
        });
    }

    @Test
    void shouldProvideContent() {
        final var displayMessageFormat = anyDisplayMessageFormat();
        target = new DisplayMessageProvider(displayMessageFormat);

        assertThat(target.getContent(), is(equalTo(displayMessageFormat)));
    }

    @Test
    void shouldProvideResolving() {

        final var bundle = new MockResourceBundle();
        final var key = "some.error.key";
        final var value = "Error occurs on {0}";
        bundle.add(key, value);

        final var displayMessageFormat =
            new DisplayMessageFormat(key, "optional service");
        target = new DisplayMessageProvider(displayMessageFormat);

        assertThat(target.getMessageFormated(bundle),
                is(equalTo("Error occurs on optional service")));
    }

    private static DisplayMessageFormat anyDisplayMessageFormat() {
        return new DisplayMessageFormat(Generators.letterStrings(1, 10).next(), Generators.letterStrings(1, 10).next());
    }

    private static class MockResourceBundle extends ResourceBundle {

        private final Map<String, Object> storage;

        public MockResourceBundle() {
            storage = new HashMap<>();
        }

        public MockResourceBundle add(final String key, final Object value) {
            storage.put(key, value);
            return this;
        }

        @Override
        protected Object handleGetObject(final String key) {
            if (storage.containsKey(key)) {
                return storage.get(key);
            }
            throw new MissingResourceException("Unknown key", "MockResourceBundle", key);
        }

        @Override
        public Enumeration<String> getKeys() {
            return null;
        }
    }

}
