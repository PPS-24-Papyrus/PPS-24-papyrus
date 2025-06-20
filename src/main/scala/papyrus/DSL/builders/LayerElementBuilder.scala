package papyrus.DSL.builders

import papyrus.logic.layerElement.{LayerElement, ListElement}

/** Base builder for any LayerElement (e.g., text, image, title, section, etc.) */
trait LayerElementBuilder extends Builder[LayerElement]

/** Base builder for list elements (either Listing or Item) */
trait ListElementBuilder extends Builder[ListElement]
