object PrintNto0 extends App {
  def countdown(n: Int) {
    if (n >= 0) {
      for (i <- (0 to n).reverse) println(i)
    } else {
      for (i <- n to 0) println(i)
    }
  }

  countdown(-5)
  println("------------")
  countdown(10)
}
