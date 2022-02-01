package com.dev24.scalajson

trait JsonObjCore[JVC<:JsonValCore[JVC]] extends JsonObjBase[JVC]  with JsonSource {


  def set(name:String,jsonSource:JsonSource)

  def remove(name:String):Boolean



}
