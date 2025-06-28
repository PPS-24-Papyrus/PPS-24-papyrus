package logic.layerElement

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import papyrus.utility.DefaultValues
import io.github.iltotore.iron.autoRefine
import papyrus.logic.Renderer.*
import papyrus.dsl.builders.textBuilder.{TitleBuilder, TextBuilder}

class TitleTest extends AnyFunSuite with Matchers:

  test("Title level 1 should render correct HTML and style"):
    val builder = TitleBuilder() title "Main Title" level 1
    val title = builder.build

    title.render.string shouldEqual "<h1>Main Title</h1>\n"
    title.renderStyle.string shouldEqual
      s"h1 {\n  font-family: ${DefaultValues.fontTitleH1}; font-size: ${DefaultValues.fontSizeTitleH1}px; color: ${DefaultValues.textColorTitleH1}; text-align: ${DefaultValues.textAlignTitleH1};\n}"

  test("Title level 2 should render correct HTML and style"):
    val builder = TitleBuilder() title "Section Title" level 2
    val title = builder.build

    title.render.string shouldEqual "<h2>Section Title</h2>\n"
    title.renderStyle.string shouldEqual
      s"h2 {\n  font-family: ${DefaultValues.fontTitleH2}; font-size: ${DefaultValues.fontSizeTitleH2}px; color: ${DefaultValues.textColorTitleH2}; text-align: ${DefaultValues.textAlignTitleH2};\n}"

  test("Title level 3 should render correct HTML and style"):
    val builder = TitleBuilder() title "Subsection" level 3
    val title = builder.build

    title.render.string shouldEqual "<h3>Subsection</h3>\n"
    title.renderStyle.string shouldEqual
      s"h3 {\n  font-family: ${DefaultValues.fontTitleH3}; font-size: ${DefaultValues.fontSizeTitleH3}px; color: ${DefaultValues.textColorTitleH3}; text-align: ${DefaultValues.textAlignTitleH3};\n}"

  test("Default TitleBuilder produces H1 with default text and style"):
    val title = TitleBuilder().build

    title.render.string shouldEqual s"<h${DefaultValues.levelH1}>${DefaultValues.titleTextH1}</h${DefaultValues.levelH1}>\n"
    title.renderStyle.string shouldEqual
      s"h${DefaultValues.levelH1} {\n  font-family: ${DefaultValues.fontTitleH1}; font-size: ${DefaultValues.fontSizeTitleH1}px; color: ${DefaultValues.textColorTitleH1}; text-align: ${DefaultValues.textAlignTitleH1};\n}"

