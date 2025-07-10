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
package de.cuioss.uimodel.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("CuiProjectStage Tests")
class CuiProjectStageTest {

    // Test implementation of CuiProjectStage
    private static class TestProjectStage implements CuiProjectStage {
        private final String stage;

        TestProjectStage(String stage) {
            this.stage = stage;
        }

        @Override
        public boolean isDevelopment() {
            return "development".equals(stage);
        }

        @Override
        public boolean isTest() {
            return "test".equals(stage);
        }

        @Override
        public boolean isProduction() {
            return "production".equals(stage) || stage == null;
        }
    }

    @Nested
    @DisplayName("Development stage tests")
    class DevelopmentStageTests {
        private final CuiProjectStage stage = new TestProjectStage("development");

        @Test
        @DisplayName("should identify development stage correctly")
        void shouldIdentifyDevelopmentStage() {
            assertTrue(stage.isDevelopment());
            assertFalse(stage.isTest());
            assertFalse(stage.isProduction());
        }
    }

    @Nested
    @DisplayName("Test stage tests")
    class TestStageTests {
        private final CuiProjectStage stage = new TestProjectStage("test");

        @Test
        @DisplayName("should identify test stage correctly")
        void shouldIdentifyTestStage() {
            assertFalse(stage.isDevelopment());
            assertTrue(stage.isTest());
            assertFalse(stage.isProduction());
        }
    }

    @Nested
    @DisplayName("Production stage tests")
    class ProductionStageTests {
        private final CuiProjectStage stage = new TestProjectStage("production");

        @Test
        @DisplayName("should identify production stage correctly")
        void shouldIdentifyProductionStage() {
            assertFalse(stage.isDevelopment());
            assertFalse(stage.isTest());
            assertTrue(stage.isProduction());
        }
    }

    @Nested
    @DisplayName("Unknown stage tests")
    class UnknownStageTests {
        private final CuiProjectStage stage = new TestProjectStage(null);

        @Test
        @DisplayName("should treat unknown stage as production")
        void shouldTreatUnknownAsProduction() {
            assertFalse(stage.isDevelopment());
            assertFalse(stage.isTest());
            assertTrue(stage.isProduction());
        }
    }
}
