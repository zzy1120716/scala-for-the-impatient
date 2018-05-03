import org.scalatest.FunSuite
import math._
import util.control.Breaks._

class ControlTest extends FunSuite {
  test("conditional expressions") {
    val x = 1
    val s = if (x > 0) 1 else -1
    assert(s === 1)

    // mixed-type expression
    assert((if (x > 0) "positive" else -1) === "positive")

    // class Unit
    val y = -1
    assert((if (y > 0) 1 else ()).getClass.getSimpleName === "BoxedUnit")

    // the parser will find the "else" on the next line
    val z = if (x > 0) 1
    else if (x == 0) 0 else -1
    assert(z === 1)
  }

  test("statement termination") {
    // multi-statement in one line
    var r = 1
    var n = 10
    if (n > 0) { r = r * n; n -= 1}
    assert(r === 10)

    // the + tells the parser that this is not the end
    val s0 = 100
    val v0, a0 = 0
    val v = 20
    val a, t = 5
    val s = s0 + (v - v0) * t + // the + doesn't mean the end
    0.5 * (a - a0) * t * t
    assert(s === 262.5)

    // the Kernighan & Ritchie brace style
    if (n > 0) {
      r = r * n
      n -= 1
    }
    assert(r === 90)

    // semicolons do no harm
  }

  test("block expression and assignments") {
    // the value of the block is the value of the last expression
    val x0, y0 = 0
    val x = 3; val y = 4
    val distance = { val dx = x - x0; val dy = y - y0; sqrt(dx * dx + dy * dy) }
    assert(distance === 5)

    // a block that ends with an assignment statement has a Unit value
    var r = 1; var n = 10
    assert({ r = r * n; n -= 1 }.getClass.getSimpleName === "void")
    //x = y = 1 // don't do this
  }

  test("loops") {
    // while loop
    var n = 10; var r = 1
    while (n > 0) {
      r = r * n
      n -= 1
    }
    assert(r === 3628800)

    // do...while loop
    r = 1; n = 10
    do {
      r = r * n
      n -= 1
    } while (n > 0)
    assert(r === 3628800)

    // for loop with "to"
    r = 1; n = 10
    for (i <- 1 to n) {
      r = r * i
    }
    assert(r === 3628800)

    // for loop with "until"
    val s = "Hello"
    var sum = 0
    for (i <- 0 until s.length) { // last value for i is s.length - 1
      sum += s(i)
    }
    assert(sum === 500)

    // for loop with "until" without indexes
    sum = 0
    for (ch <- "Hello") sum += ch
    assert(sum === 500)

    // break out of a loop
    sum = 0
    breakable {
      for (i <- 1 to 10) {
        sum += i
        if (sum > 10) break
      }
    }
    assert(sum === 15)
  }

  test("advanced for loops and for comprehensions") {
    // generators
    var s = ""
    for (i <- 1 to 3; j <- 1 to 3) s += ((10 * i + j) + " ")
    assert(s === "11 12 13 21 22 23 31 32 33 ")

    // generators with guard
    s = ""
    for (i <- 1 to 3; j <- 1 to 3 if i != j) s += ((10 * i + j) + " ")
    assert(s === "12 13 21 23 31 32 ")

    // use definitions between generators
    s = ""
    for (i <- 1 to 3; from = 4 - i; j <- from to 3) s += ((10 * i + j) + " ")
    assert(s === "13 22 23 31 32 33 ")

    // for comprehensions
    val res1 = for (i <- 1 to 10) yield i % 3
    assert(res1 === Vector(1, 2, 0, 1, 2, 0, 1, 2, 0, 1))

    // compatible with the first generator
    val res2 = for (c <- "Hello"; i <- 0 to 1) yield (c + i).toChar
    assert(res2 === "HIeflmlmop")

    val res3 = for (i <- 0 to 1; c <- "Hello") yield (c + i).toChar
    assert(res3 === Vector('H', 'e', 'l', 'l', 'o', 'I', 'f', 'm', 'm', 'p'))

    // another way: write generator, guard, definition in braces
    s = ""
    for { i <- 1 to 3
      from = 4 - i
      j <- from to 3}
      s += ((10 * i + j) + " ")
    assert(s === "13 22 23 31 32 33 ")
  }
}
