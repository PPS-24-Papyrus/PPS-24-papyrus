package papyrus.logic.builders

import papyrus.logic.styleObjects.MainStyle
import papyrus.logic.utility.TypesInline.{Charset, Extension, Language}
import io.github.iltotore.iron.autoRefine
import papyrus.logic.metadata.Metadata


class MetadataBuilder:
  private var nameFile: String = "newFile"
  private var extension: Extension = "html"
  private var style: MainStyle = MainStyle()
  private var language: Language = "en"
  private var title: String = "index"
  private var author: String = "Unknown"
  private var charset: Charset = "utf-8"
  private var styleSheet: String = "style.css"

  def build(): Metadata =
    Metadata(nameFile, extension, style, language, title, author, charset, styleSheet)