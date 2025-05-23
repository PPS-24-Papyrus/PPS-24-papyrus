package papyrus.logic


object Renderer:
    
  trait RendererStyle:
    def renderStyle: String

  trait Renderer extends RendererStyle:
    def render: String