import org.scalatest.FunSuite

class TupleTest extends FunSuite {
  test("tuple") {
    assert((1, 3.14, "Fred").getClass.getSimpleName === "Tuple3")
    val t = (1, 3.14, "Fred")
    assert(t._2 === 3.14)

    // pattern matching
    val (first, second, third) = t
    assert(first === 1)
    assert(second === 3.14)
    assert(third === "Fred")

    val (firstPartial, secondPartial, _) = t
    assert(firstPartial === 1)
    assert(secondPartial === 3.14)

    // tuple as a return value
    assert("New York".partition(_.isUpper) === ("NY", "ew ork"))
  }

  test("zipping") {
    val symbols = Array("<", "-", ">")
    val counts = Array(2, 10, 2)
    val pairs = symbols.zip(counts)
    assert(pairs.mkString(", ") === "(<,2), (-,10), (>,2)")

    var res = ""
    for ((s, n) <- pairs) res += s * n
    assert(res === "<<---------->>")

    val students = Set("Alice", "Bob", "Cindy")
    val scores = Set("10", "3", "8")
    val scoresMap = students.zip(scores).toMap
    assert(scoresMap.mkString(", ") === "Alice -> 10, Bob -> 3, Cindy -> 8")
  }
}
