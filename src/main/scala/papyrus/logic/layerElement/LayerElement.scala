package papyrus.logic.layerElement

import papyrus.logic.Renderer.Renderer
import papyrus.logic.Style.Style

trait LayerElement extends Renderer:
  def addStyle(style: Style): Unit
  
object LayerElement:
  