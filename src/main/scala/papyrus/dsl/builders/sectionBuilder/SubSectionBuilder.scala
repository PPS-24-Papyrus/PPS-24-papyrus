package papyrus.dsl.builders.sectionBuilder

import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.Title
import papyrus.logic.layerElement.section.SubSection
import scala.collection.mutable.ListBuffer
import papyrus.dsl.builders.LayerElementBuilder

/** Builds a SubSection element (only allowed inside Section) */
case class SubSectionBuilder(
                              private var title: Option[Title] = None,
                              private val layerElements: ListBuffer[LayerElement] = ListBuffer.empty
                            ) extends LayerElementBuilder:

  /** Sets subsection title */
  def setTitle(newTitle: Title): SubSectionBuilder =
    title = Some(newTitle)
    this

  /** Adds a subsection element */
  def addLayerElement(element: LayerElement): SubSectionBuilder =
    layerElements += element
    this

  /** Builds the SubSection element */
  override def build: SubSection = SubSection(title, layerElements.toSeq*)

object SubSectionBuilder:
  def apply(): SubSectionBuilder = new SubSectionBuilder()