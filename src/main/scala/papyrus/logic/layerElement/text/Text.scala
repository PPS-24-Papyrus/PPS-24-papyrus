package papyrus.logic.layerElement.text

import papyrus.logic.Style
import papyrus.logic.utility.TypesInline.*
import papyrus.logic.layerElement.LayerElement
import io.github.iltotore.iron.autoRefine
import papyrus.logic.Renderer.Text.*
import papyrus.logic.styleObjects.TextStyle

trait Text extends LayerElement:
  def text: String

object Text:

  def apply(text: String)(
    textStyle: TextStyle
  ): Text = TextImpl(text)(textStyle)

  private class TextImpl(
                          override val text: String,
                        )(textStyle: TextStyle) extends Text:

    override def render: MainText =
      s"""<span class="${textStyle.tag}">${text.replace("\n", "<br></br>")}</span>""".toMainText


    override def renderStyle: StyleText =
      s""".${textStyle.tag} {\n  ${textStyle.renderStyle}\n}""".toStyleText

