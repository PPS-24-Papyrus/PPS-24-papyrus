package papyrus.logic.builders

import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.Title
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.layerElement.section.Section
import java.util.Optional
import scala.collection.mutable.ArrayBuffer

class SectionBuilder:
  private var title: Optional[Title] = Optional.empty
  val layerElements = ArrayBuffer[LayerElement]()

  def setTitle(newTitle: Title): Unit =
    title = Optional.of(newTitle)

  def addLayerElement(element: LayerElement): Unit =
    layerElements += element

  def build(): Section = Section(title, layerElements.toSeq *)