package oop

/**
  * 伴生对象举例
  * Created by zzy on 2019/9/6
  */
class Dog {
  val id = 1
  private var name = "itcast"

  def printName(): Unit = {
    // 在Dog类中可以访问伴生对象Dog的私有属性
    println(Dog.CONSTANT + name)
  }
}

/**
  * 伴生对象
  */
object Dog {
  // 伴生对象中的私有属性
  private val CONSTANT = "汪汪汪"

  def main(args: Array[String]): Unit = {
    val p = new Dog
    // 访问私有的字段name
    p.name = "123"
    p.printName()
  }
}