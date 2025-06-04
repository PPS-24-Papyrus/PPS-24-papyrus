package logic.layerElement


import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import papyrus.logic.layerElement.captionElement.{Cell, Row, Table}
import papyrus.logic.styleObjects.TableStyle

class TableTest extends AnyFunSuite with Matchers:

  test("Table should render correctly with caption and rows"):
    val cell1 = Cell("Cell 1", head = true)
    val cell2 = Cell("Cell 2")
    val row = Row(List(cell1, cell2))
    val table = Table(Some("Table Caption"), List(row), TableStyle())
    table.render shouldEqual "<table><caption>Table Caption</caption><tbody><tr><th colspan='1' rowspan='1'>Cell 1</th><td colspan='1' rowspan='1'>Cell 2</td></tr></tbody></table>"

  test("Table should render correctly without caption"):
    val cell1 = Cell("Cell 1")
    val cell2 = Cell("Cell 2")
    val row = Row(List(cell1, cell2))
    val table = Table(None, List(row), TableStyle())
    table.render shouldEqual "<table><tbody><tr><td colspan='1' rowspan='1'>Cell 1</td><td colspan='1' rowspan='1'>Cell 2</td></tr></tbody></table>"

  test("Row should render correctly with multiple cells"):
    val cell1 = Cell("Cell 1")
    val cell2 = Cell("Cell 2")
    val row = Row(List(cell1, cell2))
    row.render shouldEqual "<tr><td colspan='1' rowspan='1'>Cell 1</td><td colspan='1' rowspan='1'>Cell 2</td></tr>"

  test("Cell should render correctly with default attributes"):
    val cell = Cell("Default Cell")
    cell.render shouldEqual "<td colspan='1' rowspan='1'>Default Cell</td>"

  test("Cell should render correctly with custom attributes"):
    val cell = Cell("Custom Cell", head = true, colspan = 2, rowspan = 3)
    cell.render shouldEqual "<th colspan='2' rowspan='3'>Custom Cell</th>"