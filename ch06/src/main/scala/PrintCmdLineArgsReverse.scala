object PrintCmdLineArgsReverse extends App {
  def reverseArgs(args: Array[String]): String = {
    args.reverse.mkString(" ")
  }

  println(reverseArgs(args))
}
