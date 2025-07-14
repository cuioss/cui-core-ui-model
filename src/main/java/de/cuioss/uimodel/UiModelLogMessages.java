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
package de.cuioss.uimodel;

import de.cuioss.tools.logging.LogRecord;
import de.cuioss.tools.logging.LogRecordModel;
import lombok.experimental.UtilityClass;

/**
 * Defines the log messages for the cui-core-ui-model module.
 * All messages follow the format: UI_MODEL-[identifier]: [message]
 */
@UtilityClass
public final class UiModelLogMessages {

    private static final String PREFIX = "UI_MODEL";

    @UtilityClass
    public static final class INFO {

        public static final LogRecord RESULT_CREATED = LogRecordModel.builder()
                .prefix(PREFIX)
                .identifier(1)
                .template("Result object created with state '%s'")
                .build();

        public static final LogRecord RESULT_MAPPED = LogRecordModel.builder()
                .prefix(PREFIX)
                .identifier(2)
                .template("Result mapped from '%s' to '%s'")
                .build();
    }

    @UtilityClass
    public static final class WARN {

        public static final LogRecord INVALID_RESULT_ACCESS = LogRecordModel.builder()
                .prefix(PREFIX)
                .identifier(100)
                .template("Invalid attempt to access result in state '%s'")
                .build();

        public static final LogRecord EMPTY_MESSAGE_ARGS = LogRecordModel.builder()
                .prefix(PREFIX)
                .identifier(101)
                .template("No message format arguments provided. Consider using LabeledKey instead.")
                .build();

        public static final LogRecord MISSING_RESULT_DETAIL = LogRecordModel.builder()
                .prefix(PREFIX)
                .identifier(102)
                .template("Result detail is missing for non-valid state '%s'")
                .build();

        public static final LogRecord RESULT_CREATION_FAILED = LogRecordModel.builder()
                .prefix(PREFIX)
                .identifier(104)
                .template("Failed to create result object: %s")
                .build();
    }

}
