import org.scalatest.FunSuite

import scala.collection.mutable.ListBuffer

class ExercisesTest extends FunSuite {

  /**
    * Write a function that, given a string, produces a map of the indexes of all
    * characters. For example, indexes("Mississippi") should return a map associating
    * 'M' with the set {0}, 'i' with the set {1, 4, 7, 10}, and so on. Use a mutable map
    * of characters to mutable sets. How can you ensure that the set is sorted?
    */
  test("1") {
    import scala.collection.mutable

    def getCharIdxMap(s: String): mutable.LinkedHashMap[Char, mutable.LinkedHashSet[Int]] = {
      val charIdxMap = mutable.LinkedHashMap[Char, mutable.LinkedHashSet[Int]]()
      for ((c, i) <- s.zipWithIndex) {
        val idxSet = charIdxMap.getOrElse(c, new mutable.LinkedHashSet[Int]())
        idxSet += i
        charIdxMap(c) = idxSet
      }
      charIdxMap
    }

    assert(getCharIdxMap("Mississippi").mkString(", ") === "M -> Set(0), i -> Set(1, 4, 7, 10), s -> Set(2, 3, 5, 6), p -> Set(8, 9)")
  }

  /**
    * Repeat the preceding exercise, using an immutable map of characters to lists.
    */
  test("2") {
    def getCharIdxMap(s: String): Map[Char, List[Int]] = {
      s.zipWithIndex.groupBy(_._1).map(x => (x._1, x._2.map(_._2).toList))
    }

    assert("Mississippi".zipWithIndex.groupBy(_._1).mkString(", ") === "M -> Vector((M,0)), s -> Vector((s,2), (s,3), (s,5), (s,6)), p -> Vector((p,8), (p,9)), i -> Vector((i,1), (i,4), (i,7), (i,10))")
    assert(getCharIdxMap("Mississippi").mkString(", ") === "M -> List(0), s -> List(2, 3, 5, 6), p -> List(8, 9), i -> List(1, 4, 7, 10)")
  }

  /**
    * Write a function that removes every second element from a ListBuffer. Try it
    * two ways.
    * Call remove(i) for all even i starting at the end of the list.
    * Copy every second element to a new list. Compare the performance.
    */
  test("3") {
    var lst = ListBuffer[Int](1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    lst = lst.zipWithIndex collect { case (x, i) if (i + 1) % 2 != 0 => x }
    assert(lst === ListBuffer(1, 3, 5, 7, 9))

    lst = ListBuffer[Int](1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val n = lst.length
    lst.zipWithIndex foreach {
      x => if (x._2 % 2 == 0) lst.remove(n - x._2 - 1)
    }
    assert(lst === ListBuffer(1, 3, 5, 7, 9))
  }

  /**
    * Write a function that receives a collection of strings and a map from strings
    * to integers. Return a collection of integers that are values of the map corresponding
    * to one of the strings in the collection. For example, given Array("Tom",
    * "Fred", "Harry") and Map("Tom" -> 3, "Dick" -> 4, "Harry" -> 5), return Array(3, 5).
    * Hint: Use flatMap to combine the Option values returned by get.
    */
  test("4") {

  }
}
