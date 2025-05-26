package papyrus.logic.layerElement

import papyrus.logic.Renderer.Renderer
import papyrus.logic.Style

trait LayerElement extends Renderer:
  def style: Style
