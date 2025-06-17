package papyrus.DSL.builders

import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.layerElement.Listing
import papyrus.logic.layerElement.text.Item

import scala.collection.mutable.ListBuffer
import scala.math

case class ListBuilder(
                        private val items: ListBuffer[Item] = ListBuffer.empty,
                        private var listType: ListType = "ul",
                        private var ordered: Boolean = false
                      ) extends LayerElementBuilder:

  def listType(newType: ListType): ListBuilder =
    listType = newType
    this

  def order: ListBuilder = 
    ordered=true
    this
  

  def addItem(item: Item): ListBuilder =
    items += item
    this

  extension (items: ListBuffer[Item])
    private def sortIf(ordered: Boolean): ListBuffer[Item] =
      ordered match
        case true  => items.sortBy(_.item.toLowerCase)
        case false => items




  override def build: Listing = Listing(listType, items.sortIf(ordered).toSeq *)

object ListBuilder:
  def apply(): ListBuilder = new ListBuilder()



