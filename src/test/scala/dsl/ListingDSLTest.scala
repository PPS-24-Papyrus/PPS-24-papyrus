package dsl


import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import papyrus.dsl.builders.*
import papyrus.logic.layerElement.text.Item
import papyrus.logic.layerElement.Listing
import io.github.iltotore.iron.autoRefine


class ListingDSLTest extends AnyFunSuite with Matchers:

  test("ListBuilderImpl starts with empty items list"):
    val lb = ListBuilderImpl()
    lb.items shouldBe empty


  test("withListType sets list type"):
    val lb = ListBuilderImpl().withListType("ol")
    lb.listType shouldBe "ol"


  test("withSortingProperties sets order"):
    val lb = ListBuilderImpl().withSortingProperties("alphabetical")
    lb.order should contain ("alphabetical")


  test("withReference sets reference and order to levenshtein"):
    val lb = ListBuilderImpl().withReference("ref")
    lb.reference should contain ("ref")
    lb.order should contain ("levenshtein")

  test("add appends an item"):
    val lb = ListBuilderImpl()
    val newLb = lb.add(ItemBuilder("test"))
    newLb.items.size shouldBe 1
    newLb.items.head match
      case ib: ItemBuilder => ib.value shouldBe "test"
      case _ => fail("Expected ItemBuilder")



  test("copyWith creates a new instance with changed fields"):
    val lb = ListBuilderImpl()
    val copy = lb.copyWith(items = List(ItemBuilder("x")), listType = "ol", order = Some("length"), reference = Some("ref"))
    copy.items.size shouldBe 1
    copy.listType shouldBe "ol"
    copy.order should contain ("length")
    copy.reference should contain ("ref")

  test("withReference sets order to levenshtein and reference string"):
    val lb = ListBuilderImpl().withReference("cat")
    lb.order should contain("levenshtein")
    lb.reference should contain("cat")

  test("items are sorted by Levenshtein distance from reference string"):
    val items = List(ItemBuilder("bat"), ItemBuilder("cat"), ItemBuilder("rats"))
    val lb = ListBuilderImpl(items = items).withReference("cat")
    val sortedItems = lb.build.items.collect { case i: Item => i.item }
    sortedItems should contain inOrder("cat", "bat", "rats")


  test("build returns Listing with correct list type"):
    val lb = ListBuilderImpl().withListType("ol")
    val listing = lb.build
    listing.listType shouldBe "ol"

  test("build returns Listing with items converted from builders"):
    val item1 = ItemBuilder("a")
    val item2 = ItemBuilder("b")
    val lb = ListBuilderImpl().add(item1).add(item2)
    val listing = lb.build
    listing.items.size shouldBe 2
    listing.items.collect { case i: Item => i.item } should contain allOf ("a", "b")


  test("sort alphabetical sorts items alphabetically"):
    val items = List(ItemBuilder("c"), ItemBuilder("a"), ItemBuilder("b"))
    val lb = ListBuilderImpl(items = items, order = Some("alphabetical"))
    val sorted = lb.build.items.collect { case i: Item => i.item }
    sorted shouldBe Seq("a", "b", "c")

  test("sort length sorts items by length"):
    val items = List(ItemBuilder("aa"), ItemBuilder("a"), ItemBuilder("aaa"))
    val lb = ListBuilderImpl(items = items, order = Some("length"))
    val sorted = lb.build.items.collect { case i: Item => i.item }
    sorted shouldBe Seq("a", "aa", "aaa")

  test("sort reverse reverses the item order"):
    val items = List(ItemBuilder("a"), ItemBuilder("b"), ItemBuilder("c"))
    val lb = ListBuilderImpl(items = items, order = Some("reverse"))
    val sorted = lb.build.items.collect { case i: Item => i.item }
    sorted shouldBe Seq("c", "b", "a")

  test("sort levenshtein sorts items by distance from reference"):
    val items = List(ItemBuilder("cat"), ItemBuilder("bat"), ItemBuilder("rat"))
    val lb = ListBuilderImpl(items = items, order = Some("levenshtein"), reference = Some("cat"))
    val sorted = lb.build.items.collect { case i: Item => i.item }
    sorted.head shouldBe "cat"

  test("nested lists are correctly parent-associated after build"):
    val sublist = ListBuilderImpl().add(ItemBuilder("subitem"))
    val lb = ListBuilderImpl().add(ItemBuilder("item")).add(sublist)
    val built = lb.build
    built.items.size shouldBe 2
    built.items(1) match
      case l: Listing => l.items.head.asInstanceOf[Item].item shouldBe "subitem"
      case _ => fail("Expected nested Listing")



  test("ItemBuilder builds Item with correct value"):
    val itemBuilder = ItemBuilder("hello")
    val item = itemBuilder.build
    item.item shouldBe "hello"

