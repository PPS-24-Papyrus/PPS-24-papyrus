package papyrus.dsl.builders

import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.Title
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.layerElement.section.Section
import scala.collection.mutable.ListBuffer

/** Builds a Section element (only allowed inside Papyrus or Content) */
case class SectionBuilder(
                           private var title: Option[Title] = None,
                           private val layerElements: ListBuffer[LayerElement] = ListBuffer.empty
                         ) extends LayerElementBuilder:

  /** Sets section title */
  def setTitle(newTitle: Title): SectionBuilder =
    title = Some(newTitle)
    this

  /** Adds a section element */
  def addLayerElement(element: LayerElement): SectionBuilder =
    layerElements += element
    this

  /** Builds the Section element */
  override def build: Section = Section(title, layerElements.toSeq*)

object SectionBuilder:
  def apply(): SectionBuilder = new SectionBuilder()
