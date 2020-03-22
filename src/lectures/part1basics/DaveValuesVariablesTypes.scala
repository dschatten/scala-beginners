package lectures.part1basics

//extends App allows you to run directly
object DaveValuesVariablesTypes extends App {

  val x: Int = 42
  println(x)

  val y = 43

  //Compiler can also just infer the types
  val aString: String = "something"
  val aBool: Boolean = true
  val aShort: Short = 4444
//  var aShort: Short = 5555555555555555555  //too long for a short type
  var aLong: Long =12345677L //'L' at end for long, like java syntax
  var aFloat: Float = 2.0f //'f' for float
  var aDouble: Double = 2.0
  val aChar: Char = 'a'


}
