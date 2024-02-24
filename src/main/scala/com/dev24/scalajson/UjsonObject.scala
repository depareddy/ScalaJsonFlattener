package com.dev24.scalajson

import ujson.Value.Value

import java.util
import java.util.Map
import scala.collection.mutable

class UjsonObject(map:mutable.LinkedHashMap[String,Value] ) extends JsonObjCore[UJsonValue]{

  override def set(name: String, jsonSource: JsonSource): Unit = ???

  override def remove(name: String): Boolean = ???

  override def iterator: Iterator[Map.Entry[String, UJsonValue]] = {
//    map.iterator.map(v => {
//      val v2=new UJsonValue(v._2)
//      //new mutable.LinkedHashMap().put(v._1,v2)
//      new ScalaJSONObjIterator(map.iterator)
//    }
    val it=map.iterator

    new ScalaJSONObjIterator(map.iterator)
  }

  class ScalaJSONObjIterator(ujsonIterator:  Iterator[Tuple2[String, Value]]) extends Iterator[Map.Entry[String, UJsonValue]] {
    def hasNext = ujsonIterator.hasNext
    def next() = {
      val member = ujsonIterator.next();
      new util.AbstractMap.SimpleImmutableEntry(member._1,
        new UJsonValue(member._2));

    }
  }

  override def getSource: AnyRef = ???

  override def names: Iterator[String] = ???

  override def contains(name: String): Boolean = ???

  override def get(name: String): Option[UJsonValue] = ???

  override def isObject(): Boolean = {
    println("UjsonObject isObject")
    true
  }

  override def isString: Boolean = ???

  override def asString: String = ???

  override def asObject(): JsonObjBase[UJsonValue] = {
    println("JacksonJsonObject asObject")
    return this;
  }


  override def asValue(): JsonValBase[UJsonValue] = UjsonObject(map)

  override def asArray(): JsonArrayBase[UJsonValue] = throw new UnsupportedOperationException

  override def isArray(): Boolean = false
}
object UjsonObject {

  def apply(map:mutable.LinkedHashMap[String,Value]): UjsonObject = {
    var j = new UjsonObject(map)
    j
  }

}