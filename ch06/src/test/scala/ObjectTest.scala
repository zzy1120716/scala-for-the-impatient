import org.scalatest.FunSuite

class ObjectTest extends FunSuite {

  test("singletons") {
    object Accounts {
      private var lastNumber = 0
      def newUniqueNumber(): Int = { lastNumber += 1; lastNumber }
    }

    assert(Accounts.newUniqueNumber() === 1)
  }

  test("companion objects") {
    class Account {
      val id: Int = Account.newUniqueNumber()
      private var _balance = 0.0
      def deposit(amount: Double) { _balance += amount }
      def withdraw(amount: Double) { if (amount <= _balance) _balance -= amount }
      def balance: Double = _balance
    }

    object Account {
      private var lastNumber = 0
      def newUniqueNumber(): Int = { lastNumber += 1; lastNumber }
    }

    val myAccount = new Account
    myAccount.deposit(100)
    assert(Account.newUniqueNumber() === 2)
    assert(myAccount.balance === 100)
    myAccount.withdraw(50)
    assert(myAccount.balance === 50)
  }

  test("object extending a class or trait") {
    abstract class UndoableAction(val description: String) {
      def undo(): Unit
      def redo(): Unit
    }

    object DoNothingAction extends UndoableAction("Do nothing") {
      override def undo() {}
      override def redo() {}
    }

    val actions = Map("open" -> DoNothingAction, "save" -> DoNothingAction)
    // open and save not yet implemented
    assert(actions.keySet.mkString(", ") === "open, save")
  }

  test("apply method") {
    val log: Array[String] = Array("Mary", "had", "a", "little", "lamb")
    assert(log.mkString(" ") === "Mary had a little lamb")
    assert(log.getClass.getSimpleName === "String[]")

    val twoDArr = Array(Array(1, 7), Array(2, 9))
    val matrix = Array.ofDim[Int](2, 2)
    matrix(0)(0) = 1
    matrix(0)(1) = 7
    matrix(1)(0) = 2
    matrix(1)(1) = 9
    assert(twoDArr(0).mkString(", ") === "1, 7")
    assert(twoDArr(1).mkString(", ") === "2, 9")
    assert(matrix(0).mkString(", ") === "1, 7")
    assert(matrix(1).mkString(", ") === "2, 9")
    assert(twoDArr === matrix)

    val arr1 = Array(100)
    val arr2 = new Array(100)
    assert(arr1.length === 1)
    assert(arr2.length === 100)
    assert(arr1.getClass.getSimpleName === "int[]")
    assert(arr2.getClass.getSimpleName === "Object[]")

    class Account private (val id: Int, initialBalance: Double) {
      private var _balance = initialBalance
      def balance: Double = _balance
      def deposit(amount: Double) { _balance += amount }
      def withdraw(amount: Double) { if (amount <= _balance) _balance -= amount }
      override def toString: String = "User: %d, Balance: %.2f".format(id, balance)
    }

    object Account {  // companion object
      def apply(initialBalance: Double = 0.0) = new Account(newUniqueNumber(), initialBalance)
      private var lastNumber = 0
      def newUniqueNumber(): Int = { lastNumber += 1; lastNumber }
    }

    val acct = Account(1000.0)
    assert(acct.toString === "User: 1, Balance: 1000.00")
  }

  // enumeration definition
  object TrafficLightColor extends Enumeration {
    type TrafficLightColor = Value
    //val Red, Yellow, Green = Value
    val Red: Value = Value(0, "Stop")
    val Yellow: Value = Value(10)   // name is "Yellow"
    val Green: Value = Value("Go")  // ID is 11
  }

  test("enumerations") {
    import TrafficLightColor._

    def doWhat(color: TrafficLightColor): String = {
      if (color === Red) "stop"
      else if (color == Yellow) "hurry up"
      else "go"
    }

    assert(doWhat(Red) === "stop")
    assert(doWhat(Yellow) === "hurry up")
    assert(doWhat(Green) === "go")

    var s = ""
    for (c <- TrafficLightColor.values) s += c.id + ": " + c + "\n"
    assert(s === "0: Stop\n10: Yellow\n11: Go\n")

    assert(TrafficLightColor(0) === Red)
    assert(TrafficLightColor.withName("Stop") === Red)
  }
}
