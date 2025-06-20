package logic.layerElement

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import papyrus.DSL.builders.{ItemBuilder, ListBuilder}

class ListingTest extends AnyFunSuite with Matchers:

  test("ItemBuilder should build a default item"):
    val itemBuilder = ItemBuilder()
    val item = itemBuilder.build
    item.render shouldEqual "<li>Elemento lista</li>"

  test("ListBuilder should build an empty list"):
    val listBuilder = ListBuilder()
    val listing = listBuilder.build
    listing.render shouldEqual "<ul>\n\n</ul>"
    listing.renderStyle shouldEqual ""

  test("ListBuilder should build a list with a single item"):
    val itemBuilder = ItemBuilder()
    val listBuilder = ListBuilder()
    listBuilder.addItem(itemBuilder.build)
    val listing = listBuilder.build

    val expected = s"""<ul>\n<li>Elemento lista</li>\n</ul>"""

    listing.render shouldEqual expected

  test("ListBuilder should build a list with multiple items"):
    val item1 = ItemBuilder().build
    val item2 = (ItemBuilder() value "Secondo elemento").build

    val listBuilder = ListBuilder()
    listBuilder.addItem(item1)
    listBuilder.addItem(item2)

    val listing = listBuilder.build

    val expected = s"""<ul>\n<li>Elemento lista</li>\n<li>Secondo elemento</li>\n</ul>"""

    listing.render shouldEqual expected

  test("Item renderStyle should be empty (no styling applied)"):
    val item = ItemBuilder().build
    item.renderStyle shouldEqual ""
