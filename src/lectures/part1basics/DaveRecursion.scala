package lectures.part1basics

import scala.annotation.tailrec

object DaveRecursion extends App{

  //DRS - Trouble is this crashes with larger numbers
  //e.g. factorial(5000) will blow up due to too much junk on the call stack
  //Tailrec annotation doesn't work for this method, you'll get a compiler error
//  @tailrec
  def factorial(n: Int): Int =
    if (n <= 1) 1
    else {
      println("Computing factorial of " + n + " - I first need factorial of " + (n-1))
      val result = n * factorial(n-1)
      println("Computed factorial of " + n)

      result
    }

  //DRS - This does not crash the call stack!
  //Because it is tail recursive, where the recursion is the last statement of the method
  def anotherFactorial(n: Int): BigInt = {
    @tailrec
    def factHelper(x: Int, accumulator: BigInt): BigInt =
      if (x <= 1) accumulator
      else factHelper(x - 1, x * accumulator) //DRS TAIL RECURSION = use recursive call as the LAST expression

    factHelper(n, 1)
  }

}
