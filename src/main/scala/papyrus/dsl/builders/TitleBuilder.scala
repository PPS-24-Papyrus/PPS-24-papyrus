package papyrus.dsl.builders

import papyrus.logic.layerElement.text.Title
import papyrus.logic.styleObjects.TitleStyle
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.dsl.DefaultValues

/** Builds styled title elements for Papyrus document */
case class TitleBuilder(
                         private val title: String = DefaultValues.titleTextH1,
                         private val level: Level = DefaultValues.levelH1,
                         private val font: FontFamily = DefaultValues.fontTitleH1,
                         private val fontSize: FontSize = DefaultValues.fontSizeTitleH1,
                         private val textColor: ColorString = DefaultValues.textColorTitleH1,
                         private val textAlign: Alignment = DefaultValues.textAlignTitleH1
                       ) extends LayerElementBuilder:

  private def withTitle(t: String): TitleBuilder = this.copy(title = t)
  private def withLevel(l: Level): TitleBuilder = this.copy(level = l)
  private def withFont(f: FontFamily): TitleBuilder = this.copy(font = f)
  private def withFontSize(fs: FontSize): TitleBuilder = this.copy(fontSize = fs)
  private def withTextColor(tc: ColorString): TitleBuilder = this.copy(textColor = tc)
  private def withTextAlign(ta: Alignment): TitleBuilder = this.copy(textAlign = ta)

  /** Builds a styled Title element */
  override def build: Title =
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
  /** Creates a TitleBuilder (default: Helvetica 32pt black centered) */
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
    /** Sets the title text */
    def title(t: String): TitleBuilder = tb.withTitle(t)

    /** Sets the heading level (Int: 1 to 3) */
    def level(l: Level): TitleBuilder = tb.withLevel(l)

    /** Sets the font family (e.g. "Arial", "Helvetica", "Times New Roman", ...) */
    def font(f: FontFamily): TitleBuilder = tb.withFont(f)

    /** Sets the font size (Int: 8 to 72) */
    def fontSize(fs: FontSize): TitleBuilder = tb.withFontSize(fs)

    /** Sets the text color (ColorString: "#fff", "rgb(...)", or named colors) */
    def textColor(tc: ColorString): TitleBuilder = tb.withTextColor(tc)

    /** Sets the text alignment (String: "left", "right", "center", "justify", "start", "end") */
    def textAlign(ta: Alignment): TitleBuilder = tb.withTextAlign(ta)
