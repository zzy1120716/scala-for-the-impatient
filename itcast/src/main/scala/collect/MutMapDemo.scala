package collect

import scala.collection.mutable

/**
  * Created by zzy on 2019/9/6
  */
object MutMapDemo {
  def main(args: Array[String]): Unit = {
    val map1 = new mutable.HashMap[String, Int]()
    // 向map中添加数据
    map1("spark") = 1
    map1 += (("hadoop", 2))
    map1.put("storm", 3)
    println(map1)

    // 从map中移除元素
    map1 -= "spark"
    map1.remove("hadoop")
    println(map1)

    val pair = ("t", 5)
    map1 += pair
    println(map1)

    map1 += (("y", 6), ("z", 3))
    println(map1)
  }
}
