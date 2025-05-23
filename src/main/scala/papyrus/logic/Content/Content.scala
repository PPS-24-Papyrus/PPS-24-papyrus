package papyrus.logic.Content

import papyrus.logic.Renderer.Renderer
import papyrus.logic.layerElement.LayerElement.LayerElement

object Content:

  trait Content extends Renderer:
    def title(title: String): Unit
    def layerElement(layerElements: LayerElement*): Unit

  object Content:
    def apply() = ???
