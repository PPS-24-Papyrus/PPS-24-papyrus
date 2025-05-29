package papyrus.logic.builders

import papyrus.logic.layerElement.text.Title
import papyrus.logic.styleObjects.TitleStyle
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine


class TitleBuilder:
  var title: String = "Default Title"
  var level: Level = 1
  var font: FontFamily = "Georgia"
  var fontSize: FontSize = 24
  var textColor: ColorString = "red"
  var textAlign: Alignment = "left"

  def build(): Title =
    Title(title, level)(TitleStyle(
      font=font,
      fontSize=fontSize,
      textColor=textColor,
      textAlign=textAlign)
    )