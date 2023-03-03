package de.cuioss.uimodel.model;

import static de.cuioss.tools.property.PropertyReadWrite.WRITE_ONLY;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.contracts.VerifyFactoryMethod;
import de.cuioss.test.valueobjects.api.object.ObjectTestConfig;
import de.cuioss.test.valueobjects.api.property.PropertyConfig;

@PropertyConfig(name = "minBound",
        propertyClass = int.class,
        propertyReadWrite = WRITE_ONLY)
@PropertyConfig(name = "maxBound",
        propertyClass = int.class,
        propertyReadWrite = WRITE_ONLY)
@VerifyConstructor(of = { "content", "minBound", "maxBound" })
@VerifyFactoryMethod(of = "content",
        factoryMethodName = "createYearInstance")
@VerifyFactoryMethod(of = "content",
        factoryMethodName = "createMonthInstance")
@VerifyFactoryMethod(of = "content",
        factoryMethodName = "createDayInstance")
@ObjectTestConfig(equalsAndHashCodeOf = "content")
class ConditionalIntegerTest extends ValueObjectTest<ConditionalInteger> {

}
