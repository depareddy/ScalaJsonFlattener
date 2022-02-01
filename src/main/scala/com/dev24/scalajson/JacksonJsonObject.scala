package com.dev24.scalajson
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode

import java.util
import java.util.Map
import java.util.Map.Entry
import scala.jdk.CollectionConverters._

class JacksonJsonObject(objectNode:ObjectNode) extends JsonObjCore[JacksonJsonVal]{
  override def set(name: String, jsonSource: JsonSource): Unit = {
    val jsonNode=jsonSource.getSource.asInstanceOf[JsonNode]
    objectNode.set(name,jsonNode)
  }

  override def remove(name: String): Boolean = objectNode.remove(name)!=null

  override def getSource: ObjectNode = objectNode

  override def names: Iterator[String] = {
    objectNode.fieldNames().asScala
  }

    def isObject():Boolean ={
      println("JacksonJsonObject isObject")
     true
  }

  def  asObject():JacksonJsonObject ={
    println("JacksonJsonObject asObject")
    return this;
  }

  override def contains(name: String): Boolean = objectNode.has(name)

  override def get(name: String): Option[JacksonJsonVal] = {

    val json=Option(objectNode.get(name))
     json match {
       case Some(a) => Some(JacksonJsonVal(a))
       case _ =>  None//throw new RuntimeException
     }
  // ret

  }


  override def iterator(): Iterator[Map.Entry[String, JacksonJsonVal]] = {
    val it=objectNode.fields.asScala
    new ScalaJSONObjIterator(it)
  }

  class ScalaJSONObjIterator(jsonNodeIterator:  Iterator[Entry[String, JsonNode]]) extends Iterator[Entry[String, JacksonJsonVal]] {
    def hasNext = jsonNodeIterator.hasNext
    def next() = {
      val member = jsonNodeIterator.next();
      new util.AbstractMap.SimpleImmutableEntry(member.getKey(),
        new JacksonJsonVal(member.getValue()));

    }
    }

  override def isString: Boolean = false

  override def asString: String = {
    throw new UnsupportedOperationException
  }

  override def asValue(): JsonValBase[JacksonJsonVal] = JacksonJsonObject(objectNode)

  override def asArray(): JsonArrayBase[JacksonJsonVal] = {
    throw new UnsupportedOperationException
  }

  override def isArray(): Boolean = false
}

object JacksonJsonObject {

  def apply(objectNode:ObjectNode): JacksonJsonObject = {
    var j = new JacksonJsonObject(objectNode)
    j
  }

}


