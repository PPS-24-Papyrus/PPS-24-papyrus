package DSL

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers.contain
import org.scalatest.matchers.should.Matchers.{should, shouldBe}
import papyrus.logic.builders.TableBuilder
import papyrus.logic.layerElement.captionElement.Table
import papyrus.logic.builders.TextDSL
import papyrus.DSL.DSL
import papyrus.DSL.DSL.{caption, given_Conversion_String_TextDSL}
import papyrus.logic.builders.given_Conversion_String_TextDSL

class TableDSLTest extends AnyFunSuite:

  private def table(init: TableBuilder ?=> Unit): Table =
    given builder: TableBuilder = TableBuilder()
    init
    builder.build()

  test("Table DSL should create a table with caption and rows"):
    val generatedTable = table:
      "prova2" | "prova3" | "prova4" | "prova5"
      "prova4" | "prova5" | "prova6" | "prova7"
      "prova6" | "prova7" | "prova8" | "prova9"
      caption:
        "Table Caption"

    generatedTable.rows.size shouldBe 3
    generatedTable.rows.forall(_.cells.size == 4) shouldBe true
    generatedTable.caption shouldBe Some("Table Caption")

  test("Table DSL should create a table with header cells"):
    val generatedTable = table:
      "prova2" hsh "prova3" hsh "prova4" hsh "prova5"
      "prova4" hs "prova5" s "prova6" s "prova7"
      "prova6" hs "prova7" s "prova8" s "prova9"

    generatedTable.rows.size shouldBe 3
    generatedTable.rows.head.cells.map(_.content) should contain inOrder("prova2", "prova3", "prova4", "prova5")


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