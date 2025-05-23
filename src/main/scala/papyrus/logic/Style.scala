package papyrus.logic

import papyrus.logic.Renderer.RendererStyle

import java.awt.Color

object Style:

   trait Style extends RendererStyle:
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
     




   object Style:
     def apply() = ???

