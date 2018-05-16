import org.scalatest.FunSuite

class ExercisesTest extends FunSuite {

  /**
    * According to the precedence rules, how are 3 + 4 -> 5 and 3 -> 4 + 5 evaluated?
    */
  test("1") {
    assert(3 + 4 -> 5 === (7 -> 5))
    assert(3 -> (4 + 5) === (3 -> 9))
  }

  /**
    * Implement the Fraction class with operations + - * /. Normalize fractions, for
    * example, turning 15/–6 into –5/2. Divide by the greatest common divisor,
    * like this:
    * class Fraction(n: Int, d: Int) {
    *   private val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d);
    *   private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d);
    *   override def toString = s"$num/$den"
    *   def sign(a: Int) = if (a > 0) 1 else if (a < 0) -1 else 0
    *   def gcd(a: Int, b: Int): Int = if (b == 0) abs(a) else gcd(b, a % b)
    *   ...
    * }
    */
  test("3") {

  }
}
