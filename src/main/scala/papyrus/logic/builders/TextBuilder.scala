package papyrus.logic.builders

import papyrus.logic.layerElement.text.Text
import papyrus.logic.styleObjects.TextStyle
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.DefaultValues

case class TextBuilder(
                        private val value: String = DefaultValues.defaultText,
                        private val color: ColorString = DefaultValues.colorText,
                        private val fontWeight: FontWeight = DefaultValues.fontWeightText,
                        private val fontStyle: FontStyle = DefaultValues.fontStyleText,
                        private val textDecoration: TextDecoration = DefaultValues.textDecorationText
                      ):

  private def withValue(v: String): TextBuilder = this.copy(value = v)
  private def withColor(c: ColorString): TextBuilder = this.copy(color = c)
  private def withFontWeight(fw: FontWeight): TextBuilder = this.copy(fontWeight = fw)
  private def withFontStyle(fs: FontStyle): TextBuilder = this.copy(fontStyle = fs)
  private def withTextDecoration(td: TextDecoration): TextBuilder = this.copy(textDecoration = td)

  def build: Text = Text(value)(TextStyle(
    color = color,
    fontWeight = fontWeight,
    fontStyle = fontStyle,
    textDecoration = textDecoration
  ))

object TextBuilder:
  def apply(
             value: String = DefaultValues.defaultText,
             color: ColorString = DefaultValues.colorText,
             fontWeight: FontWeight = DefaultValues.fontWeightText,
             fontStyle: FontStyle = DefaultValues.fontStyleText,
             textDecoration: TextDecoration = DefaultValues.textDecorationText
           ): TextBuilder =
    new TextBuilder(value, color, fontWeight, fontStyle, textDecoration)

  extension (tb: TextBuilder)
    def value(v: String): TextBuilder = tb.withValue(v)
    def color(c: ColorString): TextBuilder = tb.withColor(c)
    def fontWeight(fw: FontWeight): TextBuilder = tb.withFontWeight(fw)
    def fontStyle(fs: FontStyle): TextBuilder = tb.withFontStyle(fs)
    def textDecoration(td: TextDecoration): TextBuilder = tb.withTextDecoration(td)
