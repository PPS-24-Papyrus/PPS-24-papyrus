package papyrus.logic.styleObjects

import papyrus.logic.Renderer.RendererStyle
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.dsl.DefaultValues
import papyrus.logic.Renderer.Text.*
import papyrus.logic.Style

/** Describes the main visual style for a document (applied to <body>) */
trait MainStyle extends RendererStyle:

  /** Font family used in the document */
  def font: FontFamily

  /** Font size in points */
  def fontSize: FontSize

  /** Line height multiplier */
  def lineHeight: LineHeight

  /** Text color */
  def textColor: ColorString

  /** Background color of the page */
  def backgroundColor: ColorString

  /** Text alignment (e.g. left, justify) */
  def textAlign: Alignment

  /** Margin around the body content */
  def margin: Margin

  /** CSS tag to which the style applies (always "body") */
  def tag: String

  
object MainStyle:

  /** Creates a MainStyle instance with default or custom values */
  def apply(
             font: FontFamily = DefaultValues.font,
             fontSize: FontSize = DefaultValues.fontSize,
             lineHeight: LineHeight = DefaultValues.lineHeight,
             textColor: ColorString = DefaultValues.colorText,
             backgroundColor: ColorString = DefaultValues.backgroundColor,
             textAlign: Alignment = DefaultValues.textAlignText,
             margin: Margin = DefaultValues.margin
           ): MainStyle =
    MainStyleImpl(font, fontSize, lineHeight, textColor, backgroundColor, textAlign, margin)

  private class MainStyleImpl(
                               override val font: FontFamily,
                               override val fontSize: FontSize,
                               override val lineHeight: LineHeight,
                               override val textColor: ColorString,
                               override val backgroundColor: ColorString,
                               override val textAlign: Alignment,
                               override val margin: Margin
                             ) extends MainStyle:
    override def renderStyle: StyleText =
      Seq(
        Style.font(font),
        Style.fontSize(fontSize),
        Style.lineHeight(lineHeight),
        Style.textColor(textColor),
        Style.backgroundColor(backgroundColor),
        Style.textAlign(textAlign),
        Style.margin(margin),
      ).mkString("\n  ").toStyleText
      
    override def tag: String =
      s"""body""" 