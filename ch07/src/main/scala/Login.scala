object Login extends App {
  import java.lang.System._

  val username = getProperty("user.name")
  val password = readLine("Please enter your password: ")

  if (password == "secret") println("Welcome, %s!".format(username))
  else err.println("Wrong password. Please try again...")
}
