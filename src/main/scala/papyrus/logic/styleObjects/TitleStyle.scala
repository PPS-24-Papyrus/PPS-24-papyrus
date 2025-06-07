package papyrus.logic.styleObjects

import papyrus.logic.Style
import papyrus.logic.utility.TypesInline.*
import papyrus.logic.layerElement.LayerElement
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.DefaultValues
import papyrus.logic.Renderer.RendererStyle
import papyrus.logic.utility.IdGenerator

trait TitleStyle extends RendererStyle:
  def font: FontFamily
  def fontSize: FontSize
  def textColor: ColorString
  def textAlign: Alignment

object TitleStyle:
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

    override def renderStyle: String =
      Seq(
        Style.font(font),
        Style.fontSize(fontSize),
        Style.textColor(textColor),
        Style.textAlign(textAlign)
      ).mkString(" ")

