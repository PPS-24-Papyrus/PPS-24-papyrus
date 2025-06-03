package papyrus.logic.layerElement.text


import papyrus.logic.utility.TypesInline.*
import papyrus.logic.layerElement.LayerElement
import io.github.iltotore.iron.autoRefine

trait Item extends LayerElement:
  def item: String

object Item:

  def apply(text: String): Item = ItemImpl(text)

  private class ItemImpl(
                          override val item: String,
                        ) extends Item:

    override def render: String =
      s"""<li>$item</li>"""

    override def renderStyle: String =
      s""
