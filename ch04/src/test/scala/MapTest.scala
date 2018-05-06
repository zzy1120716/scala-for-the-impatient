import org.scalatest.FunSuite

import scala.collection.mutable

class MapTest extends FunSuite {

  test("constructing a map") {
    val scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8) // immutable map
    val scoresMutable = collection.mutable.Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)
    val scoresBlank = new mutable.HashMap[String, Int]
    // another way to definite map
    val scoresParentheses = Map(("Alice", 10), ("Bob", 3), ("Cindy", 8))

    assert("Alice" -> 10 === ("Alice", 10))
    assert(scores.mkString(", ") === "Alice -> 10, Bob -> 3, Cindy -> 8")
    assert(scoresMutable.mkString(", ") === "Bob -> 3, Alice -> 10, Cindy -> 8")
    assert(scoresBlank.mkString(", ") === "")
    assert(scoresParentheses.mkString(", ") === "Alice -> 10, Bob -> 3, Cindy -> 8")
  }

  test("accessing map values") {
    val scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8) // immutable map
    val bobsScore = scores("Bob")
    assert(bobsScore === 3)
    intercept[NoSuchElementException] { // when map doesn't contain a value
      val amysScore = scores("Amy")
    }

    // check
    val alicesScore = if (scores.contains("Alice")) scores("Alice") else 0
    assert(alicesScore === 10)
    // shortcut of check
    val cindysScore = scores.getOrElse("Cindy", 0)
    assert(cindysScore === 8)

    // get
    val bob = scores.get("Bob")
    assert(bob.getClass.getSimpleName === "Some")
    val amy = scores.get("Amy")
    assert(amy.getClass.getSimpleName === "None$")
  }

  test("update map values") {

  }
}
