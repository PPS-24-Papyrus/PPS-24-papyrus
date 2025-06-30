package papyrus.dsl.builders.listBuilder

import io.github.iltotore.iron.autoRefine
import papyrus.logic.layerElement.Listing
import papyrus.utility.OrderingAlgorithms.levenshtein
import scala.math
import papyrus.utility.TypesInline.*
import papyrus.dsl.builders.ListElementBuilder

/** Represents a nestable list builder with optional sorting (only allowed inside Papyrus, Content, Section, SubSection and Listing) */
trait ListBuilder extends ListElementBuilder:
  def items: List[ListElementBuilder]
  def listType: ListType
  def order: Option[SortingList]
  def reference: Option[String]

  /** Sets the list type ("ul" or "ol") */
  def withListType(tpe: ListType): ListBuilder

  /** Applies sorting based on the provided order type ("alphabetic", "length", "revert", "levenshtein") */
  def withSortingProperties(orderType: SortingList): ListBuilder

  /** Sets a reference string used for Levenshtein sorting */
  def withReference(str: String): ListBuilder

  /** Adds an item or a nested list */
  def add(element: ListElementBuilder): ListBuilder

  /** Builds the final listing */
  def build: Listing

  /** Creates a modified copy with custom internal state */
  def copyWith(
                items: List[ListElementBuilder] = this.items,
                listType: ListType = this.listType,
                order: Option[SortingList] = this.order,
                reference: Option[String] = this.reference
              ): ListBuilder

/** Concrete implementation of ListBuilder with sorting and nesting */
case class ListBuilderImpl(
                            items: List[ListElementBuilder] = Nil,
                            listType: ListType = "ul",
                            order: Option[SortingList] = None,
                            reference: Option[String] = None
                          ) extends ListBuilder:

  def withListType(tpe: ListType): ListBuilder =
    copy(listType = tpe)

  def withSortingProperties(orderType: SortingList): ListBuilder =
    copy(order = Some(orderType))

  def withReference(ref: String): ListBuilder =
    copy(reference = Some(ref), order = Some("levenshtein"))

  def add(element: ListElementBuilder): ListBuilder =
    copy(items = items :+ element)

  override def copyWith(
                         items: List[ListElementBuilder],
                         listType: ListType,
                         order: Option[SortingList],
                         reference: Option[String]
                       ): ListBuilder =
    this.copy(items, listType, order, reference)

  override def build: Listing =
    val finalBuilder = ListStructureTransformer.transform(this)
    Listing(finalBuilder.listType, finalBuilder.items.map(_.build)*)


private object ListStructureTransformer:

  def transform(builder: ListBuilder): ListBuilder =
    val updatedChildren = builder.items.map {
      case lb: ListBuilder => transform(lb)
      case other           => other
    }

    val updatedBuilder = builder.copyWith(items = updatedChildren)
    val associationMap = findSublistParents(updatedBuilder)
    val sortedItems = sortItems(updatedBuilder, updatedChildren)
    val finalItems = attachSublists(sortedItems, associationMap)

    updatedBuilder.copyWith(items = finalItems)

  private def findSublistParents(builder: ListBuilder): Map[ListBuilder, Option[ItemBuilder]] =
    val childMaps = builder.items.collect {
      case lb: ListBuilder => findSublistParents(lb)
    }.foldLeft(Map.empty[ListBuilder, Option[ItemBuilder]])(_ ++ _)

    val localPairs = builder.items.zipWithIndex.collect {
      case (sublist: ListBuilder, idx) =>
        val maybeItemAbove =
          if idx > 0 then builder.items(idx - 1) match
            case item: ItemBuilder => Some(item)
            case _                 => None
          else None
        sublist -> maybeItemAbove
    }.toMap

    childMaps ++ localPairs

  private def sortItems(
                             builder: ListBuilder,
                             elems: List[ListElementBuilder]
                           ): List[ItemBuilder] =
    val items = elems.collect { case i: ItemBuilder => i }
    builder.order match
      case Some("alphabetical") => items.sortBy(_.value.toLowerCase)
      case Some("length") => items.sortBy(_.value.length)
      case Some("reverse") => items.reverse
      case Some("levenshtein") =>
        val ref = builder.reference.getOrElse("")
        items.sortBy(i => levenshtein(i.value, ref))
      case _ => items

  private def attachSublists(
                              sortedItems: List[ItemBuilder],
                              associationMap: Map[ListBuilder, Option[ItemBuilder]]
                            ): List[ListElementBuilder] =
    sortedItems.flatMap { item =>
      val attached = associationMap.collect {
        case (sublist, Some(`item`)) => sublist
      }.toList
      item :: attached
    }
