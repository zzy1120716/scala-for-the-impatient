import java.text.MessageFormat

import org.scalatest.FunSuite

class FunctionTest extends FunSuite {

  test("function definition") {

    def abs(x: Double) = if (x >= 0) x else -x

    def fac(n: Int) = {
      var r = 1
      for (i <- 1 to n) r = r * i
      r
    }

    def facRecursive(n : Int) : Int = if (n <= 0) 1 else n * facRecursive(n - 1)

    assert(abs(-1) === 1.0)
    assert(fac(10) === 3628800)
    assert(facRecursive(10) === 3628800)
  }

  test("default and named arguments") {
    def decorate(str: String, left: String = "[", right: String = "]") =
      left + str + right

    assert(decorate("Hello") === "[Hello]")
    assert(decorate("Hello", "<<<", ">>>") === "<<<Hello>>>")
    assert(decorate("Hello", ">>>[") === ">>>[Hello]")
    assert(decorate(left = "<<<", str = "Hello", right = ">>>") === "<<<Hello>>>")
    assert(decorate("Hello", right = "]<<<") === "[Hello]<<<")
  }

  test("variable arguments") {
    def sum(args: Int*) = {
      var result = 0
      for (arg <- args) {
        result += arg
      }
      result
    }

    def recursiveSum(args: Int*) : Int = {
      if (args.isEmpty) 0
      else args.head + recursiveSum(args.tail: _*)
    }

    assert(sum(1, 4, 9, 16, 25) === 55)
    //val s = sum(1 to 5) // error
    val s = sum(1 to 5: _*) // consider 1 to 5 as an argument sequence
    assert(s === 15)

    assert(recursiveSum(1, 4, 9, 16, 25) === 55)

    // convert any primitive types by hand
    val str = MessageFormat.format("The answer to {0} is {1}", "everything", 42.asInstanceOf[AnyRef])
    assert(str === "The answer to everything is 42")
  }
}
