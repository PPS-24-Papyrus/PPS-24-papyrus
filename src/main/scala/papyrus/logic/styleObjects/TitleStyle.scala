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
  def tag: String

object TitleStyle:
  def apply(
             font: FontFamily,
             fontSize: FontSize,
             textColor: ColorString,
             textAlign: Alignment
           ): TitleStyle = new TitleStyleImpl(font, fontSize, textColor, textAlign)

  private class TitleStyleImpl(
                           override val font: FontFamily,
                           override val fontSize: FontSize,
                           override val textColor: ColorString,
                           override val textAlign: Alignment
                         ) extends TitleStyle:
    private val id: String = IdGenerator.nextId()
    
    override def renderStyle: String =
      Seq(
        Style.font(font),
        Style.fontSize(fontSize),
        Style.textColor(textColor),
        Style.textAlign(textAlign)
      ).mkString(" ")

    override def tag: String = 
      s"cls-$id"
    
