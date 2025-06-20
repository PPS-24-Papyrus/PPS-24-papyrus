package papyrus.DSL.builders

import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.layerElement.{ListElement, Listing}
import papyrus.logic.layerElement.text.Item

import scala.collection.mutable.ListBuffer
import scala.math

trait ListBuilder extends LayerElementBuilder {
  def items: List[ListElement]

  def listType: ListType

  def order: Option[SortingList]

  def reference: Option[String]

  def reversed: Boolean

  def withListType(tpe: ListType): ListBuilder

  def ordered(orderType: SortingList): ListBuilder

  def reverseListing: ListBuilder

  def withReference(str: String): ListBuilder

  def add(element: ListElement): ListBuilder

  def build: Listing

  def copyWith(
                items: List[ListElement] = this.items,
                listType: ListType = this.listType,
                order: Option[SortingList] = this.order,
                reference: Option[String] = this.reference,
                reversed: Boolean = this.reversed
              ): ListBuilder
}


case class ListBuilderImpl(
                            items: List[ListElement] = Nil,
                            listType: ListType = "ul",
                            order: Option[SortingList] = None,
                            reference: Option[String] = None,
                            reversed: Boolean = false,
                            private val parentMap: scala.collection.mutable.Map[ListBuilder, Item] = scala.collection.mutable.Map.empty
                          ) extends ListBuilder:



  private def registerSubList(subList: ListBuilder): Unit =
    parentMap.update(subList, lastInsertedItem.getOrElse(Item("")))

  private def lastInsertedItem: Option[Item] =
    items.reverse.collectFirst { case i: Item => i }

  private def getParentItem(subList: ListBuilder): Option[Item] =
    parentMap.get(subList)

  def withListType(tpe: ListType): ListBuilder =
    copy(listType = tpe)

  def ordered(orderType: SortingList): ListBuilder =
    copy(order = Some(orderType))

  def reverseListing: ListBuilder =
    copy(reversed = true)

  def withReference(ref: String): ListBuilder =
    copy(reference = Some(ref), order = Some("levenshtein"))

  def add(element: ListElement): ListBuilder = element match
    case subList: ListBuilder =>
      registerSubList(subList)
      copy(items = items :+ subList)
    case item =>
      copy(items = items :+ item)

  private def sortItems(items: List[Item]): List[Item] =
    println(s"sortItems: start with ${items.size} items")
    val sorted = order match
      case Some("alphabetical") => items.sortBy(_.item.toLowerCase)
      case Some("length")       => items.sortBy(_.item.length)
      case Some("reverse")      => items.reverse
      case Some("levenshtein") =>
        val ref = reference.getOrElse("")
        items.sortBy(i => levenshtein(i.item, ref))
      case _ => items
    val finalSorted = if reversed then
      println("sortItems: reversed sorting applied")
      sorted.reverse
    else
      println("sortItems: sorted without reverse")
      sorted
    finalSorted

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

  private def printItemsBottomUpFromBuilder(builder: ListBuilder, depth: Int = 0): Unit =
    val indent = "  " * depth

    // Prima visito tutte le sottoliste contenute negli items
    builder.items.foreach {
      case subBuilder: ListBuilder =>
        printItemsBottomUpFromBuilder(subBuilder, depth + 1)
      case _ => // ignoro tutto il resto per ora
    }

    // Poi stampo gli Item di questo livello
    builder.items.foreach {
      case item: Item =>
        println(s"${indent}- Item: ${item.item}")
      case _ => // ignoro ListBuilder e altri elementi
    }

  private def sortItemsBottomUpFromBuilder(builder: ListBuilder, depth: Int = 0): ListBuilder =
    // 1. Ricorsione sulle sottoliste, passando giù gli stessi parametri
    val newItems = this.items.map {
      case subBuilder: ListBuilder =>
        val updatedSub = this.copy(
          order = this.order,
          reference = this.reference,
          reversed = this.reversed
        )
        sortItemsBottomUpFromBuilder(updatedSub, depth + 1)
      case other => other
    }

    // 2. Ordina gli Item usando sortItems
    val itemsOnly = newItems.collect { case i: Item => i }
    val sortedItems = this.sortItems(itemsOnly)

    // 3. Raccoglie le sottoliste
    val subLists = newItems.collect { case l: ListBuilder => l }

    // 4. Ricostruisce lista finale (item + sottoliste)
    val finalItems: List[ListElement] = sortedItems ++ subLists.map(_.asInstanceOf[ListElement])

    // 5. Restituisce builder aggiornato
    builder.copyWith(items = finalItems)


  override def build: Listing =
    println(">>> Inizio stampa bottom-up <<<")
    //printItemsBottomUpFromBuilder(sortItemsBottomUpFromBuilder(this))
    println(">>> Fine stampa bottom-up <<<")
    Listing(listType, items*)

  override def copyWith(
                         items: List[ListElement] = this.items,
                         listType: ListType = this.listType,
                         order: Option[SortingList] = this.order,
                         reference: Option[String] = this.reference,
                         reversed: Boolean = this.reversed
                       ): ListBuilder =
    this.copy(items, listType, order, reference, reversed, parentMap)
