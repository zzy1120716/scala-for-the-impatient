import java.awt.event.{ActionEvent, ActionListener}

import javax.swing.JButton
import org.scalatest.FunSuite

class HigherOrderFuncTest extends FunSuite {

  test("functions as values") {
    import scala.math._
    val num = 3.14
    val fun = ceil _
    val f1: Double => Double = ceil // no underscore needed
    assert(fun(num) === 4)
    assert(f1(num) === 4)

    val f2 = (_: String).charAt(_: Int)
    val f3: (String, Int) => Char = _.charAt(_)
    assert(f2("Hello", 2) === 'l')
    assert(f3("Hello", 2) === 'l')

    // give fun to another function
    assert(Array(3.14, 1.42, 2.0).map(fun) === Array(4, 2, 2))
  }

  test("anonymous functions") {
    val triple = (x: Double) => 3 * x
    // def triple(x: Double) = 3 * x
    assert(Array(3.14, 1.42, 2.0).map(triple) === Array(9.42, 4.26, 6.0))
    assert(Array(3.14, 1.42, 2.0).map((x: Double) => 3 * x) === Array(9.42, 4.26, 6.0))
    assert(Array(3.14, 1.42, 2.0).map{ x: Double => 3 * x } === Array(9.42, 4.26, 6.0))
    assert((Array(3.14, 1.42, 2.0) map { x: Double => 3 * x }) === Array(9.42, 4.26, 6.0))
  }

  test("functions with function parameters") {
    import math._
    def valueAtOneQuarter(f: Double => Double) = f(0.25)
    // (parameterType) => resultType
    assert(valueAtOneQuarter(ceil _) === 1.0)
    assert(valueAtOneQuarter(sqrt _) === 0.5)

    def mulBy(factor : Double) = (x : Double) => factor * x
    // (Double) => ((Double) => Double)
    val quintuple = mulBy(5)
    assert(quintuple(20) === 100.0)
  }

  test("parameter inference") {
    def valueAtOneQuarter(f: Double => Double) = f(0.25)
    assert(valueAtOneQuarter((x: Double) => 3 * x) === 0.75)
    assert(valueAtOneQuarter(x => 3 * x) === 0.75)
    assert(valueAtOneQuarter(_ * 3) === 0.75)

    //val fun = 3 * _ // error: can't infer types
    val fun = 3 * (_: Double) // ok
    //val fun: Double => Double = 3 * _ // ok because we specified the type for fun
    assert(fun(3.14) === 9.42)

    val f1 = (_: String).length
    val f2 = (_: String).substring(_: Int, _: Int)
    assert(f1("Hello, World!") === 13)
    assert(f2("Hello, World!", 0, 5) === "Hello")
  }

  test("useful higher-order functions") {
    assert((1 to 9).map(0.1 * _).mkString(", ") === "0.1, 0.2, 0.30000000000000004, 0.4, 0.5, 0.6000000000000001, 0.7000000000000001, 0.8, 0.9")

    (1 to 9).map("*" * _).foreach(println _)

    assert((1 to 9).filter(_ % 2 == 0).mkString(", ") === "2, 4, 6, 8")

    assert((1 to 9).reduceLeft(_ * _) === 362880)
    assert((1 to 9).product === 362880)

    assert("Mary had a little lamb".split(" ").sortWith(_.length < _.length) === Array("a", "had", "Mary", "lamb", "little"))
  }

  test("closures") {
    def mulBy(factor: Double) = (x: Double) => factor * x
    val triple = mulBy(3)
    val half = mulBy(0.5)
    assert(s"${triple(14)} ${half(14)}" === "42.0 7.0")
  }

  test("SAM conversions") {
    var counter = 0

    val button = new JButton("Increment")
    /*button.addActionListener(new ActionListener {
      override def actionPerformed(event: ActionEvent) {
        counter += 1
      }
    })*/
    button.addActionListener(event => counter += 1)

    // conversion from a Scala function to a Java SAM interface only works for function literals

    /*val listener = (event: ActionListener) => println(counter)
    button.addActionListener(listener)*/
    // cannot convert a nonliteral function to a Java SAM interface

    val listener: ActionListener = event => println(counter)
    button.addActionListener(listener)  // OK

    // turn a function variable into a literal expression
    val exit = (event: ActionEvent) => if (counter > 9) System.exit(0)
    button.addActionListener(exit(_))
  }

  test("currying") {
    // takes two arguments into a function that takes one argument
    val mul = (x: Int, y: Int) => x * y
    val mulOneAtATime = (x: Int) => (y: Int) => x * y
    assert(mul(6, 7) === 42)
    assert(mulOneAtATime(6)(7) === 42)

    val mulBy6 = mulOneAtATime(6)
    assert(mulBy6(7) === 42)

    // shortcut for defining curried methods
    def mulOneAtATimeShort(x: Int)(y: Int): Int = x * y
    assert(mulOneAtATimeShort(6)(7) === 42)

    // use currying for a method parameter so that the type inferencer has more information
    val a = Array("Hello", "World")
    val b = Array("hello", "world")
    assert(a.corresponds(b)(_.equalsIgnoreCase(_)) === true)
    assert(a.corresponds(b)((a: String, b: String) => a.equalsIgnoreCase(b)) === true)
  }

  test("control abstractions") {
    def runInThread(block: () => Unit) {
      new Thread {
        override def run() { block() }
      }.start()
    }
    runInThread { () => println("Hi"); Thread.sleep(10000); println("Bye") }

    def runInThreadSightly(block: => Unit): Unit = {
      new Thread {
        override def run() { block }
      }.start()
    }
    runInThreadSightly { println("Hi"); Thread.sleep(10000); println("Bye") }

    // implement a function that is used exactly as a while statement
    def until(condition: => Boolean)(block: => Unit): Unit = {
      if (!condition) {
        block
        until(condition)(block)
      }
    }

    var x = 10
    until(x == 0) {
      x -= 1
      println(x)
    }

    def untilNoCurried(condition: => Boolean, block: => Unit): Unit = {
      if (!condition) {
        block
        until(condition)(block)
      }
    }
    var y = 10
    untilNoCurried(y == 0, { y -= 1; println(y) })  // not pretty
  }

  test("the return expression") {
    def until(condition: => Boolean)(block: => Unit): Unit = {
      if (!condition) {
        block
        until(condition)(block)
      }
    }

    def indexOf(str: String, ch: Char): Int = {
      var i = 0
      until (i == str.length) {
        // the enclosing named function indexOf terminates and returns the given value
        if (str(i) == ch) return i
        i += 1
      }
      return -1
    }
    assert(indexOf("Hello", 'e') === 1)
  }
}
