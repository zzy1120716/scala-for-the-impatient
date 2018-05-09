object PrintAnyAndUnit extends App {
  def printAny(x: Any){ println(x) }
  def printUnit(x: Unit) { println(x) }

  printAny("Hello")
  printUnit("Hello")
}
