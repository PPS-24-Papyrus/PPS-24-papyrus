package logic.layerElement


import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import papyrus.logic.layerElement.captionElement.{Cell, Row, Table}
import papyrus.logic.styleObjects.TableStyle
import papyrus.logic.Renderer.*
import papyrus.logic.Renderer.Text.MainText

class TableTest extends AnyFunSuite with Matchers:

  test("Table should render correctly with caption and rows"):
    val cell1 = Cell("Cell 1", head = true)
    val cell2 = Cell("Cell 2")
    val row = Row(List(cell1, cell2))
    val table = Table(Some("Table Caption"), List(row), TableStyle(), MainText(_))
    table.render.string.replaceAll("""cls-.{3}""", "") shouldEqual "<div class=\"\">\n<table>\n<caption>Table Caption</caption>\n<tbody>\n<tr>\n<th colspan='1' rowspan='1'>Cell 1</th>\n<td colspan='1' rowspan='1'>Cell 2</td>\n</tr>\n</tbody>\n</table>\n</div>"

  test("Table should render correctly without caption"):
    val cell1 = Cell("Cell 1")
    val cell2 = Cell("Cell 2")
    val row = Row(List(cell1, cell2))
    val table = Table(None, List(row), TableStyle(), MainText(_))
    table.render.string.replaceAll("""cls-.{3}""", "") shouldEqual "<div class=\"\">\n<table>\n\n<tbody>\n<tr>\n<td colspan='1' rowspan='1'>Cell 1</td>\n<td colspan='1' rowspan='1'>Cell 2</td>\n</tr>\n</tbody>\n</table>\n</div>"

  test("Row should render correctly with multiple cells"):
    val cell1 = Cell("Cell 1")
    val cell2 = Cell("Cell 2")
    val row = Row(List(cell1, cell2))
    val render = row.render(MainText(_))
    render.string shouldEqual "<tr>\n<td colspan='1' rowspan='1'>Cell 1</td>\n<td colspan='1' rowspan='1'>Cell 2</td>\n</tr>\n"

  test("Cell should render correctly with default attributes"):
    val cell = Cell("Default Cell")
    val render = cell.render(MainText(_))
    render.string shouldEqual "<td colspan='1' rowspan='1'>Default Cell</td>\n"

  test("Cell should render correctly with custom attributes"):
    val cell = Cell("Custom Cell", head = true, colspan = 2, rowspan = 3)
    val render = cell.render(MainText(_))
    render.string shouldEqual "<th colspan='2' rowspan='3'>Custom Cell</th>\n"