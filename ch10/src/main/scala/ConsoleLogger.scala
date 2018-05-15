trait ConsoleLogger extends Logger {
  var name = "ConsoleLogger"
  override def log(msg: String) { println(msg) }  // implements method log
}