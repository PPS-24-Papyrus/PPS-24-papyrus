package papyrus.logic.builders

import papyrus.logic.content.Content
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.Title
import scala.collection.mutable.ListBuffer

case class ContentBuilder(
                           private var title: Option[Title] = None,
                           private val layerElements: ListBuffer[LayerElement] = ListBuffer.empty
                         ) extends Builder[Content]:

  def setTitle(newTitle: Title): Unit =
    if title.isDefined then
      throw new IllegalStateException("Title has already been set for this ContentBuilder.")
    title = Some(newTitle)

  def addLayerElement(element: LayerElement): Unit =
    layerElements += element

  override def build: Content = Content(title, layerElements.toSeq *)

object ContentBuilder:
  def apply(): ContentBuilder = new ContentBuilder()
