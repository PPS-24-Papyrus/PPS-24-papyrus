package papyrus.DSL.builders

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import papyrus.logic.layerElement.Listing
import papyrus.logic.layerElement.text.Item

class ListingTest extends AnyFunSuite with Matchers:

  test("ItemBuilder builds default item") {
    val itemBuilder = ItemBuilder()
    val item: Item = itemBuilder.build
    item.render shouldEqual "<li>Elemento lista</li>"
  }

  test("ItemBuilder builds item with custom value") {
    val itemBuilder = ItemBuilder("Test Item")
    val item: Item = itemBuilder.build
    item.render shouldEqual "<li>Test Item</li>"
  }

  test("ListBuilderImpl builds empty list") {
    val listBuilder = ListBuilderImpl()
    val listing: Listing = listBuilder.build
    listing.render shouldEqual "<ul>\n\n</ul>"
    listing.renderStyle shouldEqual ""
  }

  test("ListBuilderImpl builds list with one item") {
    val itemBuilder = ItemBuilder("Solo")
    val listBuilder = ListBuilderImpl().add(itemBuilder)
    val listing = listBuilder.build
    listing.render shouldEqual "<ul>\n<li>Solo</li>\n</ul>"
  }

  test("ListBuilderImpl builds list with multiple items") {
    val item1 = ItemBuilder("Primo")
    val item2 = ItemBuilder("Secondo")
    val listBuilder = ListBuilderImpl().add(item1).add(item2)
    val listing = listBuilder.build
    listing.render shouldEqual "<ul>\n<li>Primo</li>\n<li>Secondo</li>\n</ul>"
  }

  test("ListBuilderImpl supports nested lists") {
    val sublist = ListBuilderImpl().add(ItemBuilder("Sub 1")).add(ItemBuilder("Sub 2"))
    val listBuilder = ListBuilderImpl()
      .add(ItemBuilder("Item 1"))
      .add(sublist)
      .add(ItemBuilder("Item 2"))
    val listing = listBuilder.build

    // Expected: ul with Item 1, then nested ul with subitems, then Item 2
    val expected = "<ul>\n<li>Item 1</li>\n<ul>\n<li>Sub 1</li>\n<li>Sub 2</li>\n</ul>\n<li>Item 2</li>\n</ul>"
    listing.render shouldEqual expected
  }
