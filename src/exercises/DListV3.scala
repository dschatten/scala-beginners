package exercises

import exercises.ListTest.listOfIntegers

//TODO DRS - Why is this contravariant?
//TODO DRS - In lecutre 24 ~13 minutes in, these traits are removed (syntactic sugar)
trait MyTransformer[-A, B]{
  def transform(myElem: A) : B
}

//TODO DRS - Why is this contravariant?
trait MyPredicate[-T]{
  def test(myElem: T): Boolean
}

/*
Abstract class to build out common List operations
For different types of Lists
 */
abstract class DListV3 [+A]{

  def head: A
  def tail: DListV3[A]
  def isEmpty: Boolean
  def add[B >:A](element: B): DListV3[B]

  //defining a method that's referring to a trait in it's signature....
  def map[B](transformer: MyTransformer[A, B]) : DListV3[B]
  def flatMap[B](transformer: MyTransformer[A, DListV3[B]]): DListV3[B]
  def filter(predicate: MyPredicate[A]) : DListV3[A]

  override def toString: String = super.toString

  // concatenation
  def ++[B >: A](list: DListV3[B]): DListV3[B]
}

//Define as object, doesn't need to be instantiated
case object DEmptyListV3 extends DListV3[Nothing]{
  def head: Nothing = throw new NoSuchElementException
  def tail: DListV3[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): DListV3[B] = new DConsV3(element, DEmptyListV3)

  override def toString: String = " "

  //The one thing that's not confusing is that each of these methods in this class just return an empty list..think I got that
  def map[B](transformer: MyTransformer[Nothing, B]) : DListV3[B] = DEmptyListV3
  def flatMap[B](transformer: MyTransformer[Nothing, DListV3[B]]): DListV3[B]= DEmptyListV3
  def filter(predicate: MyPredicate[Nothing]) : DListV3 [Nothing]= DEmptyListV3

  def ++[B >: Nothing](list: DListV3[B]): DListV3[B] = list
}

case class DConsV3[+A](h: A, t: DListV3[A]) extends DListV3[A]{
  def head: A = h
  def tail: DListV3[A] = t
  def isEmpty: Boolean = false
  def add[B >:A](element: B): DConsV3[B] = new DConsV3(element, this)

  override def toString: String = {
    s"$head " + this.tail.toString
  }

  def filter(predicate: MyPredicate[A]): DListV3[A] =
    if(predicate.test(h)) new DConsV3(h, t.filter(predicate))
    else t.filter(predicate)

  def map[B](transformer: MyTransformer[A, B]) : DListV3[B] =
    new DConsV3(transformer.transform(h), t.map(transformer))

  //Add a concatenation method for use by FlatMap
  /*
  [1,2] ++ [3,4,5]
  = new Cons(1, [2] ++ [3,4,5])
  = new Cons(1, new Cons(2, Empty ++ [3,4,5]))
  = new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5)))))
 */
  def ++[B >: A](list: DListV3[B]): DListV3[B] =  new DConsV3(h, t ++ list)
  def flatMap[B](transformer: MyTransformer[A, DListV3[B]]): DListV3[B]  =
    transformer.transform(h) ++ t.flatMap(transformer)


}


//class EvenPredicate extends MyPredicate[Int] {
//  override def test[Int]: Boolean = ???
//}


object Driver3 extends App{
  val aList = new DConsV3(1, DEmptyListV3)
    println(aList.head)

  //Create a linked list
  val listofIntegers = new DConsV3(3, new DConsV3(2, new DConsV3(1, DEmptyListV3)))
  println(listofIntegers.head)

  //Test the add method
  println(s"List of Integers head ${listofIntegers.add(20).head}")

  //Test the toString method
  println(s"Printing to string: ${listofIntegers.toString}")

  //Try with another type
  val stringList = new DConsV3("My String 1",  DEmptyListV3)
  println(s"Printing string list: ${stringList.toString}")
  println(s"String list head ${stringList.head}")
  println(s"Printing string list with an added value: ${stringList.add("Another chunklet").toString}")

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
  println(listofIntegers.map(new MyTransformer[Int, Int] {
    override def transform(elem: Int) : Int = elem * 2
  }))

  println("Filtered list of evens: ")
  println(listofIntegers.filter(new MyPredicate[Int] {
    override def test(myElem: Int): Boolean = {
      if (myElem % 2 == 0)  true else false
    }
  }))

  val anotherListofIntegers = new DConsV3(6, new DConsV3(5, new DConsV3(4, DEmptyListV3)))
  println("Concatenation: ")
  println(anotherListofIntegers ++ listofIntegers)

  println("Flat Map stuff: ")
  println(listofIntegers.flatMap(new MyTransformer[Int, DListV3[Int]]{
    override def transform(elem: Int): DListV3[Int] = new DConsV3(elem, new DConsV3(elem + 1, DEmptyListV3))
  }).toString)

}