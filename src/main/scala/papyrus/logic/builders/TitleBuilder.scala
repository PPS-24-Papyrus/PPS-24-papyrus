package papyrus.logic.builders

import papyrus.logic.layerElement.text.Title
import papyrus.logic.styleObjects.TitleStyle
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.DefaultValues


class TitleBuilder:
  var title: String = DefaultValues.titleTextH1
  var level: Level = DefaultValues.levelH1
  var font: FontFamily = DefaultValues.fontTitleH1
  var fontSize: FontSize = DefaultValues.fontSizeTitleH1
  var textColor: ColorString = DefaultValues.textColorTitleH1
  var textAlign: Alignment = DefaultValues.textAlignTitleH1

  class TitleBuilder:
    var title: String = DefaultValues.titleTextH1
    var level: Level = DefaultValues.levelH1

  def build(): Title =
    val (font, fontSize, textColor, textAlign) = level match
      case 2 => (DefaultValues.fontTitleH2, DefaultValues.fontSizeTitleH2, DefaultValues.textColorTitleH2, DefaultValues.textAlignTitleH2)
      case 3 => (DefaultValues.fontTitleH3, DefaultValues.fontSizeTitleH3, DefaultValues.textColorTitleH3, DefaultValues.textAlignTitleH3)
      case _ => (DefaultValues.fontTitleH1, DefaultValues.fontSizeTitleH1, DefaultValues.textColorTitleH1, DefaultValues.textAlignTitleH1)
    
    Title(title, level)(
      TitleStyle(
        font = font,
        fontSize = fontSize,
        textColor = textColor,
        textAlign = textAlign
      )
    )
