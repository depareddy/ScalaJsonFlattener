package com.dev24.scalajson

object TestFlatenner extends App{

  val json1 = "{\"abc\":{\"def\":123,\"klm\":345 }}"
  val json = "{\"abc\":{\"def\":123},\"employees\":[    {\"name\":\"Ram\", \"email\":\"ram@gmail.com\", \"age\":23}, {\"name\":\"Ram1\",\"email\":\"ram1@gmail.com\", \"age\":33}]}"

  //val jsonVal=new JacksonJsonCore().parse(json)
  //println("jsonVal--"+jsonVal)
  //val jsonString = os.read(os.pwd/"src"/"test"/"resources"/"simple2.json")
  val jsonString = os.read(os.pwd/"src"/"test"/"resources"/"complex2.json")
 // val jsonString = os.read(os.pwd/"src"/"test"/"resources"/"simple3.json")
 // val str=new JsonFlattener(json).flatten()
 val str=new JsonFlattenerNew(jsonString).flatten()
  println("str--"+str)

  /* testing lazy list

  def func(num: Int): Boolean = {
  println(num)
  num < 5
}

def test_func() = {
  Iterator.iterate(1)(_ + 1).takeWhile(func(_))
}


   */


}
