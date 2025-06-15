package papyrus.DSL.builders

import papyrus.logic.Papyrus
import papyrus.logic.content.Content
import papyrus.logic.metadata.Metadata

import java.util.Optional


enum PapyrusField:
  case Metadata, Content

class PapyrusBuilder:

  private var _metadata: Metadata = Metadata()
  private var _content: Content = Content(None)

  private val modifiedFields = scala.collection.mutable.Set.empty[PapyrusField]

  private def setOnce[T](field: PapyrusField, setter: T => Unit)(value: T): PapyrusBuilder =
    if modifiedFields.contains(field) then
      throw new IllegalStateException(s"$field has already been set")
    setter(value)
    modifiedFields += field
    this

  def withMetadata(value: Metadata): PapyrusBuilder =
    setOnce(PapyrusField.Metadata, (v: Metadata) => _metadata = v)(value)

  def withContent(value: Content): PapyrusBuilder =
    setOnce(PapyrusField.Content, (v: Content) => _content = v)(value)

  def build(): Unit =
    Papyrus(_metadata, _content).build()
