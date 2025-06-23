package papyrus.logic.layerElement

import papyrus.logic.Renderer.Renderer
import papyrus.logic.Style
import papyrus.logic.layerElement.text.Text

/** Represents any renderable element in the document body (text, title, image, table, section, etc.) */
trait LayerElement extends Renderer

/** Represents an element that can be part of a list (e.g., Item or Listing) */
trait ListElement extends LayerElement
