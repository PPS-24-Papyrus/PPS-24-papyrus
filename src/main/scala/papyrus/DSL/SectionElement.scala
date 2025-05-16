package papyrus.DSL

import PapyrusElement.ContentElement

object SectionElement:

  private case class Section(
    title: String,
    content: ContentElement*
  ) extends ContentElement:
    def render: String = s"<section><h2>${title}</h2>${content.map(_.render).mkString("\n")}</section>"

    case class SubSection(
      title: String,
      content: ContentElement*
    ) extends ContentElement:
      def render: String = s"<section><h3>${title}</h3>${content.map(_.render).mkString("\n")}</section>"

  def section(title: String) (content: ContentElement*): ContentElement =
    Section(title, content: _*)
