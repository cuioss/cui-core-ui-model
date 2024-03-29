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
package de.cuioss.uimodel.nameprovider;

import de.cuioss.uimodel.model.code.CodeType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Instance of IDisplayNameProvider which indicates to provide display key that
 * implements {@link CodeType}.
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class CodeTypeDisplayNameProvider implements IDisplayNameProvider<CodeType> {

    private static final long serialVersionUID = -4376110565562089715L;

    @Getter
    @NonNull
    private final CodeType content;

}
