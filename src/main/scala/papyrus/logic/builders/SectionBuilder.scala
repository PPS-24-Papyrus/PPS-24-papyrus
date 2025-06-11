package papyrus.logic.builders

import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.Title
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.layerElement.section.Section
import scala.collection.mutable.ListBuffer

case class SectionBuilder(
                              private var title: Option[Title] = None,
                              private val layerElements: ListBuffer[LayerElement] = ListBuffer.empty
                            ):

  def setTitle(newTitle: Title): Unit =
    title = Some(newTitle)

  def addLayerElement(element: LayerElement): Unit =
    layerElements += element

  def build: Section = Section(title, layerElements.toSeq*)

object SectionBuilder:
  def apply(): SectionBuilder = new SectionBuilder()
