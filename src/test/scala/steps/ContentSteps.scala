package steps

import io.cucumber.datatable.DataTableType
import io.cucumber.scala.{EN, ScalaDsl}
import org.scalatest.matchers.should.Matchers
import papyrus.DSL.builders.ImageBuilder.alternative
import papyrus.DSL.builders.{CellBuilder, ContentBuilder, ImageBuilder, ItemBuilder, ListBuilder, PapyrusBuilder, RowBuilder, SectionBuilder, SubSectionBuilder, TableBuilder, TextBuilder, TitleBuilder}
import papyrus.logic.content.Content
import papyrus.logic.layerElement.text.Title
import papyrus.DSL.DSL.given_Conversion_String_ImageBuilder
import papyrus.logic.utility.TypesInline.Width
import io.cucumber.scala.{EN}
import scala.jdk.CollectionConverters._

class ContentSteps extends ScalaDsl with EN with Matchers:

  private var contentBuilder: Option[ContentBuilder] = None
  private var papyrusBuilder: Option[PapyrusBuilder] = None
  private var renderedContent: Option[String] = None
  private var error: Option[Throwable] = None

  Given("""I create a Papyrus document"""):
    contentBuilder = Some(ContentBuilder())

  Given("""I add a title {string}"""): (title: String) =>
    contentBuilder.get.setTitle(TitleBuilder(title).build)

  Given("""I add a paragraph {string}"""): (text: String) =>
    contentBuilder.get.addLayerElement(TextBuilder(text).build)

  Given("""I add a section with title {string} and text {string}"""): (title: String, text: String) =>
    contentBuilder = Some(ContentBuilder())
    contentBuilder.get.addLayerElement(SectionBuilder().setTitle(TitleBuilder(title).build).addLayerElement(TextBuilder(text).build).build)

  Given("""I add a subsection with title {string} and text {string}"""): (title: String, text: String) =>
    contentBuilder = Some(ContentBuilder())
    contentBuilder.get.addLayerElement(SubSectionBuilder().setTitle(TitleBuilder(title).build).addLayerElement(TextBuilder(text).build).build)

  Given("""I add an image with:"""): (data: Map[String, String]) =>
    contentBuilder = Some(ContentBuilder())
    contentBuilder.get.addLayerElement(
      (data("path") alternative data("alt") width data("size").asInstanceOf[Width] caption data("caption")).build
    )

  Given("""I add a list with:"""): (items: List[String]) =>
    contentBuilder = Some(ContentBuilder())
    contentBuilder.get.addLayerElement(items.foldLeft(ListBuilder())((builder, item) => builder.addItem(ItemBuilder(item).build)).build)

  Given("""I add a table with caption {string}, headers {string}, and rows:"""): (caption: String, headers: String, rows: List[List[String]]) =>
    val tableBuilder = TableBuilder()
      .withCaption(caption)

    rows.foreach(row =>
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
      renderedContent = Some(contentBuilder.get.build.render)
    catch
      case e: Throwable => error = Some(e)

  Then("""The HTML output should contain:"""): (expectedOutput: String) =>
    renderedContent should not be empty
    renderedContent.get should include(expectedOutput.stripMargin)

  Then("""The system should raise an error.""") { () =>
    assert(error.isDefined)
  }