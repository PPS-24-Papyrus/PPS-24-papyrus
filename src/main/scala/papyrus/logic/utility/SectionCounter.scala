package papyrus.logic.utility


object SectionCounter:
  private var section: Int = 0
  private var subsection: Int = 0

  def reset(): Unit =
    section = 0
    subsection = 0

  def nextSection(): String =
    section += 1
    subsection = 0
    currentString

  def nextSubsection(): String =
    subsection += 1
    currentString

  def current(): (Int, Int) =
    (section, subsection)

  def currentString: String =
    if subsection == 0 then s"$section"
    else s"$section.$subsection"
