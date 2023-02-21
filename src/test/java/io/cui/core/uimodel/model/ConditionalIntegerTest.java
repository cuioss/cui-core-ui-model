package io.cui.core.uimodel.model;

import static io.cui.tools.property.PropertyReadWrite.WRITE_ONLY;

import io.cui.test.valueobjects.ValueObjectTest;
import io.cui.test.valueobjects.api.contracts.VerifyConstructor;
import io.cui.test.valueobjects.api.contracts.VerifyFactoryMethod;
import io.cui.test.valueobjects.api.object.ObjectTestConfig;
import io.cui.test.valueobjects.api.property.PropertyConfig;

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
