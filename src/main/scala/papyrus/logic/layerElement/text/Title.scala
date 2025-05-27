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
             textColor: ColorString = "#000000",
             textAlign: Alignment = "left"
           ): Title = new TitleImpl(title, level)(font, fontSize, textColor, textAlign)

  private class TitleImpl(
                           override val title: String,
                           override val level: Level
                         )(
                           font: FontFamily,
                           fontSize: FontSize,
                           textColor: ColorString,
                           textAlign: Alignment
                         ) extends Title:

    override def render: String = s"<h$level>$title</h$level>"

    override def renderStyle: String =
      val css = Seq(
        Style.font(font),
        Style.fontSize(fontSize),
        Style.textColor(textColor),
        Style.textAlign(textAlign)
      ).mkString(" ")

      s"h$level {\n  $css\n}"
