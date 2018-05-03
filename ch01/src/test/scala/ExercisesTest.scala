import org.scalatest.FunSuite

import math._
import util.Random
import BigInt.probablePrime

class ExercisesTest extends FunSuite {
  test("4") {
    assert(("crazy" * 3) === "crazycrazycrazy")
  }

  test("5") {
//    println(10 max 2)
//    println(10.max(2))
    assert((10 max 2) === 10)
    assert((10 max 11) === 11)
  }

  test("6") {
//    println(BigInt(2).pow(1024))
    assert(BigInt(2).pow(1024) === BigInt("179769313486231590772930519078902473361797697894230657273430081157732675805500963132708477322407536021120113879871393357658789768814416622492847430639474124377767893424865485276302219601246094119453082952085005768838150682342462881473913110540827237163350510684586298239947245938479716304835356329624224137216"))
  }

  test("7") {
//    println(probablePrime(100, Random))
    assert(probablePrime(100, Random).bitLength === 100)
  }

  test("8 get random file test") {
    assert(GetRandomFile.getRandomFileName(128).length >= 24 && GetRandomFile.getRandomFileName(128).length <= 25)
  }

  test("9") {
    val str: String = "Hello World"
    // 首字符
    assert(str.head === 'H')
    assert(str(0) === 'H')
    assert(str.take(1) === "H") // 字符串

    // 尾字符
    assert(str.last === 'd')
    assert(str.takeRight(1) === "d")  // 字符串
    assert(str.reverse(0) === 'd')
    assert(str(str.length - 1) === 'd')
  }

  test("10") {
    val str: String = "Love and Peace"
    assert(str.take(4) === "Love")
    assert(str.takeRight(5) === "Peace")
    assert(str.drop(9) === "Peace")
    assert(str.dropRight(10) === "Love")
    assert(str.substring(5, 8) === "and")
    assert(str.take(8).takeRight(3) === "and")
  }
}
