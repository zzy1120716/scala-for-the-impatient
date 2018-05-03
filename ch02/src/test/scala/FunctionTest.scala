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
}
