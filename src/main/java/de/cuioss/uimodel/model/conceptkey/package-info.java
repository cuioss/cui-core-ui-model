/**
 * Provides a framework for handling concept keys and their associated metadata
 * in the UI model. This package defines the core abstractions and implementations
 * for managing concept-based data structures, particularly focusing on medical
 * and healthcare-related concepts.
 *
 * <p>Key Components:
 * <ul>
 *   <li>{@link de.cuioss.uimodel.model.conceptkey.ConceptKeyType} - Core interface
 *       defining the contract for concept key types</li>
 *   <li>{@link de.cuioss.uimodel.model.conceptkey.ConceptCategory} - Enumeration
 *       of available concept categories</li>
 *   <li>{@link de.cuioss.uimodel.model.conceptkey.ConceptKeyTypeSelection} - Selection
 *       model for concept keys</li>
 *   <li>{@link de.cuioss.uimodel.model.conceptkey.AugmentationKeyConstants} - Constants
 *       for concept key augmentation</li>
 * </ul>
 *
 * <p>Features:
 * <ul>
 *   <li>Type-safe concept key handling</li>
 *   <li>Category-based organization</li>
 *   <li>Selection model support</li>
 *   <li>Augmentation capabilities</li>
 *   <li>Internationalization support</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * // Create a concept key type selection
 * ConceptKeyTypeSelection selection = new ConceptKeyTypeSelection();
 * selection.setCategory(ConceptCategory.DIAGNOSIS);
 *
 * // Work with concept keys
 * if (selection.getCategory().isAugmentable()) {
 *     // Handle augmentable concepts
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.uimodel.model.conceptkey;
