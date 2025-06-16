package papyrus.logic.layerElement.text

import papyrus.logic.Renderer.Text.*
import papyrus.logic.utility.TypesInline.*
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.styleObjects.TitleStyle

trait Title extends LayerElement:
  def title: String
  def level: Level

object Title:
  def apply(
             title: String,
             level: Level
           )(
             titleStyle: TitleStyle
           ): Title = TitleImpl(title, level)(titleStyle)

  private class TitleImpl(
                           override val title: String,
                           override val level: Level
                         )(
                           titleStyle: TitleStyle
                         ) extends Title:

    override def render: MainText = 
      s"""<h$level>$title</h$level>\n""".toMainText

    override def renderStyle: StyleText =
      s"""h$level {\n  ${titleStyle.renderStyle}\n}""".toStyleText
