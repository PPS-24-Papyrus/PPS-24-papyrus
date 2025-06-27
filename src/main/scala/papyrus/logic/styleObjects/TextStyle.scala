package papyrus.logic.styleObjects

import papyrus.logic.Style
import papyrus.logic.layerElement.LayerElement
import io.github.iltotore.iron.autoRefine
import papyrus.utility.DefaultValues
import papyrus.logic.Renderer.RendererStyle
import papyrus.logic.Renderer.Text.*
import papyrus.utility.IdGenerator
import papyrus.utility.TypesInline.*

/** Represents the visual style for inline text (e.g. <span>) */
trait TextStyle extends RendererStyle:

  /** Text color */
  def color: ColorString

  /** Font weight (e.g. normal, bold) */
  def fontWeight: FontWeight

  /** Font style (e.g. normal, italic) */
  def fontStyle: FontStyle

  /** Text decoration (e.g. underline, none) */
  def textDecoration: TextDecoration

  /** CSS class tag to apply this style */
  def tag: String


object TextStyle:

  /** Creates a TextStyle with optional custom values */
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
    
    override def renderStyle: StyleText =
      List(
        Option.when(color != DefaultValues.colorText)(Style.textColor(color)),
        Option.when(fontWeight != DefaultValues.fontWeightText)(Style.fontWeight(fontWeight)),
        Option.when(fontStyle != DefaultValues.fontStyleText)(Style.fontStyle(fontStyle)),
        Option.when(textDecoration != DefaultValues.textDecorationText)(Style.textDecoration(textDecoration))
      ).flatten.mkString(" ").toStyleText

    override def tag: String =
      s"cls-$id"

