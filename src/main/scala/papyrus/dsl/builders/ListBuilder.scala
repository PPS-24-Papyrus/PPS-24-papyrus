package papyrus.dsl.builders

import io.github.iltotore.iron.autoRefine
import papyrus.logic.layerElement.Listing
import scala.math
import papyrus.utility.TypesInline.*

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

  private def sortItemAndSublists(
                                   builder: ListBuilder,
                                   elems: List[ListElementBuilder],
                                   associationMap: Map[ListBuilder, Option[ItemBuilder]]
                                 ): List[ListElementBuilder] =
    val items = elems.collect { case i: ItemBuilder => i }
    val sortedItems = builder.order match
      case Some("alphabetical") => items.sortBy(_.value.toLowerCase)
      case Some("length")       => items.sortBy(_.value.length)
      case Some("reverse")      => items.reverse
      case Some("levenshtein") =>
        val ref = builder.reference.getOrElse("")
        items.sortBy(i => levenshtein(i.value, ref))
      case _ => items

    sortedItems.flatMap { item =>
      val attachedSublists = associationMap.collect {
        case (sublist, Some(`item`)) => sublist
      }.toList
      item :: attachedSublists
    }

  private def levenshtein(a: String, b: String): Int =
    val memo = Array.tabulate(a.length + 1, b.length + 1) { (i, j) =>
      if i == 0 then j
      else if j == 0 then i
      else 0
    }

    for i <- 1 to a.length; j <- 1 to b.length do
      val cost = if a(i - 1) == b(j - 1) then 0 else 1
      memo(i)(j) = List(
        memo(i - 1)(j) + 1,
        memo(i)(j - 1) + 1,
        memo(i - 1)(j - 1) + cost
      ).min

    memo(a.length)(b.length)

  private def findSublistParents(builder: ListBuilder): Map[ListBuilder, Option[ItemBuilder]] =
    val childMaps = builder.items.collect {
      case lb: ListBuilder => findSublistParents(lb)
    }.foldLeft(Map.empty[ListBuilder, Option[ItemBuilder]])(_ ++ _)

    val localPairs = builder.items.zipWithIndex.collect {
      case (sublist: ListBuilder, idx) =>
        val maybeItemAbove = if idx > 0 then builder.items(idx - 1) match
          case item: ItemBuilder => Some(item)
          case _                 => None
        else None
        sublist -> maybeItemAbove
    }.toMap

    childMaps ++ localPairs

  private def visitAllNodesBottomUp(builder: ListBuilder): ListBuilder =
    val updatedChildren = builder.items.map {
      case lb: ListBuilder => visitAllNodesBottomUp(lb)
      case other           => other
    }

    val associationMap = findSublistParents(builder.copyWith(items = updatedChildren))
    val updatedItems = sortItemAndSublists(builder, updatedChildren, associationMap)

    builder.copyWith(items = updatedItems)

  override def copyWith(
                         items: List[ListElementBuilder],
                         listType: ListType,
                         order: Option[SortingList],
                         reference: Option[String]
                       ): ListBuilder =
    this.copy(items, listType, order, reference)

  override def build: Listing =
    val finalBuilder = visitAllNodesBottomUp(this)
    Listing(finalBuilder.listType, finalBuilder.items.map(_.build)*)
