package papyrus.logic.styleObjects

import papyrus.logic.Style
import papyrus.utility.DefaultValues
import papyrus.logic.Renderer.RendererStyle
import papyrus.logic.Renderer.Text.*
import papyrus.utility.TypesInline.*

/** Describes the visual style for title elements (e.g. <h1>, <h2>) */
trait TitleStyle extends RendererStyle:

  /** Font family used for the title */
  def font: FontFamily

  /** Font size for the title */
  def fontSize: FontSize

  /** Text color */
  def textColor: ColorString

  /** Text alignment (e.g. left, center) */
  def textAlign: Alignment


object TitleStyle:

  /** Creates a TitleStyle with optional overrides */
  def apply(
             font: FontFamily = DefaultValues.fontTitleH1,
             fontSize: FontSize = DefaultValues.fontSizeTitleH1,
             textColor: ColorString = DefaultValues.textColorTitleH1,
             textAlign: Alignment = DefaultValues.textAlignTitleH1
           ): TitleStyle = new TitleStyleImpl(font, fontSize, textColor, textAlign)

  private class TitleStyleImpl(
                           override val font: FontFamily,
                           override val fontSize: FontSize,
                           override val textColor: ColorString,
                           override val textAlign: Alignment
                         ) extends TitleStyle:

    override def renderStyle: StyleText =
      Seq(
        Style.font(font),
        Style.fontSize(fontSize),
        Style.textColor(textColor),
        Style.textAlign(textAlign)
      ).mkString(" ").toStyleText

