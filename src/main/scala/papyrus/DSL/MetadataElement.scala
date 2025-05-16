package papyrus.DSL

import papyrus.DSL.PapyrusElement.PapyrusElement

sealed trait MetadataElement extends PapyrusElement

case class Title(metadata: String) extends MetadataElement:
  def render: String = s"<meta name=\"title\" content=\"$metadata\">"

case class Author(metadata: String) extends MetadataElement:
  def render: String = s"<meta name=\"author\" content=\"$metadata\">"

def title(s: String): Title = Title(s)
def author(s: String): Author = Author(s)