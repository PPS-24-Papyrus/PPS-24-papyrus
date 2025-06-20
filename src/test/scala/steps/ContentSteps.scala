package steps

import io.cucumber.datatable.{DataTable, DataTableType}
import io.cucumber.scala.{EN, ScalaDsl}
import org.scalatest.matchers.should.Matchers
import papyrus.DSL.builders.ImageBuilder.{alternative, caption}
import papyrus.DSL.builders.{CellBuilder, ContentBuilder, ImageBuilder, ItemBuilder, ListBuilder, ListBuilderImpl, PapyrusBuilder, RowBuilder, SectionBuilder, SubSectionBuilder, TableBuilder, TextBuilder, TitleBuilder}
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

  /*Given("""I add a list with items:"""): (dataTable: DataTable) =>
    val items: List[String] = dataTable.asLists().asScala.map(_.get(0)).toList
    contentBuilder = Some(ContentBuilder())
    val listBuilder = items.foldLeft(ListBuilderImpl()) { (builder, item) =>
      builder.add(ItemBuilder(item).build)
    }
    contentBuilder.get.addLayerElement(listBuilder.build)*/


  Given("""I add a table with rows:"""): (rows: DataTable) =>
    import papyrus.DSL.builders._
    val tableBuilder = TableBuilder()
    import scala.jdk.CollectionConverters._
    val scalaRows = rows.asLists().asScala.toList.map(_.asScala.toList)
    scalaRows.foreach(row =>
      tableBuilder.addRow(
        row.foldLeft(RowBuilder()) { (rb, cell) =>
          rb.addCell(CellBuilder().withContent(cell))
        }
      )
    )
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
