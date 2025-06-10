package papyrus.logic.builders

import papyrus.logic.layerElement.text.Title
import papyrus.logic.styleObjects.TitleStyle
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.DefaultValues

case class TitleBuilder(
                         private val title: String = DefaultValues.titleTextH1,
                         private val level: Level = DefaultValues.levelH1,
                         private val font: FontFamily = DefaultValues.fontTitleH1,
                         private val fontSize: FontSize = DefaultValues.fontSizeTitleH1,
                         private val textColor: ColorString = DefaultValues.textColorTitleH1,
                         private val textAlign: Alignment = DefaultValues.textAlignTitleH1
                       ):

  private def withTitle(t: String): TitleBuilder = this.copy(title = t)
  private def withLevel(l: Level): TitleBuilder = this.copy(level = l)
  private def withFont(f: FontFamily): TitleBuilder = this.copy(font = f)
  private def withFontSize(fs: FontSize): TitleBuilder = this.copy(fontSize = fs)
  private def withTextColor(tc: ColorString): TitleBuilder = this.copy(textColor = tc)
  private def withTextAlign(ta: Alignment): TitleBuilder = this.copy(textAlign = ta)

  def build: Title =
    val (finalFont, finalFontSize, finalTextColor, finalTextAlign) = level match
      case 2 => (DefaultValues.fontTitleH2, DefaultValues.fontSizeTitleH2, DefaultValues.textColorTitleH2, DefaultValues.textAlignTitleH2)
      case 3 => (DefaultValues.fontTitleH3, DefaultValues.fontSizeTitleH3, DefaultValues.textColorTitleH3, DefaultValues.textAlignTitleH3)
      case _ => (font, fontSize, textColor, textAlign)

    Title(title, level)(
      TitleStyle(
        font = finalFont,
        fontSize = finalFontSize,
        textColor = finalTextColor,
        textAlign = finalTextAlign
      )
    )

object TitleBuilder:
  def apply(
             title: String = DefaultValues.titleTextH1,
             level: Level = DefaultValues.levelH1,
             font: FontFamily = DefaultValues.fontTitleH1,
             fontSize: FontSize = DefaultValues.fontSizeTitleH1,
             textColor: ColorString = DefaultValues.textColorTitleH1,
             textAlign: Alignment = DefaultValues.textAlignTitleH1
           ): TitleBuilder =
    new TitleBuilder(title, level, font, fontSize, textColor, textAlign)

  extension (tb: TitleBuilder)
    def title(t: String): TitleBuilder = tb.withTitle(t)
    def level(l: Level): TitleBuilder = tb.withLevel(l)
    def font(f: FontFamily): TitleBuilder = tb.withFont(f)
    def fontSize(fs: FontSize): TitleBuilder = tb.withFontSize(fs)
    def textColor(tc: ColorString): TitleBuilder = tb.withTextColor(tc)
    def textAlign(ta: Alignment): TitleBuilder = tb.withTextAlign(ta)
