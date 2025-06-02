package papyrus.logic.builders

import papyrus.logic.layerElement.captionElement.Image
import papyrus.logic.utility.TypesInline.{Float, Width}

class ImageBuilder:
  var src: String = ""
  var alt: String = ""
  var width: Option[Width] = None
  var caption: Option[String] = None
  var alignment: Option[Float] = None
  
  def build(): Image = 
    Image(src, alt, caption, width, alignment)
