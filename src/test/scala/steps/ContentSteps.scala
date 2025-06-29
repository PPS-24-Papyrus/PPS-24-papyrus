package steps

import io.cucumber.datatable.{DataTable, DataTableType}
import io.cucumber.scala.{EN, ScalaDsl}
import org.scalatest.matchers.should.Matchers
import papyrus.DSL.builders.ImageBuilder.{alternative, caption}
import papyrus.DSL.builders.{CellBuilder, ContentBuilder, ImageBuilder, ItemBuilder, ListBuilder, ListBuilderImpl, ListBuilderProxy, PapyrusBuilder, RowBuilder, SectionBuilder, SubSectionBuilder, TableBuilder, TextBuilder, TitleBuilder}
import papyrus.logic.content.Content
import papyrus.logic.layerElement.text.Title
import papyrus.DSL.DSL.given_Conversion_String_ImageBuilder
import papyrus.logic.utility.TypesInline.{Level, Width}
import io.cucumber.scala.EN
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
      (src caption caption).build
    )

  // ----- LIST

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

  // ----


  Given("""I add a table with rows:"""): (rows: DataTable) =>
    import papyrus.DSL.builders._
    import scala.jdk.CollectionConverters._
    val tableBuilder = TableBuilder[String]()
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
