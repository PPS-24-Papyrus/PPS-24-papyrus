package papyrus.logic.styleObjects

import papyrus.logic.Renderer.RendererStyle
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.Style

trait MainStyle extends RendererStyle:
  def font: FontFamily
  def fontSize: FontSize
  def lineHeight: LineHeight
  def textColor: ColorString
  def backgroundColor: ColorString
  def margin: Margin
  def tag: String
  
object MainStyle:
  def apply(
             font: FontFamily = "Georgia",
             fontSize: FontSize = 16,
             lineHeight: LineHeight = 1.6,
             textColor: ColorString = "#000000",
             backgroundColor: ColorString = "grey",
             margin: Margin = 80
           ): MainStyle =
    MainStyleImpl(font, fontSize, lineHeight, textColor, backgroundColor, margin)

  private class MainStyleImpl(
                               override val font: FontFamily,
                               override val fontSize: FontSize,
                               override val lineHeight: LineHeight,
                               override val textColor: ColorString,
                               override val backgroundColor: ColorString,
                               override val margin: Margin
                             ) extends MainStyle:
    override def renderStyle: String =
      Seq(
        Style.font(font),
        Style.fontSize(fontSize),
        Style.lineHeight(lineHeight),
        Style.textColor(textColor),
        Style.backgroundColor(backgroundColor),
        Style.margin(margin)
      ).mkString(" ")
      
    override def tag: String =
      s"""body""" 