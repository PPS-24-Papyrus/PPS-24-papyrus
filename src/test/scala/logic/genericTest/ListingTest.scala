package logic.genericTest

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import papyrus.logic.builders.{ItemBuilder, ListBuilder}
import papyrus.logic.layerElement.text.Item
import papyrus.logic.content.Listing
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine

class ListingTest extends AnyFunSuite with Matchers:

  test("ItemBuilder should build a default item"):
    val itemBuilder = ItemBuilder()
    val item = itemBuilder.build()
    item.render shouldEqual "<li>Elemento lista</li>"

  test("ListBuilder should build an empty list"):
    val listBuilder = ListBuilder()
    val listing = listBuilder.build()
    listing.render shouldEqual "<ul>\n\n</ul>"
    listing.renderStyle shouldEqual ""

  test("ListBuilder should build a list with a single item"):
    val itemBuilder = ItemBuilder()
    val listBuilder = ListBuilder()

    listBuilder.addItem(Item("Primo"))
    listBuilder.addItem(Item("Secondo"))
    val listing = listBuilder.build()

    val expected = """<ul>
    <li>Primo</li>
    <li>Secondo</li>
</ul>"""

    listing.render shouldEqual expected

  test("ListBuilder should build a list with multiple items"):
    val item1 = ItemBuilder().build()
    val item2 =
      val builder = ItemBuilder()
      builder.value = "Secondo elemento"
      builder.build()

    val listBuilder = ListBuilder()
    listBuilder.addItem(item1)
    listBuilder.addItem(item2)

    val listing = listBuilder.build()

    val expected = """<ul>
<li>Elemento lista</li>
<li>Secondo elemento</li>
</ul>"""

    listing.render shouldEqual expected

  test("Item renderStyle should be empty (no styling applied)"):
    val item = ItemBuilder().build()
    item.renderStyle shouldEqual ""
