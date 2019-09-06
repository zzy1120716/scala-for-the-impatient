package collect

import scala.collection.immutable.HashSet

/**
  * Created by zzy on 2019/9/6
  */
object ImmutSetDemo {
  def main(args: Array[String]): Unit = {
    val set1 = new HashSet[Int]()
    println(s"set1=$set1")
    // 将元素和set1合并生成一个新的set，原有set不变
    val set2 = set1 + 4
    println(s"set2=$set2")
    // set中元素不能重复
    val set3 = set1 ++ Set(5, 6, 7)
    println(s"set3=$set3")
    val set0 = Set(1, 3, 4) ++ set1
    println(s"set0=$set0")
    println(set0.getClass)
  }
}
