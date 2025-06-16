package logic.layerElement

import io.github.iltotore.iron.autoRefine
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import papyrus.DSL.DefaultValues
import papyrus.DSL.builders.{SectionBuilder, SubSectionBuilder, TextBuilder, TitleBuilder}
import papyrus.logic.utility.TypesInline.*

class SectionAndSubSectionTest extends AnyFunSuite with Matchers:

  test("Section should render HTML with title and one paragraph"):
    val titleBuilder = TitleBuilder() title "Section Title" level 2
    val title = titleBuilder.build

    val textBuilder = TextBuilder() value "This is a paragraph."
    val text = textBuilder.build

    val sectionBuilder = SectionBuilder()
    sectionBuilder.setTitle(title)
    sectionBuilder.addLayerElement(text)
    val section = sectionBuilder.build

    section.render should include("<h2>Section Title</h2>")
    section.render should include("This is a paragraph.")
    section.render should startWith("<section>")
    section.render should endWith("</section>")

  test("Section should render correct CSS styles for title and text"):
    val titleBuilder = TitleBuilder() title "Styled Section" level 2
    val title = titleBuilder.build

    val textBuilder = TextBuilder() value "Styled paragraph."
    val text = textBuilder.build
    val className = text.render.split("class=\"")(1).takeWhile(_ != '"')

    val sectionBuilder = SectionBuilder()
    sectionBuilder.setTitle(title)
    sectionBuilder.addLayerElement(text)
    val section = sectionBuilder.build

    section.renderStyle should include("h2 {")
    section.renderStyle should include(s".$className {")
    section.renderStyle should include("font-family:")

  test("SubSection should render with H3 title and paragraph"):
    val titleBuilder = TitleBuilder() title "Subsection Title" level 3
    val title = titleBuilder.build

    val textBuilder = TextBuilder() value "Sub content"
    val text = textBuilder.build

    val subSectionBuilder = SubSectionBuilder()
    subSectionBuilder.setTitle(title)
    subSectionBuilder.addLayerElement(text)
    val subSection = subSectionBuilder.build

    subSection.render should include("<h3>Subsection Title</h3>")
    subSection.render should include("Sub content")
    subSection.render should startWith("<section>")

  test("SubSection should render only text if title is not set"):
    val textBuilder = TextBuilder() value "Lonely text"
    val text = textBuilder.build

    val subSectionBuilder = SubSectionBuilder()
    subSectionBuilder.addLayerElement(text)
    val subSection = subSectionBuilder.build
    
    subSection.render should include("Lonely text")
    subSection.render should not include "<h3>"

  test("Empty Section should render empty section element"):
    val sectionBuilder = SectionBuilder()
    val section = sectionBuilder.build

    section.render shouldEqual "<section>\n    \n</section>"
    section.renderStyle.trim shouldEqual ""
