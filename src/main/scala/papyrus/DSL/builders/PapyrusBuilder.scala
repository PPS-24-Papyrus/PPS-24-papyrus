package papyrus.DSL.builders

import papyrus.logic.Papyrus
import papyrus.logic.content.Content
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.Title
import papyrus.logic.metadata.Metadata

import java.util.Optional


enum PapyrusField:
  case Metadata, Content

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

  def withMetadata(value: MetadataBuilder): PapyrusBuilder =
    setOnce(PapyrusField.Metadata, (v: MetadataBuilder) => _metadata = v)(value)

  def withContent(value: ContentBuilder): PapyrusBuilder =
    setOnce(PapyrusField.Content, (v: ContentBuilder) => _content = v)(value)

  def setTitle(newTitle: Title): Unit = _content.setTitle(newTitle)

  def addLayerElement(element: LayerElement): Unit = _content.addLayerElement(element)

  def build(): Unit =
    Papyrus(_metadata.build, _content.build).build()
