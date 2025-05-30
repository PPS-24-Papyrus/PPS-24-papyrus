package papyrus.logic.content

import papyrus.logic.Renderer.Renderer
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.Title

import java.util.Optional


trait Content extends Renderer:
  def title: Optional[Title]
  def layerElement: Seq[LayerElement]


object Content:

  def apply(title: Optional[Title], layerElement: LayerElement*): Content = new ContentImpl(title, layerElement)

  private class ContentImpl(override val title: Optional[Title], override val layerElement: Seq[LayerElement]) extends Content:
    override def render: String =
      val titleRendered = if title.isPresent then title.get().render else ""
      val layerElementsRendered = layerElement.map(_.render).mkString("\n")
      s"<body>$titleRendered$layerElementsRendered</body>"
    override def renderStyle: String =
      val titleRendered = if title.isPresent then title.get().renderStyle else ""
      (titleRendered +: layerElement.map(_.renderStyle)).mkString("\n")