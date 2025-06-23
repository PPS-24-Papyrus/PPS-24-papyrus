package papyrus.logic.layerElement.section

import papyrus.logic.Renderer.Text.*
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.Title

/** Represents a section element with an optional title and nested content */
trait Section extends LayerElement:
  def title: Option[Title]
  def layerElement: Seq[LayerElement]

object Section:

  /** Creates a Section with an optional title and one or more LayerElements */
  def apply(title: Option[Title], layerElement: LayerElement*): Section =
    SectionImpl(title, layerElement.toSeq)

  private class SectionImpl(
                             override val title: Option[Title],
                             override val layerElement: Seq[LayerElement]
                           ) extends Section:

    /** Renders section content as HTML */
    override def render: MainText =
      val titleRendered = title.map(_.render).getOrElse("")
      val layerElementsRendered = layerElement.map(_.render).mkString("\n")
      s"<section>\n  $titleRendered  $layerElementsRendered\n</section>".toMainText

    /** Renders styles associated with section and nested elements */
    override def renderStyle: StyleText =
      val titleRendered = title.map(_.renderStyle).getOrElse("")
      (titleRendered +: layerElement.map(_.renderStyle)).mkString("\n").toStyleText
