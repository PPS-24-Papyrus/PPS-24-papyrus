package papyrus.logic.builders

import papyrus.logic.layerElement.text.Title
import papyrus.logic.styleObjects.TitleStyle
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.DefaultValues


class TitleBuilder:
  var title: String = DefaultValues.titleText
  var level: Level = DefaultValues.level
  var font: FontFamily = DefaultValues.fontTitle
  var fontSize: FontSize = DefaultValues.fontSizeTitle
  var textColor: ColorString = DefaultValues.textColorTitle
  var textAlign: Alignment = DefaultValues.textAlignTitle

  def build(): Title =
    Title(title, level)(TitleStyle(
      font=font,
      fontSize=fontSize,
      textColor=textColor,
      textAlign=textAlign)
    )