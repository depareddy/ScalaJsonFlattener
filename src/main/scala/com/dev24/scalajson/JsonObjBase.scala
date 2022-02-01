package com.dev24.scalajson

import java.util.Map.Entry



trait JsonObjBase[JVB<:JsonValBase[JVB]] extends Iterable[Entry[String,JVB]] with JsonValBase[JVB] {

  //JsonValBase[JVB]

   def names: Iterator[String]
  // Iterator<String> names();

  def contains(name:String):Boolean

  def get(name:String):Option[JVB]

  override def isEmpty():Boolean={
    !iterator.hasNext
  }

}
