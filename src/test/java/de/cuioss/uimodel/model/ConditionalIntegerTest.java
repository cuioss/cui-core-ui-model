/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.uimodel.model;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.contracts.VerifyFactoryMethod;
import de.cuioss.test.valueobjects.api.object.ObjectTestConfig;
import de.cuioss.test.valueobjects.api.property.PropertyConfig;
import org.junit.jupiter.api.DisplayName;

import static de.cuioss.tools.property.PropertyReadWrite.WRITE_ONLY;

@DisplayName("Tests ConditionalInteger Value Object")
@PropertyConfig(name = "minBound", propertyClass = int.class, propertyReadWrite = WRITE_ONLY)
@PropertyConfig(name = "maxBound", propertyClass = int.class, propertyReadWrite = WRITE_ONLY)
@VerifyConstructor(of = {"content", "minBound", "maxBound"})
@VerifyFactoryMethod(of = "content", factoryMethodName = "createYearInstance")
@VerifyFactoryMethod(of = "content", factoryMethodName = "createMonthInstance")
@VerifyFactoryMethod(of = "content", factoryMethodName = "createDayInstance")
@ObjectTestConfig(equalsAndHashCodeOf = "content")
class ConditionalIntegerTest extends ValueObjectTest<ConditionalInteger> {

}
