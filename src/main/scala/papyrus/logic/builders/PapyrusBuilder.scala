package papyrus.logic.builders

import papyrus.logic.Papyrus
import papyrus.logic.content.Content
import papyrus.logic.metadata.Metadata

import java.util.Optional


class PapyrusBuilder:
  var metadata: Metadata = Metadata()
  var content: Content = Content(Optional.empty)

  def build(): Unit = Papyrus(metadata, content).build()