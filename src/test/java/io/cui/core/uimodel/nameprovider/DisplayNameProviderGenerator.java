package io.cui.core.uimodel.nameprovider;

import io.cui.test.generator.Generators;
import io.cui.test.generator.TypedGenerator;

@SuppressWarnings({ "javadoc", "rawtypes" })
public class DisplayNameProviderGenerator implements TypedGenerator<IDisplayNameProvider> {

    @Override
    public IDisplayNameProvider next() {
        return new DisplayName(Generators.letterStrings(1, 12).next());
    }

    @Override
    public Class<IDisplayNameProvider> getType() {
        return IDisplayNameProvider.class;
    }

}
