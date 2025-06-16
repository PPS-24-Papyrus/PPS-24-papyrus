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
    table.render.replaceAll("""cls-.{3}""", "") shouldEqual "<div class=\"\">\n<table>\n<caption>Table Caption</caption><tbody>\n<tr>\n<th colspan='1' rowspan='1'>Cell 1</th>\n<td colspan='1' rowspan='1'>Cell 2</td>\n</tr>\n</tbody>\n</table>\n</div>"

  test("Table should render correctly without caption"):
    val cell1 = Cell("Cell 1")
    val cell2 = Cell("Cell 2")
    val row = Row(List(cell1, cell2))
    val table = Table(None, List(row), TableStyle())
    table.render.replaceAll("""cls-.{3}""", "") shouldEqual "<div class=\"\">\n<table>\n<tbody>\n<tr>\n<td colspan='1' rowspan='1'>Cell 1</td>\n<td colspan='1' rowspan='1'>Cell 2</td>\n</tr>\n</tbody>\n</table>\n</div>"

  test("Row should render correctly with multiple cells"):
    val cell1 = Cell("Cell 1")
    val cell2 = Cell("Cell 2")
    val row = Row(List(cell1, cell2))
    row.render shouldEqual "<tr>\n<td colspan='1' rowspan='1'>Cell 1</td>\n<td colspan='1' rowspan='1'>Cell 2</td>\n</tr>\n"

  test("Cell should render correctly with default attributes"):
    val cell = Cell("Default Cell")
    cell.render shouldEqual "<td colspan='1' rowspan='1'>Default Cell</td>\n"

  test("Cell should render correctly with custom attributes"):
    val cell = Cell("Custom Cell", head = true, colspan = 2, rowspan = 3)
    cell.render shouldEqual "<th colspan='2' rowspan='3'>Custom Cell</th>\n"