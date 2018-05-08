package com {
  package zzy {
    package impatient {
      class Manager {
        // no longer compiles
        //val subordinates = new collection.mutable.ArrayBuffer[Employee]

        // use top-level scala package
        //val subordinates = new scala.collection.mutable.ArrayBuffer[Employee]

        // absolute package name
        val subordinates = new _root_.scala.collection.mutable.ArrayBuffer[Employee]
      }
    }
  }
}
