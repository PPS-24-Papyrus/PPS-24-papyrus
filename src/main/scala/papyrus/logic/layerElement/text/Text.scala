package papyrus.logic.layerElement.text

import papyrus.logic.Style
import papyrus.logic.layerElement.LayerElement
import io.github.iltotore.iron.autoRefine
import papyrus.logic.Renderer.Text.*
import papyrus.logic.styleObjects.TextStyle

/** Represents a styled text element in the document */
trait Text extends LayerElement:

  /** The raw text content */
  def text: String

object Text:

  /** Creates a Text element with given content and style */
  def apply(text: String)(textStyle: TextStyle): Text = TextImpl(text)(textStyle)

  private class TextImpl(
                          override val text: String,
                        )(textStyle: TextStyle) extends Text:

    override def render: MainText =
      s"""<span class="${textStyle.tag}">${text.replace("\n", "<br></br>")}</span>""".toMainText


    override def renderStyle: StyleText =
      s""".${textStyle.tag} {\n  ${textStyle.renderStyle}\n}""".toStyleText

