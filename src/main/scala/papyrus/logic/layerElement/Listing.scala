package papyrus.logic.layerElement

import papyrus.logic.Renderer.Text.*
import papyrus.logic.layerElement.text.{Item, Text, Title}
import papyrus.logic.utility.TypesInline.ListType

trait Listing extends LayerElement:
  def items: Seq[Item]
  def listType: ListType

object Listing:

  def apply(listType: ListType, items: Item*): Listing = ListingImpl(listType, items.toSeq)

  private class ListingImpl(override val listType: ListType, override val items: Seq[Item]) extends Listing:
    override def render: MainText =
      s"<$listType>\n${items.map(_.render).mkString("\n")}\n</$listType>".toMainText
  
    override def renderStyle: StyleText = "".toStyleText
