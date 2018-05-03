import org.scalatest.FunSuite

class SuiteBasicTest extends FunSuite {
  test("suite basic test") {
    //  assert(1 == 2)
    //  assert(1 === 2)
    val s = "hi"
    val thrown = intercept[IndexOutOfBoundsException] {
      s.charAt(-1)
    }
    assert(thrown.getMessage === "String index out of range: -1")
  }
}
