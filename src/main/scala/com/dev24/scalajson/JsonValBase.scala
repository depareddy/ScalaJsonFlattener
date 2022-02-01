package com.dev24.scalajson

trait JsonValBase[JVB<:JsonValBase[JVB]] {


  def isObject(): Boolean

  def isString: Boolean

  def asString: String

  def asObject(): JsonObjBase[JVB]

  def asValue(): JsonValBase[JVB]

  def asArray(): JsonArrayBase[JVB]

  def isArray(): Boolean

}
