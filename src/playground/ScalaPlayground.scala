package playground

import exercises.MyList


object ScalaPlayground extends App {
  println("Hello, Scala!")

  //DRS - map vs. flatMap
  //https://www.credera.com/blog/technology-insights/mastering-scala-understanding-map-and-flatmap/
  //Flatmap is shorthand to map a collection, same as map, and then flatten it
  val list = List(1,2,3,4)

  //Same results - it's already a flat list; see below
  println(list.map(x => x * 2))
  println(list.flatMap(x => List(x *2)))

  //  def flatMap[B](transformer: A => MyList[B]): MyList[B] =
  //    transformer(h) ++ t.flatMap(transformer)

  //Illustrations
  val listOLists = List(List(1,2,3),List(4,5,6))
  val flatMapped = listOLists.flatMap(x => x)  //Simply flattens the lists together

  println(s"List O Lists $listOLists")
  println(s"Flat like a pancake using flatMap: ${flatMapped}")
  println(s"Flat like a pancake: ${listOLists.flatten}")
}
