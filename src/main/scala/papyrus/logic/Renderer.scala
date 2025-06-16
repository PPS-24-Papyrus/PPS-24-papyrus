package papyrus.logic

import papyrus.logic.Renderer.Text.{MainText, StyleText}


object Renderer:

  object Text:
    opaque type MainText = String
    opaque type StyleText = String

    object MainText:
      def apply(s: String): MainText = s
      def value(m: MainText): String = m

      extension (s: MainText)
        def string: String = s

    object StyleText:
      def apply(s: String): StyleText = s
      def value(s: StyleText): String = s
      
      extension (s: StyleText)
        def string: String = s

    extension (s: String)
      def toMainText: MainText = MainText(s)
      def toStyleText: StyleText = StyleText(s)
  


  trait RendererStyle:
    def renderStyle: StyleText

  trait Renderer extends RendererStyle:
    def render: MainText