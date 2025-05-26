package papyrus.logic.layerElement.text

import papyrus.logic.Style
import papyrus.logic.utility.TypesInline.Level
import papyrus.logic.layerElement.LayerElement

trait Title extends LayerElement:
  def title: String
  def level: Level

object Title:
  def apply(title: String, level: Level, style: Style = null): Title = new TitleImpl(title, level, style)

  private class TitleImpl(override val title: String, override val level: Level, override val style: Style) extends Title:
    override def render: String = s"<h$level>$title</h$level>"
    override def renderStyle: String = if (style != null) s".h$level{${style.renderStyle}}" else ""