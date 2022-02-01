package com.dev24.scalajson

object TestFlatenner extends App{

  //val json = "{\"abc\":{\"def\":123,\"klm\":345 }}"
  val json = "{\"abc\":{\"def\":123},\"employees\":[    {\"name\":\"Ram\", \"email\":\"ram@gmail.com\", \"age\":23}, {\"name\":\"Ram1\",\"email\":\"ram1@gmail.com\", \"age\":33}]}"

  //val jsonVal=new JacksonJsonCore().parse(json)
  //println("jsonVal--"+jsonVal)
  val str=new JsonFlattener(json).flatten()
  println("str--"+str)

}
