package papyrus.dsl.builders.listBuilder

import papyrus.logic.layerElement.text.Item
import papyrus.utility.DefaultValues
import papyrus.dsl.builders.ListElementBuilder

/** Builds a list item (only allowed inside Listing) */
case class ItemBuilder(
                        value: String
                      ) extends ListElementBuilder:

  private def withValue(v: String): ItemBuilder = this.copy(value = v)

  /** Builds the Item element */
  override def build: Item = Item(value)

object ItemBuilder:
  def apply(value: String = DefaultValues.defaultItem): ItemBuilder =
    new ItemBuilder(value)

  extension (ib: ItemBuilder)
    /** Sets the item value */
    def value(v: String): ItemBuilder = ib.withValue(v)