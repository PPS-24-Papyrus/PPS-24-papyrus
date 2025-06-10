package papyrus.logic.builders

import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.layerElement.Listing
import papyrus.logic.layerElement.text.Item
import scala.collection.mutable.ListBuffer

case class ListBuilder(private val items: ListBuffer[Item] = ListBuffer.empty):
  def addItem(item: Item): Unit =
    items += item

  def build: Listing = Listing(items.toSeq*)

object ListBuilder:
  def apply(): ListBuilder = new ListBuilder()
