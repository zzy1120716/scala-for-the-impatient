import util.Random

object GetRandomFile extends App {

  def getRandomFileName(bitLen: Int): String = {
    BigInt(bitLen, Random).toString(36)
//    BigInt(Random.nextInt).toString(36)
  }

  for (i <- 1 to 100) {
    println(getRandomFileName(128))
  }
}
