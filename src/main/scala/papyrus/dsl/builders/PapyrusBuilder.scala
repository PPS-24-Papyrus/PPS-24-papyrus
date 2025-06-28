
package papyrus.dsl.builders

import papyrus.logic.Papyrus
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.Title
import papyrus.dsl.builders.metadataBuilder.MetadataBuilder

enum PapyrusField:
  case Metadata, Content

/** Main builder for the entire Papyrus document */
class PapyrusBuilder:

  private var _metadata: MetadataBuilder = MetadataBuilder()
  private var _content: ContentBuilder = ContentBuilder()

  private val modifiedFields = scala.collection.mutable.Set.empty[PapyrusField]

  private def setOnce[T](field: PapyrusField, setter: T => Unit)(value: T): PapyrusBuilder =
    if modifiedFields.contains(field) then
      throw new IllegalStateException(s"$field has already been set")
    setter(value)
    modifiedFields += field
    this

  /** Sets metadata builder (can be set only once) */
  def withMetadata(value: MetadataBuilder): PapyrusBuilder =
    setOnce(PapyrusField.Metadata, (v: MetadataBuilder) => _metadata = v)(value)

  /** Sets content builder (can be set only once) */
  def withContent(value: ContentBuilder): PapyrusBuilder =
    setOnce(PapyrusField.Content, (v: ContentBuilder) => _content = v)(value)

  /** Sets the document title in content */
  def setTitle(newTitle: Title): Unit = _content.setTitle(newTitle)

  /** Adds a LayerElement to content */
  def addLayerElement(element: LayerElement): Unit = _content.addLayerElement(element)

  /** Builds the full Papyrus document */
  def build(): Unit =
    Papyrus(_metadata.build, _content.build).build()
