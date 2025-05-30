package papyrus.logic.styleObjects

import papyrus.logic.Style
import papyrus.logic.utility.TypesInline.*
import papyrus.logic.layerElement.LayerElement
import io.github.iltotore.iron.autoRefine
import papyrus.logic.Renderer.RendererStyle

trait TitleStyle extends RendererStyle:
  def font: FontFamily
  def fontSize: FontSize
  def textColor: ColorString
  def textAlign: Alignment

object TitleStyle:
  def apply(
             font: FontFamily = "Georgia",
             fontSize: FontSize = 24,
             textColor: ColorString = "#000000",
             textAlign: Alignment = "left"
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

