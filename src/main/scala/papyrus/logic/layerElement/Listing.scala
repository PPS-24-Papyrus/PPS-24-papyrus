package papyrus.logic.layerElement

import papyrus.logic.layerElement.text.{Item, Text, Title}

trait Listing extends LayerElement:
  def items: Seq[Item]

object Listing:

  def apply(items: Item*): Listing = ListingImpl(items.toSeq)

  private class ListingImpl(override val items: Seq[Item]) extends Listing:
    override def render: String =
      s"<ul>\n${items.map(_.render).mkString("\n")}\n</ul>"
  
    override def renderStyle: String = ""
