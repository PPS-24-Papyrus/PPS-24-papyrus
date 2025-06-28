package steps

import io.cucumber.datatable.{DataTable, DataTableType}
import io.cucumber.scala.{EN, ScalaDsl}
import org.scalatest.matchers.should.Matchers
import papyrus.dsl.builders.ImageBuilder.{alternative, captionImage}
import papyrus.dsl.builders.tableBuider.{CellBuilder, RowBuilder, TableBuilder, TableBuilderProxy}
import papyrus.dsl.builders.sectionBuilder.{SectionBuilder, SubSectionBuilder}
import papyrus.dsl.DSL.given_Conversion_String_ImageBuilder
import papyrus.utility.TypesInline.{Level, Width}
import papyrus.dsl.builders.listBuilder.{ItemBuilder, ListBuilderImpl}
import papyrus.dsl.builders.textBuilder.{TextBuilder, TitleBuilder}
import papyrus.dsl.builders.{ContentBuilder, PapyrusBuilder}
import papyrus.logic.Renderer.*

import scala.jdk.CollectionConverters.*

class ContentSteps extends ScalaDsl with EN with Matchers:

  private var contentBuilder: Option[ContentBuilder] = None
  private var papyrusBuilder: Option[PapyrusBuilder] = None
  private var renderedContent: Option[String] = None
  private var sectionBuilder: Option[SectionBuilder] = None
  private var error: Option[Throwable] = None

  Given("""I create a Papyrus document"""):
    contentBuilder = Some(ContentBuilder())

  Given("""I add a title {string}"""): (title: String) =>
    contentBuilder.get.setTitle(TitleBuilder(title).build)

  Given("""I add a paragraph {string}"""): (text: String) =>
    contentBuilder.get.addLayerElement(TextBuilder(text).build)

  Given("""I add a section with title {string} and text {string}"""): (title: String, text: String) =>
    contentBuilder = Some(ContentBuilder())
    sectionBuilder = Some(SectionBuilder().setTitle(TitleBuilder(title).build).addLayerElement(TextBuilder(text).build))
    contentBuilder.get.addLayerElement(sectionBuilder.get.build)

  Given("""I add a subsection with title {string} and text {string}"""): (title: String, text: String) =>
    contentBuilder = Some(ContentBuilder())
    sectionBuilder = Some(sectionBuilder.get.addLayerElement(SubSectionBuilder().setTitle(TitleBuilder(title, 2.asInstanceOf[Level]).build).addLayerElement(TextBuilder(text).build).build))
    contentBuilder.get.addLayerElement(sectionBuilder.get.build)

  Given("""I add an image with source {string} and caption {string}"""): (src: String, caption: String) =>
    contentBuilder = Some(ContentBuilder())
    contentBuilder.get.addLayerElement(
      (src captionImage caption).build
    )

  Given("""I create an empty list"""): () =>
    val listBuilder = ListBuilderImpl()
    contentBuilder = Some(ContentBuilder())
    contentBuilder.get.addLayerElement(listBuilder.build)

  Given("""I add a list with items:"""): (dataTable: DataTable) =>
    val items: List[String] = dataTable.asLists().asScala.map(_.get(0)).toList
    val listBuilder = items.foldLeft(ListBuilderImpl()) { (lb, item) =>
      lb.add(ItemBuilder(item)).asInstanceOf[ListBuilderImpl]
    }
    contentBuilder = Some(ContentBuilder())
    contentBuilder.get.addLayerElement(listBuilder.build)

  Given("""I add a nested list with parent item {string} and child items:"""): (parentItem: String, dataTable: DataTable) =>
    val childItems = dataTable.asLists().asScala.map(_.get(0)).toList
    val childList = childItems.foldLeft(ListBuilderImpl()) { (lb, item) =>
      lb.add(ItemBuilder(item)).asInstanceOf[ListBuilderImpl]
    }
    val parentList = ListBuilderImpl().add(ItemBuilder(parentItem)).add(childList)
    contentBuilder = Some(ContentBuilder())
    contentBuilder.get.addLayerElement(parentList.build)

  Then("""The rendered output should contain list item {string}"""): (item: String) =>
    renderedContent should not be empty
    renderedContent.get should include(s"<li>$item</li>")

  Then("""The rendered output should contain nested list structure"""): () =>
    renderedContent should not be empty
    renderedContent.get should include("<ul>")
    renderedContent.get should include("<li>")

  Then("""The rendered list should be ordered with type {string}"""): (listType: String) =>
    renderedContent should not be empty
    renderedContent.get should include(s"<$listType>")

  Given("""I add a table with rows:"""): (rows: DataTable) =>
    import papyrus.dsl.builders._
    import scala.jdk.CollectionConverters._
    var current: TableBuilder[String] = TableBuilder(rows = Vector.empty[RowBuilder[String]])
    val tableBuilder = TableBuilderProxy[String](() => current, updated => current = updated)
    val scalaRows: List[List[String]] =
      rows.asLists().asScala.toList.map(_.asScala.toList)

    scalaRows.foreach { row =>
      val rowBuilder = row.foldLeft(RowBuilder[String]()) { (rb, cell) =>
        rb.addCell(CellBuilder[String]().withContent(cell))
      }
      tableBuilder.addRow(rowBuilder)
    }
    contentBuilder = Some(ContentBuilder())
    contentBuilder.get.addLayerElement(tableBuilder.build)


  When("""I render the document"""):
    try
      renderedContent = Some(contentBuilder.get.build.render.string)
    catch
      case e: Throwable => error = Some(e)

  Then("""The HTML output should contain:"""): (expectedOutput: String) =>
    renderedContent should not be empty
    renderedContent.get should include(expectedOutput.stripMargin)
