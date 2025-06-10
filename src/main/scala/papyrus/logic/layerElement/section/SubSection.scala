package papyrus.logic.layerElement.section

import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.Title

import java.util.Optional

trait SubSection extends LayerElement:
  def title: Optional[Title]
  def layerElement: Seq[LayerElement]


object SubSection:

  def apply(title: Optional[Title], layerElement: LayerElement*): SubSection = SubSectionImpl(title, layerElement)

  private class SubSectionImpl(override val title: Optional[Title], override val layerElement: Seq[LayerElement]) extends SubSection:
    override def render: String =
      val titleRendered = if title.isPresent then title.get().render else ""
      val layerElementsRendered = layerElement.map(_.render).mkString("\n")
      s"<section>$titleRendered$layerElementsRendered</section>"
    override def renderStyle: String =
      val titleRendered = if title.isPresent then title.get().renderStyle else ""
      (titleRendered +: layerElement.map(_.renderStyle)).mkString("\n")