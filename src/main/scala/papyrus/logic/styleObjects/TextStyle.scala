package papyrus.logic.styleObjects

import papyrus.logic.Style
import papyrus.logic.utility.TypesInline.*
import papyrus.logic.layerElement.LayerElement
import io.github.iltotore.iron.autoRefine
import papyrus.logic.Renderer.RendererStyle

trait TextStyle extends RendererStyle:
  def color: ColorString
  def fontWeight: FontWeight
  def fontStyle: FontStyle
  def textDecoration: TextDecoration

object TextStyle:
  def apply(
             color: ColorString = "green",
             fontWeight: FontWeight = "none",
             fontStyle: FontStyle = "none",
             textDecoration: TextDecoration = "none",
           ): TextStyle = TextStyleImpl(color, fontWeight, fontStyle, textDecoration)

  private class TextStyleImpl(
                           override val color: ColorString,
                           override val fontWeight: FontWeight,
                           override val fontStyle: FontStyle,
                           override val textDecoration: TextDecoration
                         ) extends TextStyle:
    
    override def renderStyle: String =
      Seq(
        Style.textColor(color),
        Style.fontWeight(fontWeight),
        Style.fontStyle(fontStyle),
        Style.textDecoration(textDecoration)
      ).mkString(" ")

