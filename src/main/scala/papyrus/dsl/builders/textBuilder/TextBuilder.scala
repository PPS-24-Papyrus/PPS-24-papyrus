package papyrus.dsl.builders.textBuilder

import papyrus.logic.layerElement.text.Text
import papyrus.logic.styleObjects.TextStyle
import io.github.iltotore.iron.autoRefine
import papyrus.utility.DefaultValues
import papyrus.dsl.builders.{Builder, LayerElementBuilder}
import papyrus.utility.TypesInline.*

/**Builds styled text elements for Papyrus document*/
case class TextBuilder(
                        private val value: String = DefaultValues.defaultText,
                        private val color: ColorString = DefaultValues.colorText,
                        private val fontWeight: FontWeight = DefaultValues.fontWeightText,
                        private val fontStyle: FontStyle = DefaultValues.fontStyleText,
                        private val textDecoration: TextDecoration = DefaultValues.textDecorationText
                      ) extends LayerElementBuilder:

  private def withValue(v: String): TextBuilder = this.copy(value = v)
  private def withColor(c: ColorString): TextBuilder = this.copy(color = c)
  private def withFontWeight(fw: FontWeight): TextBuilder = this.copy(fontWeight = fw)
  private def withFontStyle(fs: FontStyle): TextBuilder = this.copy(fontStyle = fs)
  private def withTextDecoration(td: TextDecoration): TextBuilder = this.copy(textDecoration = td)
  private def withNewLine(v: String = ""): TextBuilder = this.copy(value=value+"\n"+v)

  /** Builds a styled Text element */
  override def build: Text = Text(value)(TextStyle(
    color = color,
    fontWeight = fontWeight,
    fontStyle = fontStyle,
    textDecoration = textDecoration
  ))

object TextBuilder:
  /** Creates a TextBuilder */
  def apply(
             value: String = DefaultValues.defaultText,
             color: ColorString = DefaultValues.colorText,
             fontWeight: FontWeight = DefaultValues.fontWeightText,
             fontStyle: FontStyle = DefaultValues.fontStyleText,
             textDecoration: TextDecoration = DefaultValues.textDecorationText
           ): TextBuilder =
    new TextBuilder(value, color, fontWeight, fontStyle, textDecoration)

  extension (tb: TextBuilder)
    /** Sets the text content */
    def value(v: String): TextBuilder = tb.withValue(v)

    /** Sets the text color (e.g. "#fff", "rgb(...)", or "red", "blue", ...) */
    def color(c: ColorString): TextBuilder = tb.withColor(c)

    /** Sets the font weight ("normal" or "bold") */
    def fontWeight(fw: FontWeight): TextBuilder = tb.withFontWeight(fw)

    /** Sets the font style ("normal" or "italic") */
    def fontStyle(fs: FontStyle): TextBuilder = tb.withFontStyle(fs)

    /** Sets the text decoration ("none", "underline" or "overline") */
    def textDecoration(td: TextDecoration): TextBuilder = tb.withTextDecoration(td)

    /** Appends a newline to another text */
    def newLine(v: String): TextBuilder = tb.withNewLine(v)
