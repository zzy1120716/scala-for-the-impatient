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
}
