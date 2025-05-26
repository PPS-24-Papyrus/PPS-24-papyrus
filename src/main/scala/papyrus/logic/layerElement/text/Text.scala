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
    font: FontFamily = "serif",
    fontSize: FontSize = 16,
    margin: Margin = 0,
    fontWeight: FontWeight = "none",
    fontStyle: FontStyle = "none",
    textDecoration: TextDecoration = "none",
    textTransform: TextTransform = "none",
    letterSpacing: LetterSpacing = 0.0,
    wordSpacing: WordSpacing = 0.0,
    lineHeight: LineHeight = 1.0,
    textAlign: Alignment = "left"
  ): Text =
    new TextImpl(
      text,
      color,
      font,
      fontSize,
      margin,
      fontWeight,
      fontStyle,
      textDecoration,
      textTransform,
      letterSpacing,
      wordSpacing,
      lineHeight,
      textAlign
    )

  private class TextImpl(
                          override val text: String,
                          color: ColorString,
                          font: FontFamily,
                          fontSize: FontSize,
                          margin: Margin,
                          fontWeight: FontWeight,
                          fontStyle: FontStyle,
                          textDecoration: TextDecoration,
                          textTransform: TextTransform,
                          letterSpacing: LetterSpacing,
                          wordSpacing: WordSpacing,
                          lineHeight: LineHeight,
                          textAlign: Alignment
                        ) extends Text:

    override def render: String =
      s"""<p class="text">$text</p>"""

    override def renderStyle: String =
      val rules = Seq(
        Style.textColor(color),
        Style.font(font),
        Style.fontSize(fontSize),
        Style.margin(margin),
        Style.fontWeight(fontWeight),
        Style.fontStyle(fontStyle),
        Style.textDecoration(textDecoration),
        Style.textTransform(textTransform),
        Style.letterSpacing(letterSpacing),
        Style.wordSpacing(wordSpacing),
        Style.lineHeight(lineHeight),
        Style.textAlign(textAlign)
      ).mkString("\n  ")

      s""".text {\n  $rules\n}"""

    given Conversion[String, Text] with
      def apply(str: String): Text = Text(str)()
