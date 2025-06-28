package papyrus.utility

/** Generates unique hexadecimal string IDs using an internal counter */
object IdGenerator:

  private var counter: Int = 1000

  /** Returns the next unique ID as an uppercase hexadecimal string */
  def nextId(): String =
    val hexValue = f"$counter%X"
    counter += 1
    hexValue
