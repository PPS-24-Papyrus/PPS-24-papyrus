package papyrus.logic.layerElement.text

import papyrus.logic.Style
import papyrus.logic.layerElement.LayerElement

trait Text extends LayerElement:
  def text: String

object Text:
  def apply(text: String, style: Style = null): Text = new TextImpl(text, style)

  private class TextImpl(override val text: String, override val style: Style) extends Text:
    override def render: String = s"<p>${text}</p>"
    override def renderStyle: String = if (style != null) s".p{${style.renderStyle}}" else ""