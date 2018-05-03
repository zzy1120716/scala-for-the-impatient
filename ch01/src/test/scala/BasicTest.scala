import org.scalatest.FunSuite
import math._

class BasicTest extends FunSuite {
  test("interpreter") {
    // in scala command line, interpreter will give result a default name called "res0, res1..."
    val res0 = 8 * 5 + 2
    assert(res0 === 42)
    val res1 = 0.5 * res0
    assert(res1 === 21.0)
    val res2 = "Hello," + res0
    assert(res2 === "Hello,42")
    assert(res2.toUpperCase() === "HELLO,42")
    assert(res2.toLowerCase() === "hello,42")
  }

  test("declaring values and variables") {
    val answer = 8 * 5 + 2
    assert(answer === 42)
    assert(0.5 * answer === 21.0)
    // answer = 0  // error: reassignment to val
    var counter = 0
    counter = 1
    assert(counter === 1) // var can be reassigned

    val str: String = "abc"
    assert(str.getClass.getSimpleName === "String")

    val greeting: Any = "Hello"
    assert(greeting.getClass.getSimpleName === "String")

    val xmax, ymax = 100
    var msg1, msg2: String = null
    assert(xmax === 100 && ymax === 100)
    assert(msg1 === null && msg2 === null)
  }

  test("types") {
    assert(1.toString === "1")
    //println(1.to(10))
    assert(1.to(10) === Range(1, 11, 1))
    assert("Hello".intersect("World") === "lo")

    // type conversion
    assert(99.44.toInt === 99)
    assert(99.toChar === 'c')
    assert("99.44".toDouble === 99.44)
  }

  test("arithmetic and operator overloading") {
    val a, b = 1
    assert(a + b === a.+(b))
    assert(1.to(10) === (1 to 10))
    var counter = 0
    // counter++ // cannot resolve symbol
    counter += 1
    assert(counter === 1)

    val x: BigInt = 1234567890
    assert(x * x * x === BigInt("1881676371789154860897069000"))
  }

  test("functions and methods") {
//    println(sqrt(2))
    assert(sqrt(2) === 1.4142135623730951)
    assert(pow(2, 4) === 16.0)
    assert(min(3, Pi) === 3.0)

//    println(BigInt.probablePrime(100, scala.util.Random))
    assert(BigInt.probablePrime(100, scala.util.Random).bitLength === 100)

    assert("Hello".distinct === "Helo")
  }

  test("apply method") {
    assert("Hello"(4) === 'o')
    assert("Hello".apply(4) === 'o')
    assert(BigInt("1234567890") === BigInt.apply("1234567890"))
//    println(BigInt("1234567890") * BigInt("11235811321"))
    assert(BigInt("1234567890") * BigInt("11235811321") === BigInt("13871371875005082690"))
    assert(Array(1, 4, 9, 16) === Array.apply(1, 4, 9, 16))
  }

  test("scala doc") {
    // implicit conversion
    def doSomethingWithBigInt(d: BigInt) = {
      // it works fine even if you provide a Int
      assert(d.getClass.getSimpleName === "BigInt")
    }
    doSomethingWithBigInt(10)

    // function as a parameter
    assert("Hello World!".count(_.isUpper) === 2)

    assert("Harry".patch(1, "ung", 2) === "Hungry")
  }
}
