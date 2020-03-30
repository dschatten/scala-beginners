package exercises

import exercises.ListTest.listOfIntegers

//TODO: DRS: In this lecture we convert FunctionX calls with lambdas - Lecture 25.    Also added Lecture 26.
//and rewrite the 'special' adder as an anonymous function

abstract class DListV5 [+A]{

  def head: A
  def tail: DListV5[A]
  def isEmpty: Boolean
  def add[B >:A](element: B): DListV5[B]


  //DRS - We've just made these into higher order functions
  def map[B](transformer: A => B) : DListV5[B]
  def flatMap[B](transformer: A => DListV5[B]): DListV5[B]
  def filter(predicate: A => Boolean) : DListV5[A]

  override def toString: String = super.toString

  // concatenation
  def ++[B >: A](list: DListV5[B]): DListV5[B]

  def forEach(f: A => Unit)
}

//Define as object, doesn't need to be instantiated
case object DEmptyListV5 extends DListV5[Nothing]{
  def head: Nothing = throw new NoSuchElementException
  def tail: DListV5[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): DListV5[B] = new DConsV5(element, DEmptyListV5)

  override def toString: String = " "


  //The one thing that's not confusing is that each of these methods in this class just return an empty list..think I got that
  def map[B](transformer: Nothing => B) : DListV5[B] = DEmptyListV5
  def flatMap[B](transformer: Nothing => DListV5[B]): DListV5[B]= DEmptyListV5
  def filter(predicate: Nothing => Boolean) : DListV5 [Nothing]= DEmptyListV5

  def ++[B >: Nothing](list: DListV5[B]): DListV5[B] = list
  def forEach(f: Nothing => Unit) = DEmptyListV5
}

case class DConsV5[+A](h: A, t: DListV5[A]) extends DListV5[A]{
  def head: A = h
  def tail: DListV5[A] = t
  def isEmpty: Boolean = false
  def add[B >:A](element: B): DConsV5[B] = new DConsV5(element, this)

  override def toString: String = {
    s"$head " + this.tail.toString
  }

  def filter(predicate: A => Boolean): DListV5[A] =
    if(predicate(h)) new DConsV5(h, t.filter(predicate))
    else t.filter(predicate)

  def map[B](transformer: A => B) : DListV5[B] =
    new DConsV5(transformer(h), t.map(transformer))

  //Add a concatenation method for use by FlatMap
  /*
  [1,2] ++ [3,4,5]
  = new Cons(1, [2] ++ [3,4,5])
  = new Cons(1, new Cons(2, Empty ++ [3,4,5]))
  = new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5)))))
 */
  def ++[B >: A](list: DListV5[B]): DListV5[B] =  new DConsV5(h, t ++ list)
  def flatMap[B](transformer: A => DListV5[B]): DListV5[B]  =
    transformer(h) ++ t.flatMap(transformer)

  def forEach(f: A => Unit) = {
    f(h)
    t.forEach(f)
  }


}


object Driver5 extends App{
  val aList = new DConsV5(1, DEmptyListV5)
  println(aList.head)

  //Create a linked list
  val listofIntegers = new DConsV5(3, new DConsV5(2, new DConsV5(1, DEmptyListV5)))
  println(listofIntegers.head)

  //Test the add method
  println(s"List of Integers head ${listofIntegers.add(20).head}")

  //Test the toString method
  println(s"Printing to string: ${listofIntegers.toString}")

  //Try with another type
  val stringList = new DConsV5("My String 1",  DEmptyListV5)
  println(s"Printing string list: ${stringList.toString}")
  println(s"String list head ${stringList.head}")
  println(s"Printing string list with an added value: ${stringList.add("Another chunklet").toString}")


  println("Transform list of integers by double: ")

  //DRS - Old way, commented out.    Replace with Lambda
//  println(listofIntegers.map(new Function1[Int, Int] {
//    override def apply(elem: Int) : Int = elem * 2
//  }))

  //DRS - Here is a lambda
  println(listofIntegers.map(_ * 2).toString)

  println("Filtered list of evens: ")

  //DRS - Old Way
//  println(listofIntegers.filter(new Function1[Int, Boolean] {
//    override def apply(myElem: Int): Boolean = {
//      if (myElem % 2 == 0)  true else false
//    }
//  }))

  //DRS - New Lambda function
  println(listofIntegers.filter(_ % 2 == 0))

  val anotherListofIntegers = new DConsV5(6, new DConsV5(5, new DConsV5(4, DEmptyListV5)))
  println("Concatenation: ")
  println(anotherListofIntegers ++ listofIntegers)

//  println("Flat Map stuff: ")
//  println(listofIntegers.flatMap(new Function1[Int, DListV5[Int]]{
//    override def apply(elem: Int): DListV5[Int] = new DConsV5(elem, new DConsV5(elem + 1, DEmptyListV5))
//  }).toString)

  //DRS - Here is the lambda
  println(listofIntegers.flatMap(elem => new DConsV5(elem, new DConsV5(elem + 1, DEmptyListV5))).toString)

  println("Super lambda-version of the adder as opposed to curried version: ")
  val superAdd = (x: Int) => (y: Int) => x + y
  println(superAdd(3)(4))

  println("For Each")
  println(listofIntegers)
  listofIntegers.forEach(println)
}