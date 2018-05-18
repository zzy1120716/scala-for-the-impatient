import org.scalatest.FunSuite

import math._

class ExercisesTest extends FunSuite {

  /**
    * Write a function values(fun: (Int) => Int, low: Int, high: Int)
    * that yields a collection of function inputs and outputs in a
    * given range. For example, values(x => x * x, -5, 5)
    * should produce a collection of pairs(-5, 25),(-4, 16),(-3, 9),
    * ..., (5, 25).
    */
  test("1") {
    def values(fun: Int => Int, low: Int, high: Int) = {
      (low to high) map { x => (x, fun(x)) }
    }
    assert(values(x => x * x, -3, 3).mkString(", ") === "(-3,9), (-2,4), (-1,1), (0,0), (1,1), (2,4), (3,9)")
    assert(values(x => abs(x), -3, 3).mkString(", ") === "(-3,3), (-2,2), (-1,1), (0,0), (1,1), (2,2), (3,3)")
  }

  /**
    * How do you get the largest element of an array with reduceLeft?
    */
  test("2") {
    assert(Array(5, 3, 6, 2, 7, 1, 9, 4, 8).reduceLeft((a, b) => if (a > b) a else b) === 9)
  }

  /**
    * Implement the factorial function using to and reduceLeft,
    * without a loop or recursion.
    */
  test("3") {
    def factorial(n: Int): BigInt = {
      (1 to n).reduceLeft(_ * _)
    }
    assert(factorial(10) === 3628800)
    for (i <- 1 to 10) {
      println(factorial(i))
    }
  }

  /**
    * The previous implementation needed a special case when n < 1.
    * Show how you can avoid this with foldLeft. (Look at the Scaladoc
    * for foldLeft. It’s like reduceLeft, except that the first value
    * in the chain of combined values is supplied in the call.)
    */
  test("4") {
    def factorial(n: Int): BigInt = {
      (1 to n).foldLeft(1)(_ * _)
    }
    assert(factorial(5) === 120)
    assert(factorial(-3) === 1)
    assert(factorial(0) === 1)
  }

  /**
    * Write a function largest(fun: (Int) => Int, inputs: Seq[Int])
    * that yields the largest value of a function within a given
    * sequence of inputs. For example, largest(x => 10 * x - x * x,
    * 1 to 10) should return 25. Don’t use a loop or recursion.
    */
  test("5") {
    def largest(fun: Int => Int, inputs: Seq[Int]) = {
      //inputs.map(x => fun(x)).reduceLeft((a, b) => if (a > b) a else b)
      inputs.map(fun(_)).max
    }
    assert(largest(x => 10 * x - x * x, 1 to 10) === 25)
  }

  /**
    * Modify the previous function to return the input at which the
    * output is largest. For example, largestAt(x => 10 * x - x * x,
    * 1 to 10) should return 5. Don’t use a loop or recursion.
    */
  test("6") {
    def largestAt(fun: Int => Int, inputs: Seq[Int]) = {
      inputs.reduceLeft((a, b) => if (fun(a) > fun(b)) a else b)
    }
    assert(largestAt(x => 10 * x - x * x, 1 to 10) === 5)
  }

  /**
    * It’s easy to get a sequence of pairs, for example:
    *   val pairs = (1 to 10) zip (11 to 20)
    * Now, suppose you want to do something with such a sequence—say,
    * add up the values. But you can’t do
    *        pairs.map(_ + _)
    * The function _ + _ takes two Int parameters, not an (Int, Int)
    * pair. Write a function adjustToPair that receives a function of
    * type (Int, Int) => Int and returns the equivalent function that
    * operates on a pair. For example, adjustToPair(_ * _)((6, 7)) is
    * 42.
    * Then use this function in conjunction with map to compute the
    * sums of the elements in pairs.
    */
  test("7") {
    val pairs = (1 to 10) zip (11 to 20)
    println(pairs.mkString(", "))

    def adjustToPair = (fun: (Int, Int) => Int) => (pair: (Int, Int)) => fun(pair._1, pair._2)

    assert(adjustToPair(_*_)((6, 7)) === 42)

    assert(pairs.map(adjustToPair(_ + _)).mkString(", ") === "12, 14, 16, 18, 20, 22, 24, 26, 28, 30")
  }

  /**
    * In Section 12.8, “Currying,” on page 164, you saw the
    * corresponds method used with two arrays of strings. Make a call
    * to corresponds that checks whether the elements in an array of
    * strings have the lengths given in an array of integers.
    */
  test("8") {
    val a = Array("Hello", "ZZY's", "World!")
    val b = Array(5, 5, 6)
    assert(a.corresponds(b)(_.length == _))
  }

  /**
    * Implement corresponds without currying. Then try the call from
    * the preceding exercise. What problem do you encounter?
    */
  test("9") {
    def corresponds[A, B](a: Seq[A], b: Seq[B], f: (A, B) => Boolean): Boolean = {
      (a zip b).map(x => f(x._1, x._2)).count(!_) == 0
    }
    val a = Array("Hello", "ZZY's", "World!")
    val b = Array(5, 5, 6)
    assert(corresponds(a, b, (x: String, y: Int) => x.length == y))
  }

  /**
    * Implement an unless control abstraction that works just like
    * if, but with an inverted condition. Does the first parameter
    * need to be a call-by-name parameter? Do you need currying?
    */
  test("10") {
    def unless(condition: => Boolean)(block: => Unit): Unit = {
      if (!condition) {
        block
      }
    }
    unless(1 > 2) {
      println("Unless!")
    }
  }
}
