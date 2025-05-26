package papyrus.logic.metadata

import papyrus.logic.Renderer.Renderer
import papyrus.logic.Style
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine


trait Metadata extends Renderer:
  def nameFile(name: String): String
  def title(title: String): String
  def author(author: String): String
  def charset(charset: Charset): String
  def styleSheet(link: StyleSheet): String
  def language(language: Language): String

object Metadata:

  def apply(
             name: String = "untitled",
             title: String = "New Papyrus",
             author: String = "Unknown",
             charset: Charset = "utf-8",
             styleSheet: StyleSheet = "style.css",
             language: Language = "en",
             font: FontFamily = "Georgia",
             fontSize: FontSize = 16,
             lineHeight: LineHeight = 1.6,
             textColor: ColorString = "#000000",
             backgroundColor: ColorString = "#ffffff",
             margin: Margin = 40
           ): Metadata =
    MetadataImpl(name, title, author, charset, styleSheet, language, font, fontSize, lineHeight, textColor, backgroundColor, margin)

  private class MetadataImpl(
                              name: String,
                              titleValue: String,
                              authorValue: String,
                              charsetValue: Charset,
                              stylesheetValue: StyleSheet,
                              languageValue: Language,
                              font: FontFamily,
                              fontSize: FontSize,
                              lineHeight: LineHeight,
                              textColor: ColorString,
                              backgroundColor: ColorString,
                              margin: Margin
                            ) extends Metadata:

    private def meta(tag: String)(value: String): String =
      s"""<meta name="$tag" content="$value">"""

    override def nameFile(name: String): String = meta("filename")(name)
    override def title(title: String): String = meta("title")(title)
    override def author(author: String): String = meta("author")(author)
    override def charset(charset: Charset): String = s"""<meta charset="$charset">"""
    override def styleSheet(link: StyleSheet): String = s"""<link rel="stylesheet" href="$link">"""
    override def language(language: Language): String = s"""lang="$language""""

    override def render: String =
      s"""<head>
         |  ${charset(charsetValue)}
         |  ${nameFile(name)}
         |  ${title(titleValue)}
         |  ${author(authorValue)}
         |  ${styleSheet(stylesheetValue)}
         |</head>""".stripMargin

    override def renderStyle: String =
      val bodyCss = Seq(
        Style.font(font),
        Style.fontSize(fontSize),
        Style.lineHeight(lineHeight),
        Style.textColor(textColor),
        Style.backgroundColor(backgroundColor),
        Style.margin(margin)
      ).mkString(" ")

      s"body {\n  $bodyCss\n}"
