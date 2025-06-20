package papyrus.logic.layerElement.captionElement

import papyrus.logic.layerElement.LayerElement


/** A layer element that can have an optional caption */
trait CaptionElement extends LayerElement:
  /** The optional caption text for the element */
  def caption: Option[String]
