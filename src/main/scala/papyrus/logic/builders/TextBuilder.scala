package papyrus.logic.builders

import papyrus.logic.layerElement.text.Text
import papyrus.logic.styleObjects.TextStyle
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.DefaultValues

class TextBuilder:
  var value: String = DefaultValues.defaultText
  var color: ColorString = DefaultValues.colorText
  var fontWeight: FontWeight = DefaultValues.fontWeightText
  var fontStyle: FontStyle = DefaultValues.fontStyleText
  var textDecoration: TextDecoration = DefaultValues.textDecorationText

  def build(): Text = Text(value)(TextStyle(
    color = color,
    fontWeight = fontWeight,
    fontStyle = fontStyle,
    textDecoration = textDecoration
  ))