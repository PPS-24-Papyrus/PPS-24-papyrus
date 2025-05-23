package papyrus.logic.metadata

import papyrus.logic.Renderer.Renderer
import papyrus.logic.Style.Style

object Metadata:

  trait Metadata extends Renderer:
    def style: Style
    def nameFile: String
    def author: String
    
  object Metadata:
    def apply() = ???