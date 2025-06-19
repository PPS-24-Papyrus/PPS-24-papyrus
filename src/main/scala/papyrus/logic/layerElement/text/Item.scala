package papyrus.logic.layerElement.text


import papyrus.logic.utility.TypesInline.*
import papyrus.logic.layerElement.{LayerElement, ListElement}
import io.github.iltotore.iron.autoRefine
import papyrus.logic.Renderer.Text.*


trait Item extends ListElement:
  def item: String

object Item:

  def apply(text: String): Item = ItemImpl(text)

  private class ItemImpl(
                          override val item: String,
                        ) extends Item:

    override def render: MainText =
      s"""<li>$item</li>""".toMainText

    override def renderStyle: StyleText =
      s"".toStyleText
