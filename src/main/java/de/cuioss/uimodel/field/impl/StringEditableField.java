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

import java.io.Serial;

import de.cuioss.uimodel.field.DynamicFieldType;

/**
 * An input field where the user can type in a String.
 *
 * @author Oliver Wolff
 */
public class StringEditableField extends BaseDynamicField<String> {

    @Serial
    private static final long serialVersionUID = 1576605569211546203L;

    /**
     * C'tor.
     *
     * @param editable If the content of the field is editable.
     */
    public StringEditableField(final boolean editable) {
        super(editable, DynamicFieldType.STRING);
    }

    /**
     * C'tor.
     *
     * @param value    The value of the input field
     * @param editable If the input field is editable.
     */
    public StringEditableField(final String value, final boolean editable) {
        super(value, editable, DynamicFieldType.STRING);
    }
}
