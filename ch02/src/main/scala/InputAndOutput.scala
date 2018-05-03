object InputAndOutput extends App {
  // Output
  print("Answer: ")
  println(42)
  println("Answer: " + 42)
  printf("Hello, %s! You are %d years old.\n", "Fred", 42)

  // Input
  val name = readLine("Your name: ")
  print("Your age: ")
  val age = readInt()
  printf("Hello, %s! Next year, you will be %d.\n", name, age + 1)
}
