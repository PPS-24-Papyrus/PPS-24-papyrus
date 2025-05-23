package papyrus.logic.metadata

import papyrus.logic.Renderer.Renderer

object Metadata:

  trait Metadata extends Renderer:
    def style: Style
    def nameFile: String
    def author: String
    
  object Metadata:
    def apply() = ???