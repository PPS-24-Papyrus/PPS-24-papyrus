package papyrus.logic.content

import papyrus.logic.Renderer.Renderer
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.Title

trait Content extends Renderer:
  def title: Option[Title]
  def layerElement: Seq[LayerElement]

object Content:

  def apply(title: Option[Title], layerElement: LayerElement*): Content =
    new ContentImpl(title, layerElement.toSeq)

  private class ContentImpl(
                             override val title: Option[Title],
                             override val layerElement: Seq[LayerElement]
                           ) extends Content:

    override def render: String =
      val titleRendered = title.map(_.render).getOrElse("")
      val layerElementsRendered = layerElement.map(_.render).mkString("\n")
      s"<body>$titleRendered$layerElementsRendered</body>"

    override def renderStyle: String =
      val titleRendered = title.map(_.renderStyle).getOrElse("")
      (titleRendered +: layerElement.map(_.renderStyle)).mkString("\n")
