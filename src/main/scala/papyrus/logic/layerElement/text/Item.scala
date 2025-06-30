package papyrus.logic.layerElement.text

import papyrus.logic.layerElement.{LayerElement, ListElement}
import papyrus.logic.Renderer.Text.*

/** Represents a list item (li) inside a Listing in Papyrus format */
trait Item extends ListElement:
  def item: String

object Item:

  /** Creates a new list item with the provided text content */
  def apply(text: String): Item = ItemImpl(text)

  private class ItemImpl(
                          override val item: String
                        ) extends Item:

    /** Renders the item as a Papyrus list element (<li>) */
    override def render: MainText =
      s"""<li>$item</li>""".toMainText

    /** List items do not have custom styles by default */
    override def renderStyle: StyleText =
      s"".toStyleText
