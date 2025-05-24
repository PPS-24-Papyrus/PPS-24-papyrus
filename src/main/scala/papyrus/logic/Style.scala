package papyrus.logic

import papyrus.logic.Renderer.RendererStyle
import papyrus.logic.Utility.TypesInline.*

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
     def apply(id: String): Style = StyleImpl(id)

     private class StyleImpl(override val id: String) extends Style:
       override def textColor(color: Color): Unit = ???
       override def backgroundColor(color: Color): Unit = ???
       override def font(font: FontFamily): Unit = ???
       override def fontSize(size: FontSize): Unit = ???
       override def margin(top: Margin, right: Margin, bottom: Margin, left: Margin): Unit = ???
       override def margin(margin: Margin): Unit = ???
       override def padding(top: Padding, right: Padding, bottom: Padding, left: Padding): Unit = ???
       override def padding(padding: Padding): Unit = ???
       override def border(top: Border, right: Border, bottom: Border, left: Border): Unit = ???
       override def border(border: Border): Unit = ???
       override def lineHeight(lineHeight: LineHeight): Unit = ???
       override def textAlign(position: Alignment): Unit = ???
       override def width(width: Width): Unit = ???
       override def height(height: Height): Unit = ???
       override def renderStyle: String = ???
        
       


