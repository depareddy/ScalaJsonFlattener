package com.dev24.scalajson



//val  iterator;
//private E peek;

//private E current = null;



class IndexedPeekIterator[T](iterator: Iterator[T]) extends Iterator[T] {

   private var hasPeek = false
   private var index = -1
   private var peek:T = _
   private var current:T=_

  def peeking()={
    peek = iterator.next()
    hasPeek = true;
  }

  def getIndex():Int={
    index
  }

  def getCurrent():T={
    current
  }



  def remove={
    if(hasPeek)
      throw new IllegalStateException
    //iterator.
  }

  override def hasNext(): Boolean = {
    hasPeek || iterator.hasNext
  }

  override def next(): T = {

    if (!hasNext()) {
      throw new NoSuchElementException()
    }
    index += 1
    if(hasPeek)
      {
        hasPeek=false
        //peek
        current=peek
        current
      }
    else
      {
        peeking()
        next()
      }

  }

  def peekAdvanced():T={
    if(!hasPeek && hasNext())
      {
        peeking()
      }
    if(!hasPeek)
      throw new NoSuchElementException()
      peek

  }

//  def peek():T={
//    if(!hasPeek && hasNext())
//    {
//      peeking()
//    }
//    if(!hasPeek)
//      throw new NoSuchElementException()
//    peek
//
//  }



}
object IndexedPeekIterator {

  //type T= Entry[String,_]

//  def  apply[T](iter:Iterable[Entry[String,_]]): IndexedPeekIterator[Entry[String,_]] = {
//    val j = new IndexedPeekIterator(iter.iterator)
//    j
//  }


  def  apply[T](iter:Iterable[T]): IndexedPeekIterator[T] = {
    val j = new IndexedPeekIterator(iter.iterator)
    j
  }

}
