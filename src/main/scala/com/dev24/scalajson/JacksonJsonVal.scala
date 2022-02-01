package com.dev24.scalajson

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.{ArrayNode, ObjectNode}
class JacksonJsonVal(jsonNode:JsonNode)  extends JsonValCore[JacksonJsonVal]  {


  override def asObject(): JsonObjCore[JacksonJsonVal] = {
    println("asObject")
    val o=jsonNode match {
      case o:ObjectNode => {
        println("Found ObjectNode!")
        o
      }
      case _ =>   throw new RuntimeException
    }
    JacksonJsonObject(o)
  }

  override def asValue: JsonValCore[JacksonJsonVal] = {
    println("asValue")
    JacksonJsonVal(jsonNode)
  }

  override def isObject(): Boolean = {
    println("isObject"+ jsonNode.isObject())
    jsonNode.isObject();
  }

  override def isString: Boolean = jsonNode.isTextual

  override def asString: String = jsonNode.asText()

  override def getSource: JsonNode = jsonNode

  override def toString() : String={
    jsonNode.toString
  }

  // override def asArray: JsonArrCore = ???

  override def asArray:JsonArrCore[JacksonJsonVal] = {
    println("asArray")
    val o=jsonNode match {
      case o:ArrayNode => {
        println("Found ObjectNode!")
        o
      }
      case _ =>   throw new RuntimeException
    }
    JacksonJsonArray(o)

  }

  override def isArray(): Boolean = jsonNode.isArray
}
object JacksonJsonVal {

  def apply(jsonNode:JsonNode): JacksonJsonVal = {
    println("JacksonJsonVal--"+jsonNode)
    var j = new JacksonJsonVal(jsonNode)
   j
  }

}