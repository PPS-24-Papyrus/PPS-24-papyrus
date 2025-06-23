package papyrus.logic.layerElement.text

import papyrus.logic.Renderer.Text.*
import papyrus.logic.utility.TypesInline.*
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.styleObjects.TitleStyle

/** Represents a section or document title with a heading level and style */
trait Title extends LayerElement:

  /** The title text */
  def title: String

  /** Heading level (e.g. 1 for <h1>, 2 for <h2>, ...) */
  def level: Level


object Title:

  /** Creates a styled title with a specific heading level */
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
