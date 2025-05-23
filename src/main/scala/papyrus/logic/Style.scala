package papyrus.logic

import papyrus.logic.Renderer.RendererStyle

import java.awt.Color

object Style:

   trait Style extends RendererStyle:
     def id: String
     def textColor(color: Color): Unit
     def backgroundColor(color: Color): Unit
     def font(font: String): Unit
     def fontSize(size: Int): Unit
     def margin(top: Int, right: Int, bottom: Int, left: Int): Unit
     def margin(margin: Int): Unit
     def padding(top: Int, right: Int, bottom: Int, left: Int): Unit
     def padding(padding: Int): Unit
     def border(top: Int, right: Int, bottom: Int, left: Int): Unit
     def border(border: Int): Unit
     def lineHeight(lineHeight: Double): Unit //interlinea
     def textAlign(position: String): Unit
     def width(width: Int): Unit
     def height(height: Int): Unit
     
     //TODO: opaque type for size, padding, margin, border
     //TODO: enum for textAlign, position, font(si puo fare ma anche no), color(si puo fare ma anche no)



   object Style:
     def apply() = ???

