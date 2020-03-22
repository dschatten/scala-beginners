package lectures.part1basics

object DaveExpressions extends App {

  println(2 + 3 * 6)
  println(1 == 2)

  //Instructions vs Expressions
  //If statement in scala evaluates to a value(s)
  val aCondition = if (1 == 2)  true else false

  //There are loops, but they are discouraged in Scala - more imperative
  //DON'T WRITE WHILE LOOP CODE - AVOID IMPERATIVE CODE
  var i = 0
  while (i < 10){
    println(i)
    i += 1
  }

  //side effects: println(), whiles, reassigning
  //AVOID SIDE EFFECTS - these are imperative constructs

  //Code Blocks
  //Code blocks are also expressions
  val aCodeBlock = {
    val y = 2
    val z = y + 1

    //if(z > 2) "hello" : else "goodbye"

  }

  //z is not visible, only in the code block
  //val anotherValue = z + 1

}
