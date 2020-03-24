package lectures.part1basics

object DaveSCBNvsCBV extends App {

  def calledByValue(x: Long): Unit = {
    println("by value: " + x)
    println("by value: " + x)
  }

  //DRS - => operator means x is passed by Name, not by Value
  def calledByName(x: => Long): Unit = {
    println("by name: " + System.nanoTime())
    println("by name: " + System.nanoTime())
  }


  //DRS - CHECK THIS OUT!
  calledByValue(System.nanoTime())  //HERE YOU GET 2 of the same result printed out
  calledByName(System.nanoTime())   //HERE YOU GET 2 DIFFERENT results, since the System.nanoTime() expression is passed literally and evaluated twise
}
