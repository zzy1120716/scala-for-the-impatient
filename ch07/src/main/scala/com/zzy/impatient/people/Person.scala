package com.zzy.impatient {

  import com.zzy.impatient.people.Person
  // members of com and com.zzy are not visible here

  class Company {
    val p =  new Person
    //def say = println(p.description)  // not accessible
    def say = println(p.descriptionMoreVisible)
  }

  // package object
  package object people {
    val defaultName = "John Q. Public"
  }

  package people {

    class Person {
      var name: String = defaultName  // a constant from the package

      // package visibility
      private[people] def description = "A person with name " + name

      private[impatient] def descriptionMoreVisible = "A person with name " + name

      private def say = println(description)
      // ...
      // until the end of the file
    }

    class Colleague {
      val p = new Person
      def say = println(p.description)
    }
  }
}