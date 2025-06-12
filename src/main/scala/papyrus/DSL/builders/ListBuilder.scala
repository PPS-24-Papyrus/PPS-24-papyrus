package papyrus.DSL.builders

import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.layerElement.Listing
import papyrus.logic.layerElement.text.Item
import scala.collection.mutable.ListBuffer

case class ListBuilder(private val items: ListBuffer[Item] = ListBuffer.empty) extends Builder[Listing]:
  def addItem(item: Item): Unit =
    items += item

  override def build: Listing = Listing(items.toSeq*)

object ListBuilder:
  def apply(): ListBuilder = new ListBuilder()
