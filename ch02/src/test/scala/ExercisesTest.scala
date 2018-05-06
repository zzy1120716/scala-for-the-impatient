import org.scalatest.FunSuite
import math._

class ExercisesTest extends FunSuite {

  /**
    * The signum of a number is 1 if the number is positive,
    * –1 if it is negative, and 0 if it is zero.
    * Write a function that computes this value.
    */
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

  /**
    * What is the value of an empty block expression {}?
    * What is its type?
    */
  test("2") {
    assert({} === ())
    assert({}.getClass.getSimpleName === "void")
  }

  /**
    * Come up with one situation where the assignment
    * x = y = 1 is valid in Scala.
    * (Hint: Pick a suitable type for x.)
    */
  test("3") {
    var x: Unit = ()
    var y: Int = 0
    assert((x = y = 1) === ())
    assert(x === ())
    assert(y === 1)
  }

  /**
    * Write a Scala equivalent for the Java loop
    * for (int i = 10; i >= 0; i--) System.out.println(i);
    */
  test("4") {
    var s = ""
    for (i <- 0 to 10) s += (10 - i + " ")
    assert(s === "10 9 8 7 6 5 4 3 2 1 0 ")
    s = ""
    for (i <- (0 to 10).reverse) s += (i + " ")
    assert(s === "10 9 8 7 6 5 4 3 2 1 0 ")
    //(1 to 10).reverse foreach println _
  }

  /**
    * Write a procedure countdown(n: Int)
    * that prints the numbers from n to 0.
    * PrintNto0
    */
  test("5") {
    PrintNto0.countdown(5)
  }

  /**
    * Write a for loop for computing the product of the Unicode codes of all letters in a string.
    * For example, the product of the characters in "Hello" is 825152896.
    */
  test("6") {
    var r = 1L
    for (ch <- "Hello") {
      r *= ch.toLong
    }
    assert(r === 9415087488L)
  }

  /**
    * Solve the preceding exercise without writing a loop.
    * (Hint: Look at the StringOps Scaladoc.)
    */
  test("7") {
    var r = 1L
    "Hello" foreach { r *= _.toLong }
    assert(r === 9415087488L)

    assert("Hello".foldLeft(1L)(_ * _) === 9415087488L)
  }

  /**
    * Write a function product(s : String)
    * that computes the product, as described in the preceding exercises.
    */
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

  /**
    * Make the function of the preceding exercise a recursive function.
    */
  test("9") {
    def calculateCharsUnicodeProductRecursive(s: String): Long = {
      if (s.length == 1) s.head.toLong
      else s.head.toLong * calculateCharsUnicodeProductRecursive(s.tail)
    }

    assert(calculateCharsUnicodeProductRecursive("Hello") === 9415087488L)
  }

  /**
    * Write a function that computes x , where n is an integer. Use the following
    * Don’t use a return statement.
    */
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
