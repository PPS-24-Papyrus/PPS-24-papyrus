package papyrus.logic.content

import papyrus.logic.Renderer.Renderer
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.Title


trait Content extends Renderer:
  def title: Title
  def layerElement: Seq[LayerElement]


object Content:

  def apply(title: Title, layerElement: LayerElement*): Content = new ContentImpl(title, layerElement)

  private class ContentImpl(override val title: Title, override val layerElement: Seq[LayerElement]) extends Content:
    override def render: String = s"<body>${title.render}${layerElement.map(_.render).mkString("\n")}</body>"
    override def renderStyle: String = (title.renderStyle +: layerElement.map(_.renderStyle)).mkString("\n")