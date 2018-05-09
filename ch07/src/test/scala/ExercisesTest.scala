import org.scalatest.FunSuite

class ExercisesTest extends FunSuite {

  /**
    * Write a program that copies all elements from a Java hash map into a Scala
    * hash map. Use imports to rename both classes.
    */
  test("6") {
    import java.util.{HashMap => JavaHashMap}
    import scala.collection.mutable.{HashMap => ScalaHashMap}
    import scala.collection.JavaConverters._

    def copyJavaHashMap2ScalaHashMap(j: JavaHashMap[Any, Any]): ScalaHashMap[Any, Any] = {
      var s: ScalaHashMap[Any, Any] = new ScalaHashMap[Any, Any]
      //for ((k, v) <- j.asScala) s += (k -> v)
      val i = j.entrySet().iterator()
      while (i.hasNext) {
        val n = i.next
        s += ((n.getKey, n.getValue))
      }
      s
    }

    val jScores = new JavaHashMap[Any, Any]
    jScores.put("Alice", 10)
    jScores.put("Bob", 8)
    val sScores = copyJavaHashMap2ScalaHashMap(jScores)
    assert(jScores.asScala.mkString(", ") === "Bob -> 8, Alice -> 10")
    assert(sScores.mkString(", ") === "Bob -> 8, Alice -> 10")
  }
}
