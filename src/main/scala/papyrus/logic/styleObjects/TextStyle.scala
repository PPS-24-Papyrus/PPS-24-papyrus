package papyrus.logic.styleObjects

import papyrus.logic.Style
import papyrus.logic.utility.TypesInline.*
import papyrus.logic.layerElement.LayerElement
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.DefaultValues
import papyrus.logic.Renderer.RendererStyle
import papyrus.logic.utility.IdGenerator

trait TextStyle extends RendererStyle:
  def color: ColorString
  def fontWeight: FontWeight
  def fontStyle: FontStyle
  def textDecoration: TextDecoration
  def tag: String

object TextStyle:
  def apply(
             color: ColorString = DefaultValues.colorText,
             fontWeight: FontWeight = DefaultValues.fontWeightText,
             fontStyle: FontStyle = DefaultValues.fontStyleText,
             textDecoration: TextDecoration = DefaultValues.textDecorationText,
           ): TextStyle = TextStyleImpl(color, fontWeight, fontStyle, textDecoration)

  private class TextStyleImpl(
                           override val color: ColorString,
                           override val fontWeight: FontWeight,
                           override val fontStyle: FontStyle,
                           override val textDecoration: TextDecoration
                         ) extends TextStyle:
    private val id: String = IdGenerator.nextId()
    
    override def renderStyle: String =
      List(
        Option.when(color != DefaultValues.colorText)(Style.textColor(color)),
        Option.when(fontWeight != DefaultValues.fontWeightText)(Style.fontWeight(fontWeight)),
        Option.when(fontStyle != DefaultValues.fontStyleText)(Style.fontStyle(fontStyle)),
        Option.when(textDecoration != DefaultValues.textDecorationText)(Style.textDecoration(textDecoration))
      ).flatten.mkString(" ")

    override def tag: String =
      s"cls-$id"

