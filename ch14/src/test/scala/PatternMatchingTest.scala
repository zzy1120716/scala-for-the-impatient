import java.awt.Color

import org.scalatest.FunSuite

class PatternMatchingTest extends FunSuite {

  test("a better switch") {
    val ch = '+'
    val sign = ch match {
      case '+' => 1
      case '-' => -1
      case _ => 0
    }
    assert(sign === 1)

    val prefix = "0xff00".substring(0, 2)
    val typ = prefix match {
      case "0" | "0x" | "0X" => "Number"
    }
    assert(typ === "Number")

    val color = Color.BLACK
    val colorPicker = color match {
      case Color.RED => "Red"
      case Color.BLACK => "Black"
      case _ => "Unknown"
    }
    assert(colorPicker === "Black")
  }

  test("guards") {
    var sign = Int.MaxValue
    val ch = '9'
    var digit = 0
    ch match {
      case '+' => sign = 1
      case '-' => sign = -1
      case _ if Character.isDigit(ch) => digit = Character.digit(ch, 10)
      case _ => sign = 0
    }
    assert(digit === 9)
    assert(sign === Int.MaxValue)
  }

  test("variables in patterns") {
    val str = "-1a2b3c4d"
    var sign = 0
    val i = 3; val j = 5
    var digit = 0
    str(i) match {
      case '+' => sign = 1
      case '-' => sign = -1
      case ch => digit = Character.digit(ch, 10)
    }
    assert(digit === 2)

    str(j) match {
      case ch if Character.isDigit(ch) => digit = Character.digit(ch, 10)
    }
    assert(digit === 3)

    import scala.math._
    val c = 6.28; val r = 1
    0.5 * c / r match {
      case Pi =>  // If 0.5 * c / r equals Pi . . .
      // a variable must start with a lowercase letter
      case x =>  // Otherwise set x to 0.5 * c / r . . .
    }

    import java.io.File._ // Imports java.io.File.pathSeparator
    str match {
      // an expression that starts with a lowercase letter, enclose it in
      // backquotes:
      case `pathSeparator` =>  // If str == pathSeparator . . .
      case pathSeparator =>
      // Caution—declares a new variable pathSeparator
    }
  }

  /*test("type patterns") {
    obj match {
      case x: Int => x
      case s: String => Integer.parseInt(s)
      case _: BigInt => Int.MaxValue
      case _ => 0
    }

    obj match {
      case _: BigInt => Int.MaxValue
      case BigInt => -1
    }

    // case m: Map[_, _]

  }*/
}
