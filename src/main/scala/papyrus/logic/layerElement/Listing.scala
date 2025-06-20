package papyrus.logic.layerElement

import papyrus.logic.Renderer.Text.*
import papyrus.logic.layerElement.text.{Item, Text, Title}
import papyrus.logic.utility.TypesInline.ListType

trait Listing extends ListElement:
  def items: Seq[ListElement]
  def listType: ListType

object Listing:

  def apply(listType: ListType, items: ListElement*): Listing =
    ListingImpl(listType, items.toSeq)

  private class ListingImpl(
                             override val listType: ListType,
                             override val items: Seq[ListElement]
                           ) extends Listing:

    override def render: MainText =
      val renderedItems = items.map {
        case i: Item => i.render.string
        case l: Listing => l.render.string
      }.mkString("\n")

      s"<$listType>\n$renderedItems\n</$listType>".toMainText
    override def renderStyle: StyleText = "".toStyleText

