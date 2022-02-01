package com.dev24.scalajson

import com.fasterxml.jackson.databind.ObjectMapper

import scala.util.{Failure, Try,Success}

class JacksonJsonCore {

  def  parseHelper(json:String):Try[JacksonJsonVal] = Try {
    val mapper = new ObjectMapper()
    val j=new JacksonJsonVal(mapper.readTree(json))
    println("parseHelper--"+j)
    j
    }

  def  parse(json:String):JacksonJsonVal = {
    val result = parseHelper(json)
    result match {
      case Failure(e) =>
        println("Found error!")
        throw new RuntimeException(e)

      case Success(result) => result
    }
  }


}
