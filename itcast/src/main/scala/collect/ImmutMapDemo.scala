package collect

/**
  * Created by zzy on 2019/9/6
  */
object ImmutMapDemo {
  def main(args: Array[String]): Unit = {
    // zip拉链操作，将单一类型的数组组合成tuple数组
    val names = Array("tom", "jerry", "jason", "kitty")
    val scores = Array(90, 95, 88, 99)
    // 直接转换为map
    val ns = names.zip(scores).toMap
    println(ns)
    println(ns("jason"))
//    println(ns("dr.who")) // error
    println(ns.getOrElse("dr.who", 0))
//    ns("dr.who") = 100
//    ns("jason") = 80  // error
    println(ns)

    val m1 = Map(("a", 1), ("b", 2))
    println(m1)

    val m2 = Map("c" -> 3, "d" -> 4)
    println(m2)
  }
}
