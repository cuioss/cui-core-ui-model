/**
 * Provides a flexible framework for converting complex objects into displayable strings
 * in UI components. This package implements the Display Name Provider pattern, which
 * decouples backend objects from their UI representation through typed converters.
 *
 * <p>Key Components:
 * <ul>
 *   <li>{@link de.cuioss.uimodel.nameprovider.IDisplayNameProvider} - Core interface
 *       defining the display name contract</li>
 *   <li>{@link de.cuioss.uimodel.nameprovider.DisplayName} - Basic implementation
 *       for static display names</li>
 *   <li>{@link de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider} - Implementation
 *       for internationalized display names</li>
 *   <li>{@link de.cuioss.uimodel.nameprovider.CodeTypeDisplayNameProvider} - Specialized
 *       provider for code types</li>
 *   <li>{@link de.cuioss.uimodel.nameprovider.TemporalAccessorDisplayNameProvider} - Provider
 *       for temporal objects</li>
 *   <li>{@link de.cuioss.uimodel.nameprovider.DisplayMessageProvider} - Provider for
 *       formatted messages</li>
 * </ul>
 *
 * <p>Design Patterns:
 * <ul>
 *   <li>Provider Pattern - Abstracts the creation of display names</li>
 *   <li>Strategy Pattern - Allows different display strategies</li>
 *   <li>Adapter Pattern - Converts objects to displayable strings</li>
 *   <li>Factory Pattern - Creates appropriate providers</li>
 * </ul>
 *
 * <p>Usage Examples:
 * <pre>
 * // Static display name
 * IDisplayNameProvider simple = new DisplayName("Hello World");
 *
 * // Internationalized name
 * IDisplayNameProvider i18n = new I18nDisplayNameProvider("message.key");
 *
 * // Code type display
 * CodeType code = ...;
 * IDisplayNameProvider codeDisplay = new CodeTypeDisplayNameProvider(code);
 *
 * // Temporal display
 * LocalDate date = LocalDate.now();
 * IDisplayNameProvider dateDisplay = new TemporalAccessorDisplayNameProvider(date);
 * </pre>
 *
 * <p>Best Practices:
 * <ul>
 *   <li>Choose the most specific provider for your use case</li>
 *   <li>Consider internationalization requirements early</li>
 *   <li>Use composition to combine multiple providers</li>
 *   <li>Handle null values appropriately</li>
 *   <li>Cache providers when possible for better performance</li>
 * </ul>
 *
 * @author Eugen Fischer
 * @since 1.0
 */
package de.cuioss.uimodel.nameprovider;
