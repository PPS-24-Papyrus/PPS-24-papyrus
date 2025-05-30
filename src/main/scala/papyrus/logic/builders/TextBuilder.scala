package papyrus.logic.builders

import papyrus.logic.layerElement.text.Text
import papyrus.logic.styleObjects.TextStyle
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine

class TextBuilder:
  var value: String = "Default Text"
  var color: ColorString = "black"
  var fontWeight: FontWeight = "normal"
  var fontStyle: FontStyle = "normal"
  var textDecoration: TextDecoration = "none"

  def build(): Text = Text(value)(TextStyle(
    color = color,
    fontWeight = fontWeight,
    fontStyle = fontStyle,
    textDecoration = textDecoration
  ))