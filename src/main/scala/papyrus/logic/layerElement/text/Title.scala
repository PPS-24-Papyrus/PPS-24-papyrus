package papyrus.logic.layerElement.text

import papyrus.logic.Style
import papyrus.logic.utility.TypesInline.*
import papyrus.logic.layerElement.LayerElement
import io.github.iltotore.iron.autoRefine

trait Title extends LayerElement:
  def title: String
  def level: Level

object Title:
  def apply(
             title: String,
             level: Level
           )(
             font: FontFamily = "Georgia",
             fontSize: FontSize = 24,
             lineHeight: LineHeight = 1.4,
             textColor: ColorString = "#000000",
             textAlign: Alignment = "left",
             margin: Margin = 10
           ): Title = new TitleImpl(title, level)(font, fontSize, lineHeight, textColor, textAlign, margin)

  private class TitleImpl(
                           override val title: String,
                           override val level: Level
                         )(
                           font: FontFamily,
                           fontSize: FontSize,
                           lineHeight: LineHeight,
                           textColor: ColorString,
                           textAlign: Alignment,
                           margin: Margin
                         ) extends Title:

    override def render: String = s"<h$level>$title</h$level>"

    override def renderStyle: String =
      val css = Seq(
        Style.font(font),
        Style.fontSize(fontSize),
        Style.lineHeight(lineHeight),
        Style.textColor(textColor),
        Style.textAlign(textAlign),
        Style.margin(margin)
      ).mkString(" ")

      s"h$level {\n  $css\n}"
