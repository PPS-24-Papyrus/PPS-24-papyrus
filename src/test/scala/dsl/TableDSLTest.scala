package dsl

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers.{be, contain, include}
import org.scalatest.matchers.should.Matchers.{an, should, shouldBe}
import papyrus.logic.layerElement.captionElement.Table
import papyrus.dsl.DSL
import papyrus.dsl.TableDSL.*
import papyrus.dsl.builders.tableBuider.RowBuilder.*
import papyrus.dsl.builders.tableBuider.{RowBuilder, TableBuilder, TableBuilderProxy}
import papyrus.dsl.DSL.given_Conversion_List_RowBuilder

class TableDSLTest extends AnyFunSuite:

  private def table[T](init: TableBuilder[T] ?=> Unit): Table[T] =
    var current: TableBuilder[T] = TableBuilder(rows = Vector.empty[RowBuilder[T]])
    given builder: TableBuilder[T] = TableBuilderProxy[T](() => current, updated => current = updated)
    init
    builder.build

  private case class TestValue(a: Int, b: String)

  test("Table DSL should create a table with caption and rows"):
    val generatedTable = table:
      "prova2" | "prova3" | "prova4" | "prova5"
      "prova4" | "prova5" | "prova6" | "prova7"
      "prova6" | "prova7" | "prova8" | "prova9"
      captionTable:
        "Table Caption"

    generatedTable.rows.size shouldBe 3
    generatedTable.rows.forall(_.cells.size == 4) shouldBe true
    generatedTable.caption shouldBe Some("Table Caption")

  test("Table DSL should create a table with custom type"):
    val generatedTable = table[TestValue]:
      TestValue(1, "A") | TestValue(2, "B") | TestValue(3, "C") | TestValue(4, "D")
      TestValue(5, "E") | TestValue(6, "F") | TestValue(7, "G") | TestValue(8, "H")
      renderTable:
        (t: TestValue) => s"${t.a} - ${t.b}"

    generatedTable.rows.size shouldBe 2
    generatedTable.rows.head.cells.map(_.content) should contain inOrder(TestValue(1, "A"), TestValue(2, "B"), TestValue(3, "C"), TestValue(4, "D"))
    generatedTable.render.string should include("1 - A")
    generatedTable.render.string should include("2 - B")
    generatedTable.render.string should include("3 - C")
    generatedTable.render.string should include("4 - D")


  test("Table DSL should create a table with header cells"):
    val generatedTable = table:
      "prova2" hsh "prova3" hsh "prova4" hsh "prova5"
      "prova4" hs "prova5" s "prova6" s "prova7"
      "prova6" hs "prova7" s "prova8" s "prova9"

    generatedTable.rows.size shouldBe 3
    generatedTable.rows.head.cells.map(_.content) should contain inOrder("prova2", "prova3", "prova4", "prova5")
    generatedTable.rows.head.cells.forall(_.head) shouldBe true


  test("Table DSL should create a table with colspan"):
    val generatedTable = table:
      "prova2" | "prova3" | "prova4" | "prova5"
      "prova4" -|- "prova5"
      "prova6" | "prova7" |- "prova8"

    generatedTable.rows.size shouldBe 3
    generatedTable.rows(1).cells.size shouldBe 2

  test("Table DSL should create a table with rowspan"):
    val generatedTable = table:
      "prova2" | "prova3" | "prova4" | "prova5"
      "prova4" ^| "prova5" | "prova6" |^ "prova7"
      "prova6" | "prova7"

    generatedTable.rows.size shouldBe 3
    generatedTable.rows(1).cells.count(_.rowspan > 1) shouldBe 2

  test("Table DSL should create an empty table when no rows are provided"):
    val generatedTable = table:
      captionTable:
        "Empty Table"

    generatedTable.rows.size shouldBe 0
    generatedTable.caption shouldBe Some("Empty Table")

  test("Table DSL should handle a single row with mixed headers and regular cells"):
    val generatedTable = table:
      "Header1" hs "Header2" s "Cell1" s "Cell2"

    generatedTable.rows.size shouldBe 1
    generatedTable.rows.head.cells.size shouldBe 4
    generatedTable.rows.head.cells.map(_.head) shouldBe Seq(true, false, false, false)

  test("Table DSL should handle a table with both colspan and rowspan"):
    val generatedTable = table:
      "Cell1" |- "Cell2" |- "Cell3"
      "Cell4" ^| "Cell5" | "Cell6"

    generatedTable.rows.size shouldBe 2
    generatedTable.rows(1).cells.count(_.rowspan > 1) shouldBe 1
    generatedTable.rows.head.cells.count(_.colspan > 1) shouldBe 2

  test("Table DSL should create a table with no caption"):
    val generatedTable = table:
      "Cell1" | "Cell2"
      "Cell3" | "Cell4"

    generatedTable.rows.size shouldBe 2
    generatedTable.caption shouldBe None

  test("Table DSL error format should throw an exception"):
    val generatedTable = table:
      "Cell1" | "Cell2"
      "Cell3" | "Cell4" | "Cell5"
      captionTable:
          "Invalid Table"
    val render = generatedTable.render.string
    render should include("Table structure error:</strong><br>Row 0 → 2 columns<br>Row 1 → 3 columns")

  test("Table DSL should create a table with List"):
    val generatedTable = table:
      withList:
        List(
          List(TestValue(1, "A"), TestValue(2, "B"), TestValue(3, "C"), TestValue(4, "D")),
          List(TestValue(5, "E"), TestValue(6, "F"), TestValue(7, "G"), TestValue(8, "H"))
        )
      renderTable:
        (t: TestValue) => s"${t.a} - ${t.b}"

    generatedTable.rows.flatMap(_.cells.map(_.content)) should contain inOrder(
      TestValue(1, "A"), TestValue(2, "B"), TestValue(3, "C"), TestValue(4, "D"),
      TestValue(5, "E"), TestValue(6, "F"), TestValue(7, "G"), TestValue(8, "H")
    )