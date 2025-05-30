package papyrus.logic.styleObjects

import papyrus.logic.Renderer.RendererStyle
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.DefaultValues
import papyrus.logic.Style

trait MainStyle extends RendererStyle:
  def font: FontFamily
  def fontSize: FontSize
  def lineHeight: LineHeight
  def textColor: ColorString
  def backgroundColor: ColorString
  def textAlign: Alignment
  def margin: Margin
  def tag: String
  
object MainStyle:
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
    override def renderStyle: String =
      Seq(
        Style.font(font),
        Style.fontSize(fontSize),
        Style.lineHeight(lineHeight),
        Style.textColor(textColor),
        Style.backgroundColor(backgroundColor),
        Style.textAlign(textAlign),
        Style.margin(margin)
      ).mkString(" ")
      
    override def tag: String =
      s"""body""" 