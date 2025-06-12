package papyrus.DSL.builders

import papyrus.logic.Papyrus
import papyrus.logic.content.Content
import papyrus.logic.metadata.Metadata

import java.util.Optional


class PapyrusBuilder:
  var metadata: Metadata = Metadata()
  var content: Content = Content(None)

  def build: Unit = Papyrus(metadata, content).build()