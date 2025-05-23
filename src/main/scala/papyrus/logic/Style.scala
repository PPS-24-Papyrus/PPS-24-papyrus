package papyrus.logic

import papyrus.logic.Renderer.RendererStyle
import papyrus.logic.Utility.StyleTypes.Alignment.Alignment
import papyrus.logic.Utility.StyleTypes.FontSize.*
import papyrus.logic.Utility.StyleTypes.Margin.*
import papyrus.logic.Utility.StyleTypes.Padding.*
import papyrus.logic.Utility.StyleTypes.Border.*
import papyrus.logic.Utility.StyleTypes.FontFamily.FontFamily
import papyrus.logic.Utility.StyleTypes.LineHeight.*
import papyrus.logic.Utility.StyleTypes.Width.*
import papyrus.logic.Utility.StyleTypes.Height.*

import java.awt.Color

object Style:

   trait Style extends RendererStyle:
     def id: String
     def textColor(color: Color): Unit
     def backgroundColor(color: Color): Unit
     def font(font: FontFamily): Unit
     def fontSize(size: FontSize): Unit
     def margin(top: Margin, right: Margin, bottom: Margin, left: Margin): Unit
     def margin(margin: Margin): Unit
     def padding(top: Padding, right: Padding, bottom: Padding, left: Padding): Unit
     def padding(padding: Padding): Unit
     def border(top: Border, right: Border, bottom: Border, left: Border): Unit
     def border(border: Border): Unit
     def lineHeight(lineHeight: LineHeight): Unit //interlinea
     def textAlign(position: Alignment): Unit
     def width(width: Width): Unit
     def height(height: Height): Unit
     
     //TODO: opaque type for size, padding, margin, border
     //TODO: enum for textAlign, position, font(si puo fare ma anche no), color(si puo fare ma anche no)



   object Style:
     def apply() = ???

