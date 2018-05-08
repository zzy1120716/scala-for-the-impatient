package com {
  package zzy {

    object Utils {
      def percentOf(value: Double, rate: Double) = value * rate / 100
    }

    package impatient {

      class Employee {

        var salary: Double = 0.0

        def giveRaise(rate: scala.Double): Unit = {
          salary += Utils.percentOf(salary, rate)
        }
      }

    }

  }

}
// contribute to more than one package in a single file
package org {
  package bigjava {
    class Counter {

    }
  }
}