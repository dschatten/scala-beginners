package exercises

import exercises.ListTest.listOfIntegers

//TODO: DRS: In this lecture we deleted the two traits - See lecture 24, about 13 minutes in
//Also deleted the specific methods we created and changed them to apply().    Diff this file
//aginst DListV3 to see the changes, this is a good exercise
//DRS - We've changed map, flatMap, and filter into higher-order functions

/*
Abstract class to build out common List operations
For different types of Lists
 */
abstract class DListV4 [+A]{

  def head: A
  def tail: DListV4[A]
  def isEmpty: Boolean
  def add[B >:A](element: B): DListV4[B]

  //TODO - Here we removed the MyTransformer and MyPredicate and moved it like this:
  //Here are the commented out old implementations using the Traits:
  //def map[B](transformer: MyTransformer[A, B]) : DListV4[B]
  //def flatMap[B](transformer: MyTransformer[A, DListV4[B]]): DListV4[B]
  //def filter(predicate: MyPredicate[A]) : DListV4[A]

  //TODO: These are more readable with the MyTransformer and MyPredicate traits
  //DRS - We've just made these into higher order functions
  def map[B](transformer: A => B) : DListV4[B]
  def flatMap[B](transformer: A => DListV4[B]): DListV4[B]
  def filter(predicate: A => Boolean) : DListV4[A]

  override def toString: String = super.toString

  // concatenation
  def ++[B >: A](list: DListV4[B]): DListV4[B]
}

//Define as object, doesn't need to be instantiated
case object DEmptyListV4 extends DListV4[Nothing]{
  def head: Nothing = throw new NoSuchElementException
  def tail: DListV4[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): DListV4[B] = new DConsV4(element, DEmptyListV4)

  override def toString: String = " "


  //The one thing that's not confusing is that each of these methods in this class just return an empty list..think I got that
  def map[B](transformer: Nothing => B) : DListV4[B] = DEmptyListV4
  def flatMap[B](transformer: Nothing => DListV4[B]): DListV4[B]= DEmptyListV4
  def filter(predicate: Nothing => Boolean) : DListV4 [Nothing]= DEmptyListV4

  def ++[B >: Nothing](list: DListV4[B]): DListV4[B] = list
}

case class DConsV4[+A](h: A, t: DListV4[A]) extends DListV4[A]{
  def head: A = h
  def tail: DListV4[A] = t
  def isEmpty: Boolean = false
  def add[B >:A](element: B): DConsV4[B] = new DConsV4(element, this)

  override def toString: String = {
    s"$head " + this.tail.toString
  }

  //TODO: This actually kinda makes sense as far as how he's doing the filtering
  def filter(predicate: A => Boolean): DListV4[A] =
    if(predicate(h)) new DConsV4(h, t.filter(predicate))
    else t.filter(predicate)

  //TODO: This kinda sorta makes sense
  def map[B](transformer: A => B) : DListV4[B] =
    new DConsV4(transformer(h), t.map(transformer))

  //Add a concatenation method for use by FlatMap
  /*
  [1,2] ++ [3,4,5]
  = new Cons(1, [2] ++ [3,4,5])
  = new Cons(1, new Cons(2, Empty ++ [3,4,5]))
  = new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5)))))
 */
  //TODO: Confusing
  def ++[B >: A](list: DListV4[B]): DListV4[B] =  new DConsV4(h, t ++ list)
  def flatMap[B](transformer: A => DListV4[B]): DListV4[B]  =
    transformer(h) ++ t.flatMap(transformer)


}


//class EvenPredicate extends MyPredicate[Int] {
//  override def test[Int]: Boolean = ???
//}


object Driver4 extends App{
  val aList = new DConsV4(1, DEmptyListV4)
  println(aList.head)

  //Create a linked list
  val listofIntegers = new DConsV4(3, new DConsV4(2, new DConsV4(1, DEmptyListV4)))
  println(listofIntegers.head)

  //Test the add method
  println(s"List of Integers head ${listofIntegers.add(20).head}")

  //Test the toString method
  println(s"Printing to string: ${listofIntegers.toString}")

  //Try with another type
  val stringList = new DConsV4("My String 1",  DEmptyListV4)
  println(s"Printing string list: ${stringList.toString}")
  println(s"String list head ${stringList.head}")
  println(s"Printing string list with an added value: ${stringList.add("Another chunklet").toString}")

  //TODO - Review all of this crap
  //So we're directly instantiating a trait - MyTransformer....and providing a concrete implementation for it's override method
  //This is an Anonymous Class!!
  /*
  [1,2,3].map(n * 2)
    = new Cons(2, [2,3].map(n * 2))
    = new Cons(2, new Cons(4, [3].map(n * 2)))
    = new Cons(2, new Cons(4, new Cons(6, Empty.map(n * 2))))
    = new Cons(2, new Cons(4, new Cons(6, Empty))))
 */
  println("Transform list of integers by double: ")
  println(listofIntegers.map(new Function1[Int, Int] {
    override def apply(elem: Int) : Int = elem * 2
  }))

  //TODO - Review all of this stuff
  println("Filtered list of evens: ")
  println(listofIntegers.filter(new Function1[Int, Boolean] {
    override def apply(myElem: Int): Boolean = {
      if (myElem % 2 == 0)  true else false
    }
  }))

  val anotherListofIntegers = new DConsV4(6, new DConsV4(5, new DConsV4(4, DEmptyListV4)))
  println("Concatenation: ")
  println(anotherListofIntegers ++ listofIntegers)

  //TODO Yikes
  println("Flat Map stuff: ")
  println(listofIntegers.flatMap(new Function1[Int, DListV4[Int]]{
    override def apply(elem: Int): DListV4[Int] = new DConsV4(elem, new DConsV4(elem + 1, DEmptyListV4))
  }).toString)

}