package papyrus.logic.layerElement.captionElement

import papyrus.logic.layerElement.LayerElement


trait CaptionElement extends LayerElement:
  def caption: Option[String]