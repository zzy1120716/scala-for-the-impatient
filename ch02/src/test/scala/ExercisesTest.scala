import org.scalatest.FunSuite
import math._

class ExercisesTest extends FunSuite {
  test("1") {
    def calcSignum(num: Int): Int = {
      if (num > 0) 1
      else if (num < 0) -1
      else 0
    }
    assert(calcSignum(1234) === 1)
    assert(calcSignum(-567) === -1)
    assert(calcSignum(0) === 0)
  }

  test("2") {
    assert({} === ())
    assert({}.getClass.getSimpleName === "void")
  }

  test("3") {
    var x: Unit = ()
    var y: Int = 0
    assert((x = y = 1) === ())
    assert(x === ())
    assert(y === 1)
  }

  test("4") {
    var s = ""
    for (i <- 0 to 10) s += (10 - i + " ")
    assert(s === "10 9 8 7 6 5 4 3 2 1 0 ")
    s = ""
    for (i <- (0 to 10).reverse) s += (i + " ")
    assert(s === "10 9 8 7 6 5 4 3 2 1 0 ")
//    (1 to 10).reverse foreach println _
  }

  test("6") {
    var r = 1L
    for (ch <- "Hello") {
      r *= ch.toLong
    }
    assert(r === 9415087488L)
  }

  test("7") {
    var r = 1L
    "Hello" foreach { r *= _.toLong }
    assert(r === 9415087488L)

    assert("Hello".foldLeft(1L)(_ * _) === 9415087488L)
  }

  test("8") {
    def calculateCharsUnicodeProduct(s: String): Long = {
      var r = 1L
      for (ch <- s) {
        r *= ch.toLong
      }
      r
    }

    def calculateCharsUnicodeProductNoLoop(s: String): Long = {
      s.foldLeft(1L)(_*_)
    }

    assert(calculateCharsUnicodeProduct("Hello") === 9415087488L)
    assert(calculateCharsUnicodeProductNoLoop("Hello") === 9415087488L)
  }

  test("9") {
    def calculateCharsUnicodeProductRecursive(s: String): Long = {
      if (s.length == 1) s.head.toLong
      else s.head.toLong * calculateCharsUnicodeProductRecursive(s.tail)
    }

    assert(calculateCharsUnicodeProductRecursive("Hello") === 9415087488L)
  }

  test("10") {
    def powRecursive(x: Double, n: Long): Double = {
      if (n == 0) 1
      else if (n < 0) powRecursive(1 / x, -n)
      else if (n % 2 == 0) powRecursive(x, n / 2) * powRecursive(x, n / 2)
      else x * powRecursive(x, n - 1)
    }

    assert(powRecursive(1, 0) === pow(1, 0))
    assert(powRecursive(1 / 2, -2) === pow(1 / 2, -2))
    assert(powRecursive(2, 10) === pow(2, 10))
    assert(powRecursive(2, 101) === pow(2, 101))
    //assert(powRecursive(-25.23, -101) === pow(-25.23, -101))
  }
}
