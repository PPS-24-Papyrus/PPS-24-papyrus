package papyrus.logic.layerElement.captionElement

import papyrus.logic.utility.IdGenerator
import papyrus.logic.utility.TypesInline.{Float, Width}

import java.nio.file.Paths

trait Image extends CaptionElement:
  def src: String
  def alt: String
  def width: Option[Width]
  def alignment: Option[Float]

object Image:
  def apply(src: String, alt: String, caption: Option[String], width: Option[Width],  alignment: Option[Float]): Image = new ImageImpl(src, alt, caption, width, alignment)

  private class ImageImpl(override val src: String,
                          override val alt: String,
                          override val caption: Option[String],
                          override val width: Option[Width],
                          override val alignment: Option[Float]) extends Image:
    private val idImage: String = "cls-" + IdGenerator.nextId()
    private val idFigure: String = "cls-" + IdGenerator.nextId()

    override def render: String =
      val captionString =
        if caption.isEmpty then ""
        else s"<figcaption>${caption.get}</figcaption>"

      checkPathImage(src) match
        case Right(path) =>
          s"""<figure class="$idFigure">
             |  <img class="$idImage" src="file:///$path" alt="$alt"></img>
             |  $captionString
             |</figure>""".stripMargin

        case Left(error) =>
          s"""<figure class="$idFigure">
             |  <p style="color: red;">Error loading image: $error</p>
             |  $captionString
             |</figure>""".stripMargin


    override def renderStyle: String =
        val widthStyle = if width.isEmpty then "" else s"width: ${width.get};\nheight: auto;"
        val alignmentStyle = if alignment.isEmpty then "display: block; margin: 0 auto;" else s"float: ${alignment.get};"
        if width.isEmpty && alignment.isEmpty then ""
        else
          s""".$idImage {\n  $widthStyle\n}
             |
             |.$idFigure {\n  text-align: center;\n  $alignmentStyle\n}
             |""".stripMargin

    private def checkPathImage(pathStr: String): Either[String, String] =
      val path = Paths.get(pathStr)
      val file = path.toFile
      if !file.exists || !file.isFile then
        Left(s"File not found or invalid: $pathStr")
      else
        val lowerName = file.getName.toLowerCase
        if lowerName.endsWith(".png") || lowerName.endsWith(".jpg") then
          Right(file.getAbsolutePath)
        else
          Left(s"Invalid format: only .png or .jpg allowed ($pathStr)")