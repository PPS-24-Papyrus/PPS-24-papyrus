package papyrus.logic.builders

import papyrus.logic.layerElement.captionElement.Image
import papyrus.logic.utility.TypesInline.{Float, ImageFile, Width}
import io.github.iltotore.iron.autoRefine

class ImageBuilder:
  var src: ImageFile = "default.jpg" 
  var alt: String = ""
  var width: Option[Width] = None
  var caption: Option[String] = None
  var alignment: Option[Float] = None
  
  def build(): Image = 
    Image(src, alt, caption, width, alignment)
