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
package de.cuioss.uimodel.field.impl;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.generator.PropertyGenerator;
import de.cuioss.test.valueobjects.api.property.PropertyConfig;
import de.cuioss.uimodel.field.DynamicField;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Base Labeled Dynamic Field Tests")
@PropertyGenerator(BaseDynamicFieldGenerator.class)
@PropertyConfig(name = "delegate", propertyClass = DynamicField.class)
@VerifyConstructor(of = {"delegate", "identifier", "labelKey", "advisoryKey"}, writeOnly = "delegate", required = {
        "delegate", "identifier"})
class BaseLabeledDynamicFieldTest extends ValueObjectTest<BaseLabeledDynamicField<String>> {

}
