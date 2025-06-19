package papyrus.DSL.builders

import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.layerElement.{ListElement, Listing}
import papyrus.logic.layerElement.text.Item

class ListBuilderProxy(
                        get: () => ListBuilder,
                        set: ListBuilder => Unit
                      ) extends ListBuilder(get().items, get().listType, get().order, get().reference):

  override def withListType(tpe: ListType): ListBuilder =
    val updated = get().withListType(tpe)
    set(updated)
    this

  override def ordered(orderType: SortingList): ListBuilder =
    val updated = get().ordered(orderType)
    set(updated)
    this

  override def reverseListing: ListBuilder =
    val updated = get().reverseListing
    set(updated)
    this

  override def withReference(str: String): ListBuilder =
    val updated = get().withReference(str)
    set(updated)
    this

  override def add(item: ListElement): ListBuilder =
    val updated = get().add(item)
    set(updated)
    this

  override def build: Listing =
    get().build
