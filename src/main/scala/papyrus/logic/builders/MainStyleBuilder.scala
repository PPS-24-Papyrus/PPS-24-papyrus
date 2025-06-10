package papyrus.logic.builders

import org.xhtmlrenderer.css.parser.property.PrimitivePropertyBuilders.{BackgroundColor, TextAlign}
import papyrus.logic.utility.TypesInline.*
import papyrus.DSL.DefaultValues
import papyrus.logic.styleObjects.MainStyle

case class MainStyleBuilder(
                              private val font: FontFamily = DefaultValues.font,
                              private val fontSize: FontSize = DefaultValues.fontSize,
                              private val lineHeight: LineHeight = DefaultValues.lineHeight,
                              private val textColor: ColorString = DefaultValues.colorText,
                              private val backgroundColor: ColorString = DefaultValues.backgroundColor,
                              private val textAlign: Alignment = DefaultValues.bodyAlign,
                              private val margin: Margin = DefaultValues.margin
                              ):
  def withFont(font: FontFamily): MainStyleBuilder = this.copy(font = font)
  def withFontSize(fontSize: FontSize): MainStyleBuilder = this.copy(fontSize = fontSize)
  def withLineHeight(lineHeight: LineHeight): MainStyleBuilder = this.copy(lineHeight = lineHeight)
  def withTextColor(textColor: ColorString): MainStyleBuilder = this.copy(textColor = textColor)
  def withBackgroundColor(backgroundColor: ColorString): MainStyleBuilder = this.copy(backgroundColor = backgroundColor)
  def withTextAlign(textAlign: Alignment): MainStyleBuilder = this.copy(textAlign = textAlign)
  def withMargin(margin: Margin): MainStyleBuilder = this.copy(margin = margin)
  def build(): MainStyle = MainStyle(
    font = font,
    fontSize = fontSize,
    lineHeight = lineHeight,
    textColor = textColor,
    backgroundColor = backgroundColor,
    textAlign = textAlign,
    margin = margin
  )
