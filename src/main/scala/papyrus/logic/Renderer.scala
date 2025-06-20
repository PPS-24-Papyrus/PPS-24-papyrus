package papyrus.logic

import papyrus.logic.Renderer.Text.{MainText, StyleText}


/** Base module for rendering HTML and CSS fragments */
object Renderer:

  object Text:

    /** Opaque type representing HTML content to render */
    opaque type MainText = String

    /** Opaque type representing CSS style blocks */
    opaque type StyleText = String

    object MainText:
      /** Wraps a string as MainText (HTML content) */
      def apply(s: String): MainText = s

      /** Extracts the raw string from MainText */
      def value(m: MainText): String = m

      extension (s: MainText)
        /** Returns the inner string */
        def string: String = s

    object StyleText:
      /** Wraps a string as StyleText (CSS content) */
      def apply(s: String): StyleText = s

      /** Extracts the raw string from StyleText */
      def value(s: StyleText): String = s

      extension (s: StyleText)
        /** Returns the inner string */
        def string: String = s

    extension (s: String)
      /** Converts a raw string to MainText */
      def toMainText: MainText = MainText(s)

      /** Converts a raw string to StyleText */
      def toStyleText: StyleText = StyleText(s)


  /** Anything that can render CSS style information */
  trait RendererStyle:
    def renderStyle: StyleText
  
  /** Anything that can render HTML content and styles */
  trait Renderer extends RendererStyle:
    def render: MainText
  