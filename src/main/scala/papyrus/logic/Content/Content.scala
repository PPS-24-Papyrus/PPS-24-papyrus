package papyrus.logic.Content

import papyrus.logic.Renderer.Renderer
import papyrus.logic.layerElement.LayerElement


trait Content extends Renderer:
  def title: String
  def layerElement: Seq[LayerElement]


object Content:

  def apply(title: String, layerElement: LayerElement*): Content = new ContentImpl(title, layerElement)

  private class ContentImpl(override val title: String, override val layerElement: Seq[LayerElement]) extends Content:
    override def render: String = s"<body><h1>${title}</h1>${layerElement.map(_.render).mkString("\n")}</body>"
    override def renderStyle: String = layerElement.map(_.renderStyle).mkString("\n")