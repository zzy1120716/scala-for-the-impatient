import util.Random

object RandomIntArray extends App {
  def getRandomIntArray(n: Int): Array[Int] = {
    (for (i <- 0 until n) yield Random.nextInt(n)) toArray
  }

  println(getRandomIntArray(10).mkString(", "))
}
