package papyrus.logic.layerElement

import papyrus.logic.Renderer.Renderer
import papyrus.logic.Style.Style

object LayerElement:
  
  trait LayerElement extends Renderer:
    def addStyle(style: Style): Unit