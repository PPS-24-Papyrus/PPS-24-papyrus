package papyrus.DSL.builders

import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.layerElement.{ListElement, Listing}
import papyrus.logic.layerElement.text.Item

import scala.collection.mutable.ListBuffer
import scala.math

case class ListBuilder(
                        items: List[ListElement] = Nil,
                        listType: ListType = "ul",
                        order: Option[SortingList] = None,
                        reference: Option[String] = None,
                        reversed: Boolean = false
                      ) extends LayerElementBuilder:

  def withListType(tpe: ListType): ListBuilder =
    copy(listType = tpe)

  def ordered(orderType: SortingList): ListBuilder =
    copy(order = Some(orderType))

  def reverseListing: ListBuilder =
    copy(reversed = true)

  def withReference(ref: String): ListBuilder =
    copy(reference = Some(ref), order = Some("levenshtein"))

  def add(item: ListElement): ListBuilder =
    copy(items = items :+ item)

  private def sortItems(items: List[Item]): List[Item] =
    val sorted: List[Item] = order match
      case Some("alphabetical") => items.sortBy(_.item.toLowerCase)
      case Some("length")       => items.sortBy(_.item.length)
      case Some("reverse")      => items.reverse
      case Some("levenshtein") =>
        val ref = reference.getOrElse("")
        items.sortBy(i => levenshtein(i.item, ref))
      case _ => items

      if reversed then sorted.reverse else sorted


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


  override def build: Listing =
    Listing(listType, items*)

object ListBuilder:
  def apply(): ListBuilder = new ListBuilder()



