/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.uimodel.field.impl;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.uimodel.field.DynamicField;
import de.cuioss.uimodel.field.DynamicFieldType;

@SuppressWarnings({"rawtypes"})
public class BaseDynamicFieldGenerator implements TypedGenerator<DynamicField> {

    private final TypedGenerator<String> generator = Generators.letterStrings();

    @Override
    public DynamicField next() {
        return new BaseLabeledDynamicField<>(DynamicFieldType.STRING.createDynamicField(generator.next(), true),
                generator.next(), generator.next(), generator.next());
    }

    @Override
    public Class<DynamicField> getType() {
        return DynamicField.class;
    }

}
