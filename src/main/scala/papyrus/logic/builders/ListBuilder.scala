package papyrus.logic.builders

import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.content.Listing
import papyrus.logic.layerElement.text.Item

import java.util.Optional
import scala.collection.mutable.ArrayBuffer

class ListBuilder:
  private val items = ArrayBuffer[Item]()

  def addItem(item: Item): Unit =
    items += item

  def build(): Listing = Listing(items.toSeq*)