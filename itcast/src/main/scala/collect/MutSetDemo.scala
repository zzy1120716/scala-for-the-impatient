package collect

import scala.collection.mutable

/**
  * Created by zzy on 2019/9/6
  */
object MutSetDemo {
  def main(args: Array[String]): Unit = {
    // 创建一个可变的HashSet
    val set1 = new mutable.HashSet[Int]()
    // 向HashSet中添加元素
    set1 += 2
    println(s"set1=$set1")  // 2
    // add等价于+=
    set1.add(4)
    println(s"set1=$set1")  // 2, 4
    set1 ++= Set(1, 3, 5)   // 2, 4, 1, 3, 5
    println(s"set1=$set1")
    // 删除一个元素
    set1 -= 5
    println(s"set1=$set1")  // 2, 4, 1, 3
    set1.remove(2)
    println(s"set1=$set1")  // 4, 1, 3
  }
}
