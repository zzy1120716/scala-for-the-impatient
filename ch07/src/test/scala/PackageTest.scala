import java.awt.event.ActionEvent

import org.scalatest.FunSuite

class PackageTest extends FunSuite {

  test("package object") {
    assert(com.zzy.impatient.people.defaultName === "John Q. Public")
  }

  test("import all member of class or object") {
    import java.awt.Color._
    val c1 = RED  // Color.RED
    val c2 = decode("#ff0000")  // Color.decode
    assert(c1.toString === "java.awt.Color[r=255,g=0,b=0]")
    assert(c2.toString === "java.awt.Color[r=255,g=0,b=0]")
  }

  test("import all member of package") {
    import java.awt._
    def handler(evt: event.ActionEvent): event.ActionEvent = { // java.awt.event.ActionEvent
      evt
    }
    assert(handler(new ActionEvent(new Object, 1, "")).getClass.getSimpleName === "ActionEvent")
  }

  test("renaming and hiding members") {
    import java.awt.{Color, Font}
    assert(Color.BLACK.toString === "java.awt.Color[r=0,g=0,b=0]")
    assert(Font.BOLD.toString === "1")

    // rename
    import java.util.{HashMap => JavaHashMap}
    import scala.collection.mutable._

    val m1 = new JavaHashMap[Int, Int]
    val m2 = new HashMap[Int, Int]
    assert(m1.getClass.getName === "java.util.HashMap")
    assert(m2.getClass.getName === "scala.collection.mutable.HashMap")

    // hide
    import java.util.{HashMap => _, _}
    import scala.collection.mutable._
    val m = new HashMap[Int, Int]
    assert(m.getClass.getName === "scala.collection.mutable.HashMap")
  }

  test("implicit import") {
    // scala program implicitly starts with
    import java.lang._
    import scala._
    //import Predef._

    import collection.mutable.HashMap
    //import scala.collection.mutable.HashMap

    assert(new HashMap[Int, Int].getClass.getName === "scala.collection.mutable.HashMap")
  }
}
