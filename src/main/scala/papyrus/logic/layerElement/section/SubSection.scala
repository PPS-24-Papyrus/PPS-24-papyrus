package papyrus.logic.layerElement.section

import papyrus.logic.Renderer.Text.*
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.Title

trait SubSection extends LayerElement:
  def title: Option[Title]
  def layerElement: Seq[LayerElement]

object SubSection:

  def apply(title: Option[Title], layerElement: LayerElement*): SubSection =
    SubSectionImpl(title, layerElement.toSeq)

  private class SubSectionImpl(
                                override val title: Option[Title],
                                override val layerElement: Seq[LayerElement]
                              ) extends SubSection:

    override def render: MainText =
      val titleRendered = title.map(_.render).getOrElse("")
      val layerElementsRendered = layerElement.map(_.render).mkString("\n")
      s"<section>\n  $titleRendered  $layerElementsRendered\n</section>".toMainText

    override def renderStyle: StyleText =
      val titleRendered = title.map(_.renderStyle).getOrElse("")
      (titleRendered +: layerElement.map(_.renderStyle)).mkString("\n").toStyleText
