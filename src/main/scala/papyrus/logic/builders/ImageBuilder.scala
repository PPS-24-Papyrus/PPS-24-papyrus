package papyrus.logic.builders

import papyrus.logic.layerElement.captionElement.Image
import papyrus.logic.utility.TypesInline.{Float, Width}
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.DefaultValues

case class ImageBuilder(
                             private val src: String,
                             private val alt: String,
                             private val width: Option[Width],
                             private val caption: Option[String],
                             private val alignment: Option[Float]
                           ) extends Builder[Image]:
  private def withSrc(s: String): ImageBuilder = this.copy(src = s)
  private def withAlt(a: String): ImageBuilder = this.copy(alt = a)
  private def withWidth(w: Width): ImageBuilder = this.copy(width = Some(w))
  private def withCaption(c: String): ImageBuilder = this.copy(caption = Some(c))
  private def withAlignment(a: Float): ImageBuilder = this.copy(alignment = Some(a))
  override def build: Image = Image(src, alt, caption, width, alignment)


object ImageBuilder:
  def apply(
             src: String = DefaultValues.defaultImageSrc,
             alt: String = DefaultValues.defaultImageAlt,
             width: Option[Width] = None,
             caption: Option[String] = None,
             alignment: Option[Float] = None
           ): ImageBuilder = new ImageBuilder(src, alt, width, caption, alignment)
  

  extension (str: ImageBuilder)
    def alternative(a: String): ImageBuilder = str.withAlt(a)
    def caption(c: String): ImageBuilder = str.withCaption(c)
    def width(w: Width): ImageBuilder = str.withWidth(w)
    def alignment(a: Float): ImageBuilder = str.withAlignment(a)
