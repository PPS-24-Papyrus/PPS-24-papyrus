package papyrus.logic.builders

import papyrus.logic.utility.TypesInline.*
import papyrus.DSL.DefaultValues
import papyrus.logic.styleObjects.MainStyle

class MainStyleBuilder:
  var font: FontFamily = DefaultValues.font
  var fontSize: FontSize = DefaultValues.fontSize
  var lineHeight: LineHeight = DefaultValues.lineHeight
  var textColor: ColorString = DefaultValues.colorText
  var backgroundColor: ColorString = DefaultValues.backgroundColor
  var textAlign: Alignment = DefaultValues.bodyAlign
  var margin: Margin = DefaultValues.margin
  
  def build(): MainStyle = MainStyle(
    font = font,
    fontSize = fontSize,
    lineHeight = lineHeight,
    textColor = textColor,
    backgroundColor = backgroundColor,
    textAlign = textAlign,
    margin = margin
  )
