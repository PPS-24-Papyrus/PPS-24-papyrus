package papyrus.logic.layerElement

import papyrus.logic.Renderer.Renderer
import papyrus.logic.Style
import papyrus.logic.layerElement.text.Text

trait LayerElement extends Renderer

object LayerElement:
  // Conversione automatica da String a Text con stili di default
  given Conversion[String, LayerElement] with
    def apply(str: String): LayerElement = Text(str)()


