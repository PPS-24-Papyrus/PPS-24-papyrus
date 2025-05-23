package papyrus.logic

import papyrus.logic.Renderer.RendererStyle

object Style:
   
   trait Style extends RendererStyle:
     def color: String
     
   object Style:
     def apply() = ???
     
