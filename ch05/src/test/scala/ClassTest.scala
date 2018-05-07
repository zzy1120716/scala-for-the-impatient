import java.io.FileReader
import java.util.Properties

import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer

class ClassTest extends FunSuite {

  test("simple classes and parameterless method") {
    class Counter {
      private var value = 0 // you must initialize the field
      def increment() { value += 1 } // methods are public by default
      def current = value
    }

    val myCounter = new Counter
    myCounter.increment()
    assert(myCounter.current === 1)
  }

  test("properties with getters and setters") {
    class Person {
      var age = 0
    }
    val fred = new Person
    assert(fred.age === 0)  // calls the method fred.age()
    fred.age = 21 // calls fred.age_=(21)
    assert(fred.age === 21)
  }

  test("private properties") {
    class Person {
      private var privateAge = 0

      def age = privateAge
      def age_=(newValue: Int): Unit = {
        if (newValue > privateAge) privateAge = newValue  // can’t get younger
      }
    }
    val fred = new Person
    fred.age = 30
    assert(fred.age === 30)
    fred.age = 21
    assert(fred.age === 30)
  }

  test("properties with only getters") {
    class Message {
      val timeStamp = new java.util.Date
    }

    class Counter {
      private var value = 0
      def increment() { value += 1 }
      def current = value // no () in declaration
    }

    val myCounter = new Counter
    assert(myCounter.current === 0)
  }

  test("object-private fields") {
    class Counter {
      private var value = 0
      def increment() { value += 1 }
      def isLess(other: Counter) = value < other.value
      // can access private field of other object
    }
    val myCounter = new Counter
    val otherCounter = new Counter
    assert(myCounter.isLess(otherCounter) === false)

    class CounterWithPrivateValue {
      private[this] var value = 0 // accessing someObject.value is not allowed
    }
    // val myCounterWithPrivateValue = new Counter
    // myCounterWithPrivateValue.value // can't be accessed
  }

  test("Bean properties") {
    import scala.beans.BeanProperty

    class Person {
      @BeanProperty var name: String = _
      /*name: String
      name_=(newValue: String): Unit
      getName: String
      setName(newValue: String): Unit*/
    }
    val fred = new Person
    fred.setName("Fred")
    assert(fred.getName === "Fred")
  }

  test("auxiliary constructor") {
    class Person {
      private var name = ""
      private var age = 0

      def this(name: String) {  // an auxiliary constructor
        this()  // calls primary constructor
        this.name = name
      }

      def this(name: String, age: Int) {  // another auxiliary constructor
        this(name)  // calls previous auxiliary constructor
        this.age = age
      }

      def getName: String = name
      def getAge: Int = age
    }
    val p1 = new Person // primary constructor
    val p2 = new Person("Fred") // first auxiliary constructor
    val p3 = new Person("Fred", 42) // second auxiliary constructor
    assert(p1.getName === "" && p1.getAge === 0)
    assert(p2.getName === "Fred" && p2.getAge === 0)
    assert(p3.getName === "Fred" && p3.getAge === 42)
  }

  test("primary constructor") {
    class Person(var name: String = "", var age: Int = 0) {
      println("Just constructed another person")
      def description: String = name + " is " + age + " years old"
    }

    class MyProg {
      private val props = new Properties
      props.load(new FileReader("myprog.properties"))
      // the statement above is a part of the primary constructor
    }

    val p = new Person("Fred", 42)
    assert(p.description === "Fred is 42 years old")

    class Person2(name: String, age: Int) {
      // if a parameter without val or var is used inside
      // at least one method, it becomes a field.
      def description = name + " is " + age + " years old"
    }

    // to make the primary constructor private
    class Person3 private (val id: Int) {}
  }

  test("nested classes") {

    class Network {

      class Member(val name: String) {
        val contacts = new ArrayBuffer[Member]
      }

      private val members = new ArrayBuffer[Member]

      def getMembers = members

      def join(name: String): Member = {
        val m = new Member(name)
        members += m
        m
      }
    }

    val chatter = new Network
    val myFace = new Network

    val fred = chatter.join("Fred")
    val wilma = chatter.join("Wilma")
    fred.contacts += wilma  // OK
    val barney = myFace.join("Barney")  // has type myFace.Member
    //fred.contacts += barney
    // No—can’t add a myFace.Member to a buffer of chatter.Member elements

    val namesChatter = new ArrayBuffer[String]
    chatter.getMembers foreach { member =>
      namesChatter.append(member.name)
    }
    assert(namesChatter.mkString(", ") === "Fred, Wilma")

    val namesMyFace = new ArrayBuffer[String]
    myFace.getMembers foreach { member =>
      namesMyFace.append(member.name)
    }
    assert(namesMyFace.mkString(", ") === "Barney")

    val namesContacts = new ArrayBuffer[String]
    fred.contacts foreach { member =>
      namesContacts.append(member.name)
    }
    assert(namesContacts.mkString(", ") === "Wilma")
  }

  test("companion object") {
    object Network {
      class Member(val name: String) {
        val contacts = new ArrayBuffer[Member]
      }
    }

    class Network {
      private val members = new ArrayBuffer[Network.Member]

      def getMembers = members

      def join(name: String): Network.Member = {
        val m = new Network.Member(name)
        members += m
        m
      }
    }

    val chatter = new Network
    val myFace = new Network

    val fred = chatter.join("Fred")
    val wilma = chatter.join("Wilma")
    fred.contacts += wilma  // OK
    val barney = myFace.join("Barney")  // has type myFace.Member
    fred.contacts += barney

    val namesChatter = new ArrayBuffer[String]
    chatter.getMembers foreach { member =>
      namesChatter.append(member.name)
    }
    assert(namesChatter.mkString(", ") === "Fred, Wilma")

    val namesMyFace = new ArrayBuffer[String]
    myFace.getMembers foreach { member =>
      namesMyFace.append(member.name)
    }
    assert(namesMyFace.mkString(", ") === "Barney")

    val namesContacts = new ArrayBuffer[String]
    fred.contacts foreach { member =>
      namesContacts.append(member.name)
    }
    assert(namesContacts.mkString(", ") === "Wilma, Barney")
  }

  test("type projection") {
    class Network {
      class Member(val name: String) {
        val contacts = new ArrayBuffer[Network#Member]  // type projection
      }

      private val members = new ArrayBuffer[Member]

      def getMembers = members

      def join(name: String): Member = {
        val m = new Member(name)
        members += m
        m
      }
    }

    val chatter = new Network
    val myFace = new Network

    val fred = chatter.join("Fred")
    val wilma = chatter.join("Wilma")
    fred.contacts += wilma  // OK
    val barney = myFace.join("Barney")  // has type myFace.Member
    fred.contacts += barney

    val namesChatter = new ArrayBuffer[String]
    chatter.getMembers foreach { member =>
      namesChatter.append(member.name)
    }
    assert(namesChatter.mkString(", ") === "Fred, Wilma")

    val namesMyFace = new ArrayBuffer[String]
    myFace.getMembers foreach { member =>
      namesMyFace.append(member.name)
    }
    assert(namesMyFace.mkString(", ") === "Barney")

    val namesContacts = new ArrayBuffer[String]
    fred.contacts foreach { member =>
      namesContacts.append(member.name)
    }
    assert(namesContacts.mkString(", ") === "Wilma, Barney")
  }

  test("enclosing class") {
    class Network(val name: String) { outer =>
      class Member(val name: String) {
        val contacts = new ArrayBuffer[Member]

        def description = name + " inside " + outer.name
      }
    }

    val chatter = new Network("Chatter")
    val fred = new chatter.Member("Fred")
    assert(fred.description === "Fred inside Chatter")
  }
}
