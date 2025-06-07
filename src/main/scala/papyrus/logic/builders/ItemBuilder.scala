package papyrus.logic.builders

import papyrus.logic.layerElement.text.{Item, Text}
import papyrus.logic.styleObjects.TextStyle
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.DefaultValues

class ItemBuilder:
  var value: String = DefaultValues.defaultItem

  def build(): Item = Item(value)
