import org.scalatest.FunSuite

class BasicTest extends FunSuite {
  test("basic test") {
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
}
