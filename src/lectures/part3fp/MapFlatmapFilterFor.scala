package lectures.part3fp

/**
  * Created by Daniel.
  */
object MapFlatmapFilterFor extends App {

  val list = List(1,2,3)
  println(list.head)
  println(list.tail)

  // map
  println(list.map(_ + 1))
  println(list.map(_ + " is a number"))

  // filter
  println(list.filter(_ % 2 == 0))

  // flatMap
  val toPair = (x: Int) => List(x, x+1)
  println(s"List to flatmap using a simple function: ${list.flatMap(toPair)}")

  // print all combinations between two lists
  val numbers = List(1,2,3,4)
  val chars = List('a','b','c','d')
  val colors = List("black", "white")

  // List("a1", "a2"... "d4")

  //Attempt, not correct
//  val toCombination = (a: Char, b:List[Int]) => List(b.foreach(a.toString().concat(b + " ")))
//  println(s"Attempt to concat: ${chars.flatMap(toCombination(_, numbers))}")

//  chars.foreach()

  // "iterating"
  val combinations = numbers.flatMap(n => chars.flatMap(c => colors.map(color => "" + c + n + "-" + color)))
  val combinations1 = chars.flatMap(c => numbers.map(n => c.toString + n.toString + " "))
  println(s"List combinations: $combinations")
  println(s"List combinations1: $combinations1")   //Yay!    a1, a2, a3, a4, b1, b2, b3....

  // foreach
  list.foreach(println)

  // for-comprehensions
  val forCombinations = for {
    n <- numbers if n % 2 == 0
    c <- chars
    color <- colors
  } yield "" + c + n + "-" + color
  println(forCombinations)

  //DRS - use a for comprehension for combinations1 to make the code more readable
  val forCombinations1 = for {
    c <- chars
    n <- numbers
  }yield c.toString + n.toString + " "
  println(s"DRS List combinations1 using a for comprehension: $forCombinations1")

  for {
    n <- numbers
  } println(n)

  // syntax overload
  list.map { x =>
    x * 2
  }

  /*
    1.  MyList supports for comprehensions?
        map(f: A => B) => MyList[B]
        filter(p: A => Boolean) => MyList[A]
        flatMap(f: A => MyList[B]) => MyList[B]
    2.  A small collection of at most ONE element - Maybe[+T]
        - map, flatMap, filter
   */


}
