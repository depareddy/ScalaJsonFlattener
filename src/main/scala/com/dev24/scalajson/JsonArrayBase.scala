package com.dev24.scalajson

trait JsonArrayBase[JVB<:JsonValBase[JVB]] extends Iterable[JVB] with JsonValBase[JVB]  {

  /**
   * Returns a JSON value wrapper by given index.
   *
   * @param index a position in this JSON array
   * @return a JSON value wrapper
   */

  def get(index:Int):JVB


  /**
   * Returns the size of this JSON array.
   *
   * @return an int
   */
  def size(index:Int):Int


  /**
   * Checks if this JSON array is empty.
   *
   * @return true if this JSON array is empty, false otherwise
   */

  override def isEmpty():Boolean={
    !iterator.hasNext
  }




}
