import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer

class RegExpTest extends FunSuite {

  test("regular expressions") {
    val numPattern = "[0-9]+".r
    val wsnumwsPattern = """\s+[0-9]+\s+""".r
    val bottleNum = new ArrayBuffer[Int]
    for (matchString <- numPattern.findAllIn("99 bottles, 98 bottles")) bottleNum.append(matchString.toInt + 1)
    assert(bottleNum === ArrayBuffer(100, 99))

    val matches = numPattern.findAllIn("99 bottles, 98 bottles").toArray
    assert(matches === Array("99", "98"))

    val firstMatch = wsnumwsPattern.findFirstIn("99 bottles, 98 bottles")
    assert(firstMatch === Some(" 98 "))

    // add anchors, match entire string
    val anchoredPattern = "^[0-9]+$".r
    var str = ""
    if (anchoredPattern.findFirstIn(str).isEmpty) str = "001" + str
    assert(str === "001")
    // alternatively
    if (str.matches("[0-9]+")) str += ", qualified"
    assert(str === "001, qualified")

    assert(numPattern.findPrefixOf("99 bottles, 98 bottles") === Some("99"))
    assert(wsnumwsPattern.findPrefixOf("99 bottles, 98 bottles") === None)

    // replace
    assert(numPattern.replaceFirstIn("99 bottles, 98 bottles", "XX") === "XX bottles, 98 bottles")
    assert(numPattern.replaceAllIn("99 bottles, 98 bottles", "XX") === "XX bottles, XX bottles")
    assert(numPattern.replaceSomeIn("99 bottles, 98 bottles",
      m => if (m.matched.toInt % 2 == 0) Some("XX") else None) === "99 bottles, XX bottles")

    // replace placeholder with replaceSomeIn
    val varPattern = """\$[0-9]+""".r
    def format(message: String, vars: String*) =
      varPattern.replaceSomeIn(message, m => vars.lift(m.matched.tail.toInt))
    // vars.lift(i) is Some(vars(i)) if i is a valid index or None if it is not.
    val msg = format("At $1, there was $2 on $0.", "planet 7", "12:30 pm", "a disturbance of the force")
    assert(msg === "At 12:30 pm, there was a disturbance of the force on planet 7.")
  }

  test("regular expression groups") {
    val numItemPattern = "([0-9]+) ([a-z]+)".r
    val nums = ArrayBuffer[String]()
    for (m <- numItemPattern.findAllMatchIn("99 bottles, 98 bottles"))
      nums.append(m.group(1))
    assert(nums === ArrayBuffer("99", "98"))

    // retrieving groups by name
    val numItemNamePattern = "([0-9]+) ([a-z]+)".r("num", "item")
    nums.clear()
    for (m <- numItemNamePattern.findAllMatchIn("99 bottles, 98 bottles"))
      nums.append(m.group("item"))
    assert(nums === ArrayBuffer("bottles", "bottles"))

    // extractor
    val numItemPattern(num, item) = "99 bottles"
    assert(num === "99")
    assert(item === "bottles")

    // extract groups from multiple matches
    var numSet = Set[Int]()
    var itemSet = Set[String]()
    for (numItemPattern(num, item) <- numItemPattern.findAllIn("99 bottles, 98 bottles")) {
      numSet = numSet + num.toInt; itemSet = itemSet + item
    }
    assert(numSet === Set(99, 98))
    assert(itemSet === Set("bottles"))
  }
}
