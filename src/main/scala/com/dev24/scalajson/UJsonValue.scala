package com.dev24.scalajson

import ujson.Str
import ujson.Value.Value

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class UJsonValue(v: ujson.Value) extends JsonValCore[UJsonValue] {
  override def asObject(): JsonObjCore[UJsonValue] = {
    val o = v.value match {
      case map: mutable.LinkedHashMap[String, Value] => (map)
      case _ => throw new RuntimeException
    }
    UjsonObject(o)

  }

  override def asValue(): JsonValCore[UJsonValue] = UJsonValue(v)

  override def asArray: JsonArrCore[UJsonValue] = ???

  override def isObject(): Boolean = v.value match {
    case _: mutable.LinkedHashMap[String, Value] => true
    case _ => false
  }

  override def isString: Boolean = v.value match {
    case _: Str => true
    case _ => false
  }

  override def asString: String = v.value match {
    case str: Str => str.value
//    case ujson.Null => null
//    case ujson.True => 1+""
//    case ujson.False => 0+""
//    case ujson.Str(s) => s+""
//    case ujson.Num(d) => d+""
    case _ => v.value.toString
   // case _ => throw new RuntimeException
  }

  override def isArray(): Boolean = v.value match {
    case _: ArrayBuffer[Value] => true
    case _ => false
  }

  override def getSource: AnyRef = ???
}

object UJsonValue {

  def apply(v: Value): UJsonValue = {
    println("UJsonValue--" + v)
    var j = new UJsonValue(v)
    j
  }

}
