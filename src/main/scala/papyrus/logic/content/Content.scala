package papyrus.logic.content

import papyrus.logic.Renderer.Renderer
import papyrus.logic.Renderer.Text.*
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.Title

/** Represents the body content of a document, including optional title and layout elements */
trait Content extends Renderer:

  /** Optional document title (e.g. rendered as <h1>) */
  def title: Option[Title]

  /** Sequence of elements within the body (e.g. tables, images, sections) */
  def layerElement: Seq[LayerElement]

object Content:

  /** Creates a Content instance from an optional title and one or more layer elements */
  def apply(title: Option[Title], layerElement: LayerElement*): Content =
    new ContentImpl(title, layerElement.toSeq)

  private class ContentImpl(
                             override val title: Option[Title],
                             override val layerElement: Seq[LayerElement]
                           ) extends Content:

    override def render: MainText =
      val titleRendered = title.map(_.render).getOrElse("")
      val layerElementsRendered = layerElement.map(_.render).mkString("\n")
      s"<body>\n$titleRendered$layerElementsRendered</body>".toMainText

    override def renderStyle: StyleText =
      val titleRendered = title.map(_.renderStyle).getOrElse("")
      (titleRendered +: layerElement.map(_.renderStyle)).mkString("\n").toStyleText
