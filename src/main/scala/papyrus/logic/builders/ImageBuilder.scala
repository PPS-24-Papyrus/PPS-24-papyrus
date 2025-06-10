package papyrus.logic.builders

import papyrus.logic.layerElement.captionElement.Image
import papyrus.logic.utility.TypesInline.{Float, Width}
import io.github.iltotore.iron.autoRefine

//class ImageBuilder:
//  var src: String = "default.jpg" 
//  var alt: String = ""
//  var width: Option[Width] = None
//  var caption: Option[String] = None
//  var alignment: Option[Float] = None
//  
//  def build(): Image = 
//    Image(src, alt, caption, width, alignment)

final class ImageBuilder (
                                   private val src: Option[String] = None,
                                   private val alt: Option[String] = None,
                                   private val width: Option[Width] = None,
                                   private val caption: Option[String] = None,
                                   private val alignment: Option[Float] = None
                                 ):
  private def withSrc(s: String): ImageBuilder = ImageBuilder(src = Some(s), alt, width, caption, alignment)
  private def withAlt(a: String): ImageBuilder = ImageBuilder(src, Some(a), width, caption, alignment)
  private def withWidth(w: Width): ImageBuilder = ImageBuilder(src, alt, Some(w), caption, alignment)
  private def withCaption(c: String): ImageBuilder = ImageBuilder(src, alt, width, Some(c), alignment)
  private def withAlignment(a: Float): ImageBuilder = ImageBuilder(src, alt, width, caption, Some(a))
  def build: Image = Image(src.getOrElse("default.jpg"), alt.getOrElse(""), caption, width, alignment)

object ImageBuilder:
  def apply(
             src: Option[String] = None,
             alt: Option[String] = None,
             width: Option[Width] = None,
             caption: Option[String] = None,
             alignment: Option[Float] = None
           ): ImageBuilder = new ImageBuilder(src, alt, width, caption, alignment)

  extension (str: ImageBuilder)
    def alternative(a: String): ImageBuilder = str.withAlt(a)
    def caption(c: String): ImageBuilder = str.withCaption(c)
    def width(w: Width): ImageBuilder = str.withWidth(w)
    def alignment(a: Float): ImageBuilder = str.withAlignment(a)
