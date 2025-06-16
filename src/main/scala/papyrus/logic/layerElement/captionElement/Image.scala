package papyrus.logic.layerElement.captionElement

import papyrus.logic.Renderer.Text.{MainText, StyleText}
import papyrus.logic.Renderer.Text.*
import papyrus.logic.utility.IdGenerator
import papyrus.logic.utility.TypesInline.{Float, Width}


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

    override def render: MainText =
      val captionString =
        if caption.isEmpty then ""
        else s"<figcaption>${caption.get}</figcaption>"

      checkPathImage(src) match
        case Right(path) =>
          s"""<figure class="$idFigure">
             |  <img class="$idImage" src="file:///$path" alt="$alt"></img>
             |  $captionString
             |</figure>\n""".stripMargin.toMainText

        case Left(error) =>
          s"""<figure class="$idFigure">
             |  <p style="color: red;">Error loading image: $error</p>
             |  $captionString
             |</figure>\n""".stripMargin.toMainText

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
          println(f.pathAsString.replace('\\', '/'))
          Right(f.pathAsString.replace('\\', '/'))
        else
          Left(s"Invalid format: only .png or .jpg allowed ($pathStr)")