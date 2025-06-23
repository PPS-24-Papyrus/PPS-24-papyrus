package papyrus.DSL.builders

import papyrus.logic.content.Content
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.Title
import scala.collection.mutable.ListBuffer

/** Builds the content section with an optional title and a list of elements */
case class ContentBuilder(
                           private var title: Option[Title] = None,
                           private val layerElements: ListBuffer[LayerElement] = ListBuffer.empty
                         ) extends Builder[Content]:

  /** Sets title*/
  def setTitle(newTitle: Title): Unit =
    if title.isDefined then
      throw new IllegalStateException("Title has already been set for this ContentBuilder.")
    title = Some(newTitle)

  /** Adds a content element (title, image, table, etc.) */
  def addLayerElement(element: LayerElement): Unit =
    layerElements += element

  /** Builds the final Content object */
  override def build: Content = Content(title, layerElements.toSeq *)

object ContentBuilder:
  def apply(): ContentBuilder = new ContentBuilder()
