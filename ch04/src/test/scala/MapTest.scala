import java.awt.Font

import org.scalatest.FunSuite

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.collection.JavaConverters._
import java.awt.font.TextAttribute._

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
    // update mutable map
    val scoresMutable = collection.mutable.Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)
    scoresMutable("Bob") = 10
    assert(scoresMutable.mkString(", ") === "Bob -> 10, Alice -> 10, Cindy -> 8")
    scoresMutable("Fred") = 7
    assert(scoresMutable.mkString(", ") === "Bob -> 10, Fred -> 7, Alice -> 10, Cindy -> 8")
    scoresMutable += ("Billy" -> 5, "Glenn" -> 4)
    assert(scoresMutable.mkString(", ") === "Bob -> 10, Billy -> 5, Fred -> 7, Glenn -> 4, Alice -> 10, Cindy -> 8")
    scoresMutable -= "Alice"
    assert(scoresMutable.mkString(", ") === "Bob -> 10, Billy -> 5, Fred -> 7, Glenn -> 4, Cindy -> 8")

    // useful ops with immutable map
    val scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)
    val newScores = scores + ("Bob" -> 10, "Fred" -> 7) // new map with update
    assert(newScores.mkString(", ") === "Alice -> 10, Bob -> 10, Cindy -> 8, Fred -> 7")
    var scoresVariable = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)
    scoresVariable = scoresVariable + ("Bob" -> 10, "Fred" -> 7)
    assert(scoresVariable.mkString(", ") === "Alice -> 10, Bob -> 10, Cindy -> 8, Fred -> 7")
    scoresVariable = scoresVariable - "Alice"
    assert(scoresVariable.mkString(", ") === "Bob -> 10, Cindy -> 8, Fred -> 7")
  }

  test("iterating with maps") {
    val scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)
    val scoresReverse = for ((k, v) <- scores) yield (v, k)
    assert(scoresReverse.mkString(", ") === "10 -> Alice, 3 -> Bob, 8 -> Cindy")

    // access keys and values
    assert(scores.keySet === Set("Alice", "Bob", "Cindy"))
    val scoresArr = new ArrayBuffer[Int]
    for (v <- scores.values) scoresArr.append(v)
    assert(scoresArr.sortWith(_>_).mkString(", ") === "10, 8, 3")
  }

  test("sorted maps") {
    val scoresSorted = scala.collection.immutable.SortedMap("Alice" -> 10, "Fred" -> 7, "Bob" -> 3, "Cindy" -> 8)
    assert(scoresSorted.mkString(", ") === "Alice -> 10, Bob -> 3, Cindy -> 8, Fred -> 7")
    val scoresLinked = scala.collection.mutable.LinkedHashMap("January" -> 1, "February" -> 2, "March" -> 3)
    assert(scoresLinked.mkString(", ") === "January -> 1, February -> 2, March -> 3")
  }

  test("interoperating with java") {
    val scores: scala.collection.mutable.Map[String, Int] = new java.util.TreeMap[String, Int].asScala
    assert(scores.getClass.getSimpleName === "JMapWrapper")

    val props: scala.collection.Map[String, String] = System.getProperties.asScala
    assert(props("java.runtime.name") === "Java(TM) SE Runtime Environment")

    val attrs = Map(FAMILY -> "Serif", SIZE -> 12)  // scala map
    val font = new Font(attrs.asJava) // except a java map
    assert(font.getFamily === "Serif")
    assert(font.getSize === 12)
  }
}
