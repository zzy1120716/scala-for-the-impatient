package com.zzy.impatient

package object random {
  var seed: Int = _
  val a = BigDecimal(1664525)
  val b = BigDecimal(1013904223)
  val n = 32

  def setSeed(value: Int): Unit = seed = value

  def nextInt(): Int = {
    seed = ((seed * a + b) % BigDecimal(2).pow(n)).toInt
    seed
  }

  def nextDouble() : Double = {
    val temp = (seed * a + b) % BigDecimal(2).pow(n)
    seed = temp.toInt
    temp.toDouble
  }
}

package random {
  object Random extends App {
    Predef println random.nextDouble()
    Predef println random.nextDouble()
    Predef println random.nextDouble()

    setSeed((System.currentTimeMillis() / 1000).toInt)

    println(seed)
    (1 to 5).foreach(_ => println(nextInt()))
    (1 to 5).foreach(_ => println(nextDouble()))
  }
}

