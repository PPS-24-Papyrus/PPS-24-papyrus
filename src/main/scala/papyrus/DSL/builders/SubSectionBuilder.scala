package papyrus.DSL.builders

import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.Title
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.layerElement.section.SubSection
import scala.collection.mutable.ListBuffer

case class SubSectionBuilder(
                              private var title: Option[Title] = None,
                              private val layerElements: ListBuffer[LayerElement] = ListBuffer.empty
                            ) extends LayerElementBuilder:

  def setTitle(newTitle: Title): SubSectionBuilder =
    title = Some(newTitle)
    this

  def addLayerElement(element: LayerElement): SubSectionBuilder =
    layerElements += element
    this

  override def build: SubSection = SubSection(title, layerElements.toSeq*)

object SubSectionBuilder:
  def apply(): SubSectionBuilder = new SubSectionBuilder()
