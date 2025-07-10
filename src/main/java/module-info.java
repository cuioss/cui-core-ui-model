/**
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
module de.cuioss.uimodel {

    requires transitive de.cuioss.java.tools;
    requires static lombok;

    exports de.cuioss.uimodel.application;
    exports de.cuioss.uimodel.field;
    exports de.cuioss.uimodel.field.impl;
    exports de.cuioss.uimodel.model;
    exports de.cuioss.uimodel.model.code;
    exports de.cuioss.uimodel.model.conceptkey;
    exports de.cuioss.uimodel.model.conceptkey.impl;
    exports de.cuioss.uimodel.model.impl;
    exports de.cuioss.uimodel.result;
    exports de.cuioss.uimodel.nameprovider;
    exports de.cuioss.uimodel.nameprovider.data;
    exports de.cuioss.uimodel.service;

}