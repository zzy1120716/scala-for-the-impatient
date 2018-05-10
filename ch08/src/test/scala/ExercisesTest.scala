import org.scalatest.FunSuite

class ExercisesTest extends FunSuite {

  /**
    * Extend the following BankAccount class to a CheckingAccount class that charges $1
    * for every deposit and withdrawal.
    * class BankAccount(initialBalance: Double) {
    *   private var balance = initialBalance
    *   def currentBalance = balance
    *   def deposit(amount: Double) = { balance += amount; balance }
    *   def withdraw(amount: Double) = { balance -= amount; balance }
    * }
    */
  test("1") {
    class BankAccount(initialBalance: Double) {
      private var balance = initialBalance
      def currentBalance: Double = balance
      def deposit(amount: Double): Double = { balance += amount; balance }
      def withdraw(amount: Double): Double = { balance -= amount; balance }
      override def toString: String = s"Balance = $balance"
    }

    class CheckingAccount(initialBalance: Double, val charge: Double = 1.0) extends BankAccount(initialBalance) {
      override def deposit(amount: Double): Double = super.deposit(amount - charge)
      override def withdraw(amount: Double): Double = super.withdraw(amount + charge)
    }

    val myAccount = new BankAccount(100.0)
    myAccount.deposit(100.0)
    assert(myAccount.currentBalance === 200.0)
    myAccount.withdraw(100.0)
    assert(myAccount.currentBalance === 100.0)

    val myAccountChecking = new CheckingAccount(100.0, 2.0)
    myAccountChecking.deposit(100.0)
    assert(myAccountChecking.currentBalance === 198.0)
    myAccountChecking.withdraw(100.0)
    assert(myAccountChecking.currentBalance === 96.0)
  }

  /**
    * Extend the BankAccount class of the preceding exercise into a class SavingsAccount
    * that earns interest every month (when a method earnMonthlyInterest is called) and
    * has three free deposits or withdrawals every month. Reset the transaction count in
    * the earnMonthlyInterest method.
    */
  test("2") {
    class BankAccount(initialBalance: Double) {
      private var balance = initialBalance
      def currentBalance: Double = balance
      def deposit(amount: Double): Double = { balance += amount; balance }
      def withdraw(amount: Double): Double = { balance -= amount; balance }
      override def toString: String = s"Balance = $balance"
    }

    class SavingsAccount(initialBalance: Double, val interestRateYear: Double = 0.1, val freeTransactions: Int = 3, val charge: Double = 1.0) extends BankAccount(initialBalance) {

      var transactionInMonth = 0

      def isFreeTransaction: Boolean = transactionInMonth <= freeTransactions

      override def deposit(amount: Double): Double = {
        transactionInMonth += 1
        super.deposit(amount - (if (isFreeTransaction) 0 else charge))
      }

      override def withdraw(amount: Double): Double = {
        transactionInMonth += 1
        super.withdraw(amount + (if (isFreeTransaction) 0 else charge))
      }

      def earnMonthlyInterest: Double = {
        transactionInMonth = 0
        super.deposit(currentBalance * interestRateYear / 12)
      }
    }

    val myAccount = new SavingsAccount(1000.0, 0.10, 3, 2.0)
    // free transactions
    myAccount.deposit(100.0)
    assert(myAccount.currentBalance === 1100.0)
    myAccount.deposit(100.0)
    assert(myAccount.currentBalance === 1200.0)
    myAccount.withdraw(200.0)
    assert(myAccount.currentBalance === 1000.0)
    // charges $2 per transaction
    myAccount.deposit(100.0)
    assert(myAccount.currentBalance === 1098.0)
    myAccount.withdraw(100.0)
    assert(myAccount.currentBalance === 996.0)
    // earn interest at the end of the month
    myAccount.earnMonthlyInterest
    assert(myAccount.currentBalance === 1004.3)
    // free again
    myAccount.deposit(100.0)
    myAccount.withdraw(100.0)
    assert(myAccount.currentBalance === 1004.3)
  }

  /**
    * Consult your favorite Java or C++ textbook which is sure to have an example of
    * a toy inheritance hierarchy, perhaps involving employees, pets, graphical shapes,
    * or the like. Implement the example in Scala.
    */
  test("3") {
    abstract class Shape {
      val name = "Abstract shape"
      override def toString: String = name
    }

    trait Drawable {
      def draw: String = toString
    }

    class Point(val x: Int, val y: Int) {
      override def toString: String = s"($x, $y)"
    }

    object Point {
      def apply(x: Int = 0, y: Int = 0) = new Point(x, y)
    }

    class Rectangle(val topLeft: Point, val bottomRight: Point) extends Shape with Drawable {
      override val name = "Rectangle"
      override def toString: String = s"$name[${topLeft.toString}, ${bottomRight.toString}]"
    }

    class Circle(val center: Point, val radius: Int) extends Shape with Drawable {
      override val name = "Circle"
      override def toString: String = s"$name[${center.toString}, $radius]"
    }

    val shapes: Array[Shape with Drawable] = Array(
      new Rectangle(Point(1, 1), Point(10, 20)),
      new Circle(Point(3, 3), 5))
    assert(shapes(0).draw === "Rectangle[(1, 1), (10, 20)]")
    assert(shapes(1).draw === "Circle[(3, 3), 5]")
  }

  /**
    * Define an abstract class Item with methods price and description. A SimpleItem is
    * an item whose price and description are specified in the constructor. Take
    * advantage of the fact that a val can override a def. A Bundle is an item that
    * contains other items. Its price is the sum of the prices in the bundle. Also
    * provide a mechanism for adding items to the bundle and a suitable description
    * method.
    */
  test("4") {
    abstract class Item {
      def price: Double
      def description: String
      override def toString: String = s"${getClass.getSimpleName}[$description, $price]"
    }
    class SimpleItem(override val price: Double, override val description: String) extends Item {}
    class Bundle extends Item {
      private var items: List[Item] = List()
      def add(item: Item): Unit = { items = item :: items }
      def add(itemList: List[Item]): Unit = { items = itemList ::: items }
      def description: String = items.map(_.description).mkString(", ")
      /*{
        var s = ""
        items foreach { item =>
          s += item.description
        }
        s
      }*/
      def price: Double = items.map(_.price).sum
      /*{
        var sum = 0.0
        items foreach { item =>
          sum += item.price
        }
        sum
      }*/
    }
    val l: List[Item] = List(
      new SimpleItem(2000, "Retina MacBook Pro 15' 2016"),
      new SimpleItem(1500, "iPhone X")
    )
    val bundle = new Bundle
    val iPad = new SimpleItem(800, "iPad Pro 2018")
    bundle.add(iPad)
    bundle.add(new SimpleItem(600, "iPhone 8p"))
    bundle.add(l)
    assert(iPad.toString === "SimpleItem$1[iPad Pro 2018, 800.0]")
    assert(bundle.price === 4900.0)
    assert(bundle.description === "Retina MacBook Pro 15' 2016, iPhone X, iPhone 8p, iPad Pro 2018")
  }

  /**
    * Design a class Point whose x and y coordinate values can be provided in a
    * constructor. Provide a subclass LabeledPoint whose constructor takes a label
    * value and x and y coordinates, such as
    *   new LabeledPoint("Black Thursday", 1929, 230.07)
    */
  test("5") {
    class Point(x: Double, y: Double) {
      override def toString: String = s"Point($x, $y)"
    }
    class LabeledPoint(label: String, x: Double, y: Double) extends Point(x, y) {
      override def toString: String = s"LabeledPoint($label, $x, $y)"
    }
    val a = new Point(1, 1)
    val b = new LabeledPoint("Black Thursday", 1929, 230.07)
    assert(a.toString === "Point(1.0, 1.0)")
    assert(b.toString === "LabeledPoint(Black Thursday, 1929.0, 230.07)")
  }

  /**
    * Define an abstract class Shape with an abstract method centerPoint and subclasses
    * Rectangle and Circle. Provide appropriate constructors for the subclasses and
    * override the centerPoint method in each subclass.
    */
  test("6") {
    class Point(val x: Double, val y: Double) {
      override def toString: String = s"Point($x, $y)"
    }
    abstract class Shape {
      def centerPoint: Point
    }
    class Rectangle(val topLeft: Point, val bottomRight: Point) extends Shape {
      override def centerPoint: Point = new Point((topLeft.x + bottomRight.x) / 2, (topLeft.y + bottomRight.y) / 2)
    }
    class Circle(override val centerPoint: Point, val radius: Double) extends Shape
    val r = new Rectangle(new Point(0, 0), new Point(2, 2))
    val c = new Circle(new Point(3, 3), 5)
    assert(r.centerPoint.toString === "Point(1.0, 1.0)")
    assert(c.centerPoint.toString === "Point(3.0, 3.0)")
  }

  /**
    * Provide a class Square that extends java.awt.Rectangle and has three constructors:
    * one that constructs a square with a given corner point and width, one
    * that constructs a square with corner (0, 0) and a given width, and one that
    * constructs a square with corner (0, 0) and width 0.
    */
  test("7") {
    class Square(x: Int, y: Int, width: Int) extends java.awt.Rectangle(x, y, width, width) {
      def this() = this(0, 0, 0)
      def this(width: Int) = this(0, 0, width)
      override def toString: String = s"Square($x, $y, $width)"
    }
    val sqr1 = new Square()
    val sqr2 = new Square(1)
    val sqr3 = new Square(1, 2, 3)
    assert(sqr1.toString === "Square(0, 0, 0)")
    assert(sqr2.toString === "Square(0, 0, 1)")
    assert(sqr3.toString === "Square(1, 2, 3)")
  }

  /**
    * Compile the Person and SecretAgent classes in Section 8.6, “Overriding Fields,”
    * on page 95 and analyze the class files with javap. How many name fields are
    * there? How many name getter methods are there? What do they get? (Hint:
    * Use the -c and -private options.)
    */
  test("8") {
    class Person(val name: String) {
      override def toString = s"Person[name=$name]"
    }
    class SecretAgent(codename: String) extends Person(codename) {
      override val name = "secret"
      override val toString = "secret"
    }
    val bond = new Person("James Bond")
    val ukAgent = new SecretAgent("007")
    assert(bond.toString === "Person[name=James Bond]")
    assert(ukAgent.toString === "secret")
  }

  /**
    * In the Creature class of Section 8.10, “Construction Order and Early Definitions,”
    * on page 98, replace val range with a def. What happens when you also use a def
    * in the Ant subclass? What happens when you use a val in the subclass? Why?
    */
  test("9") {
    class Creature {
      //val range: Int = 10
      def range: Int = 10
      val env: Array[Int] = new Array[Int](range)
    }
    class Ant extends Creature {
      //override val range = 2
      override def range = 2
    }
    val ant = new Ant
    assert(ant.env.length === 2)
  }

  /**
    * The file scala/collection/immutable/Stack.scala contains the definition
    *   class Stack[A] protected (protected val elems: List[A])
    * Explain the meanings of the protected keywords. (Hint: Review the discussion
    * of private constructors in Chapter 5.)
    */
  test("10") {
    class Person protected (protected val name: String) {
      private var age: Int = _
      def this(name: String, age: Int) {
        this(name)
        this.age = age
      }
    }
    class Manager(name: String) extends Person(name) {}
    val p1 = new Person("Fred", 42)
    //val p2 = new Person("Peter")  // error
    // a protected constructor can only be accessed by
    // an auxiliary constructor or descendant class
    val p3 = new Manager("Peter")
    assert(p3.isInstanceOf[Person])
    assert(p1.isInstanceOf[Person])
  }

  /**
    * Define a value class Point that packs integer x and y coordinates into a Long
    * (which you should make private).
    */
  test("11") {
    val p1 = Point(1000000001)
    val p2 = Point(999999999999999999L)
    val p3 = Point(123456789987654321L)
    val p4 = Point(56789000004321L)
    intercept[IllegalArgumentException] {
      Point(-1)
    }
    assert(p1.toString === "Point(1, 1)")
    assert(p2.toString === "Point(999999999, 999999999)")
    assert(p3.toString === "Point(123456789, 987654321)")
    assert(p4.toString === "Point(56789, 4321)")
  }
}
