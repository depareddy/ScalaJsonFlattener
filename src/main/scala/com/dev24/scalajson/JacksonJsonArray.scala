package com.dev24.scalajson

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.{ArrayNode, ObjectNode}
import  scala.jdk.CollectionConverters._

import java.util.Map.Entry

class JacksonJsonArray(arrayNode:ArrayNode) extends JsonArrCore[JacksonJsonVal] {
  override def set(index: Int, jsonSource: JsonSource): Unit = arrayNode.set(index,jsonSource.getSource.asInstanceOf[JsonNode])

  override def remove(index: Int): Boolean = arrayNode.remove(index)!=null

  override def add(jsonSource: JsonSource): Unit = arrayNode.add(jsonSource.getSource.asInstanceOf[JsonNode])

  /**
   * Returns a JSON value wrapper by given index.
   *
   * @param index a position in this JSON array
   * @return a JSON value wrapper
   */
  override def get(index: Int): JacksonJsonVal = JacksonJsonVal(arrayNode.get(index))

  /**
   * Returns the size of this JSON array.
   *
   * @return an int
   */
  override def size(index: Int): Int = arrayNode.size()

  override def isObject(): Boolean = false

  override def isString: Boolean = false

  override def asString: String = arrayNode.toString

  override def asObject(): JsonObjBase[JacksonJsonVal] = throw new UnsupportedOperationException

  override def asValue(): JsonValBase[JacksonJsonVal] =JacksonJsonArray(arrayNode)

  override def asArray(): JsonArrayBase[JacksonJsonVal] = this

  override def isArray(): Boolean = true

  override def iterator: Iterator[JacksonJsonVal] = new ScalaJSONArrIterator(arrayNode.iterator().asScala)

  class ScalaJSONArrIterator(jsonNodeIterator:  Iterator[JsonNode]) extends Iterator[JacksonJsonVal]
  {
    override def hasNext: Boolean = jsonNodeIterator.hasNext

    override  def next(): JacksonJsonVal = new JacksonJsonVal(jsonNodeIterator.next());
  }

  override def getSource: AnyRef = arrayNode
}
object JacksonJsonArray {

  def apply(arrayNode:ArrayNode): JacksonJsonArray = {
    var j = new JacksonJsonArray(arrayNode)
    j
  }

}