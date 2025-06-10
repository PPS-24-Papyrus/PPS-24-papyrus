package papyrus.logic.builders

import papyrus.logic.layerElement.text.{Item, Text}
import papyrus.logic.styleObjects.TextStyle
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.DefaultValues

case class ItemBuilder(
                        private val value: String
                      ):
  private def withValue(v: String): ItemBuilder = this.copy(value = v)

  def build: Item = Item(value)

object ItemBuilder:
  def apply(value: String = DefaultValues.defaultItem): ItemBuilder =
    new ItemBuilder(value)

  extension (ib: ItemBuilder)
    def value(v: String): ItemBuilder = ib.withValue(v)
