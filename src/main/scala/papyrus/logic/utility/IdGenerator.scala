package papyrus.logic.utility

object IdGenerator:
  private var counter: Int = 1000

  def nextId(): String = 
    val hexValue = f"$counter%X" 
    counter += 1
    hexValue