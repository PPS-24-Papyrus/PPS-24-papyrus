package papyrus.logic.layerElement.text

import papyrus.logic.Style
import papyrus.logic.utility.TypesInline.*
import papyrus.logic.layerElement.LayerElement
import io.github.iltotore.iron.autoRefine

trait Text extends LayerElement:
  def text: String

object Text:

  def apply(text: String)(
    color: ColorString = "black",
    fontWeight: FontWeight = "none",
    fontStyle: FontStyle = "none",
    textDecoration: TextDecoration = "none",
  ): Text =
    new TextImpl(
      text,
      color,
      fontWeight,
      fontStyle,
      textDecoration
    )

  private class TextImpl(
                          override val text: String,
                          color: ColorString,
                          fontWeight: FontWeight,
                          fontStyle: FontStyle,
                          textDecoration: TextDecoration
                        ) extends Text:

    override def render: String =
      s"""<p class="text">$text</p>"""

    override def renderStyle: String =
      val rules = Seq(
        Style.textColor(color),
        Style.fontWeight(fontWeight),
        Style.fontStyle(fontStyle),
        Style.textDecoration(textDecoration)
      ).mkString("\n  ")

      s""".text {\n  $rules\n}"""

    given Conversion[String, Text] with
      def apply(str: String): Text = Text(str)()
