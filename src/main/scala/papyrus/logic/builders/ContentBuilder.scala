package papyrus.logic.builders

import papyrus.logic.content.Content
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.Title

import java.util.Optional
import scala.collection.mutable.ArrayBuffer

class ContentBuilder:
  private var title: Optional[Title] = Optional.empty
  val layerElements = ArrayBuffer[LayerElement]()
  private var titleSet: Boolean = false

  def setTitle(newTitle: Title): Unit =
    if titleSet then
      throw new IllegalStateException("Title has already been set for this ContentBuilder.")
    title = Optional.of(newTitle)
    titleSet = true

  def addLayerElement(element: LayerElement): Unit =
    layerElements += element

  def build(): Content = Content(title, layerElements.toSeq *)