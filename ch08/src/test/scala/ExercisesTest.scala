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
    }

    class CheckingAccount(balance: Double) extends BankAccount(balance) {

    }
  }
}
