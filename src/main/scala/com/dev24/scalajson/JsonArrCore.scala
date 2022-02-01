package com.dev24.scalajson

trait JsonArrCore[JVC<:JsonValCore[JVC]] extends JsonArrayBase[JVC]  with JsonSource {

  def set(index:Int,jsonSource:JsonSource)

  def remove(index:Int):Boolean

  def add(jsonSource: JsonSource): Unit


}
