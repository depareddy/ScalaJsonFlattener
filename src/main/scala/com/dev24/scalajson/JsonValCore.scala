package com.dev24.scalajson

trait JsonValCore[JVC<:JsonValCore[JVC]] extends JsonValBase[JVC] with JsonSource  {


  def asObject(): JsonObjCore[JVC]

  def asValue(): JsonValCore[JVC]

   def asArray: JsonArrCore[JVC]

}
