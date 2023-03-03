package de.cuioss.uimodel.nameprovider;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;

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
