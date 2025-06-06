package papyrus.logic.layerElement.captionElement

import papyrus.logic.utility.IdGenerator
import papyrus.logic.utility.TypesInline.{Float, ImageFile, Width}

trait Image extends CaptionElement:
  def src: ImageFile
  def alt: String
  def width: Option[Width]
  def alignment: Option[Float]

object Image:
  def apply(src: ImageFile, alt: String, caption: Option[String], width: Option[Width],  alignment: Option[Float]): Image = new ImageImpl(src, alt, caption, width, alignment)

  private class ImageImpl(override val src: ImageFile,
                          override val alt: String,
                          override val caption: Option[String],
                          override val width: Option[Width],
                          override val alignment: Option[Float]) extends Image:
    private val idImage: String = "cls-" + IdGenerator.nextId()
    private val idFigure: String = "cls-" + IdGenerator.nextId()

    override def render: String =
      val captionString: String = if this.caption.isEmpty then "" else s"<figcaption>${caption.get}</figcaption>"
      s"""<figure class="$idFigure">
        <img class="$idImage" src="file:///$src" alt="$alt"></img>
        $captionString
      </figure>"""

    override def renderStyle: String =
      val widthStyle = if width.isEmpty then "" else s"width: ${width.get};\nheight: auto;"
      val alignmentStyle = if alignment.isEmpty then "display: block; margin: 0 auto;" else s"float: ${alignment.get};"
      if width.isEmpty && alignment.isEmpty then ""
      else
        s""".$idImage {\n  $widthStyle\n}
           |
           |.$idFigure {\n  text-align: center;\n  $alignmentStyle\n}
           |""".stripMargin