package papyrus.dsl.builders

import io.github.iltotore.iron.autoRefine
import papyrus.logic.layerElement.Listing
import papyrus.utility.TypesInline.*

/** Proxy for ListBuilder */
class ListBuilderProxy(get: () => ListBuilder, set: ListBuilder => Unit) extends ListBuilder:

  /** Sets the list type ("ul" or "ol") */
  override def withListType(tpe: ListType): ListBuilder =
    val updated = get().withListType(tpe)
    set(updated)
    this

  /** Applies sorting based on the provided order type ("alphabetical", "length", "reverse", "levenshtein") */
  override def withSortingProperties(orderType: SortingList): ListBuilder =
    val updated = get().withSortingProperties(orderType)
    set(updated)
    this

  /** Sets a reference string used for Levenshtein sorting */
  override def withReference(str: String): ListBuilder =
    val updated = get().withReference(str)
    set(updated)
    this

  /** Adds an item or a nested list */
  override def add(item: ListElementBuilder): ListBuilder =
    val updated = get().add(item)
    set(updated)
    this

  /** Builds the final listing */
  override def build: Listing =
    get().build

  override def items: List[ListElementBuilder] = get().items
  override def listType: ListType = get().listType
  override def order: Option[SortingList] = get().order
  override def reference: Option[String] = get().reference

  /** Creates a modified copy with custom internal state */
  override def copyWith(
                         items: List[ListElementBuilder],
                         listType: ListType,
                         order: Option[SortingList],
                         reference: Option[String],
                       ): ListBuilder = get().copyWith(items, listType, order, reference)
