package papyrus.logic.layerElement

import papyrus.logic.layerElement.text.{Item, Text, Title}

trait Listing extends LayerElement:
  def items: Seq[Item]
  def listType: String = "ul"

object Listing:

  def apply(items: Item*): Listing = ListingImpl(items.toSeq)

  private class ListingImpl(override val items: Seq[Item]) extends Listing:
    override def render: String =
      s"<$listType>\n${items.map(_.render).mkString("\n")}\n</$listType>"
  
    override def renderStyle: String = ""
