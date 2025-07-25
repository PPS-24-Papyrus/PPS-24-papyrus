package papyrus.logic.layerElement.captionElement

import papyrus.logic.Renderer.Text.{MainText, StyleText}
import papyrus.logic.Renderer.Text.*
import papyrus.utility.TypesInline.{Float, Width}
import papyrus.utility.IdGenerator


/** An image element with optional caption, width, and alignment */
trait Image extends CaptionElement:
  /** Image source path (file system path) */
  def src: String

  /** Alternative text (used if image fails to load or by screen readers) */
  def alt: String

  /** Optional image width in pixels */
  def width: Option[Width]

  /** Optional image alignment (e.g. float left/right) */
  def alignment: Option[Float]


object Image:

  /** Creates an Image with src, alt text, optional caption, width and alignment */
  def apply(src: String,
            alt: String,
            caption: Option[String],
            width: Option[Width],
            alignment: Option[Float]): Image = new ImageImpl(src, alt, caption, width, alignment)

  private class ImageImpl(override val src: String,
                          override val alt: String,
                          override val caption: Option[String],
                          override val width: Option[Width],
                          override val alignment: Option[Float]) extends Image:
    private val idImage: String = "cls-" + IdGenerator.nextId()
    private val idFigure: String = "cls-" + IdGenerator.nextId()

    override def render: MainText =
      val captionString = caption.map(c => s"<figcaption>$c</figcaption>").getOrElse("")

      val figureStart = s"""<figure class="$idFigure">"""
      val figureEnd = s"""</figure>\n"""

      val content = checkPathImage(src).map: validPath =>
        s"""<img class="$idImage" src="file:///$validPath" alt="$alt"></img>"""

      val innerHtml = content match
        case Right(imgTag) => s"  $imgTag\n  $captionString"
        case Left(error)   => s"""  <p style="color: red;">Error loading image: $error</p>\n  $captionString"""

      s"""$figureStart
         |$innerHtml
         |$figureEnd""".stripMargin.toMainText

    override def renderStyle: StyleText =
        val widthStyle = if width.isEmpty then "" else s"width: ${width.get}px;\n height: auto;"
        val alignmentStyle = if alignment.isEmpty then "display: block; margin: 0 auto;" else s"float: ${alignment.get};"
        if width.isEmpty && alignment.isEmpty then "".toStyleText
        else
          s""".$idImage {\n  $widthStyle\n}
             |
             |.$idFigure {\n  text-align: center;\n  $alignmentStyle\n}
             |""".stripMargin.toStyleText

    import better.files._

    private def checkPathImage(pathStr: String): Either[String, String] =
      val file = File(pathStr)

      Either.cond(
        file.isRegularFile,
        file,
        s"File not found or invalid: $pathStr"
      ).flatMap: f =>
        val name = f.name.toLowerCase
        if name.endsWith(".png") || name.endsWith(".jpg") then
          Right(f.pathAsString.replace('\\', '/'))
        else
          Left(s"Invalid format: only .png or .jpg allowed ($pathStr)")