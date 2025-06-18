package papyrus.DSL.builders

import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.layerElement.Listing
import papyrus.logic.layerElement.text.Item
import scala.collection.mutable.ListBuffer

case class ListBuilder(
                        private val items: ListBuffer[Item] = ListBuffer.empty,
                        private var listType: ListType = "ul"
                      ) extends LayerElementBuilder:

  def listType(newType: ListType): ListBuilder =
    listType = newType
    this

  def addItem(item: Item): ListBuilder =
    items += item
    this

  override def build: Listing = Listing(listType, items.toSeq *)

object ListBuilder:
  def apply(): ListBuilder = new ListBuilder()

