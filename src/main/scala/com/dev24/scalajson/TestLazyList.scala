package com.dev24.scalajson

import java.util.Map.Entry
import scala.collection.mutable
import scala.jdk.CollectionConverters._

object TestLazyList extends App{

  private var separator = '.'
  private var leftBracket = '['
  private var rightBracket=']'
  private var flattenMode="NORMAL"
  private var printMode="MINIMAL"

  //var source:JsonValBase[JacksonJsonVal]=_
  //var source:Option[JsonValBase[_]]=_
  var source:Option[JsonValBase[UJsonValue]]=_
  private var  flattenedMap:JsonifyLinkedHashMapNew[String,Object]=new JsonifyLinkedHashMapNew()
  private var elementIters:mutable.ArrayDeque[IndexedPeekIterator[_]] =_

  main2()

  def main2() = {
    println(s"in main2")
   // val jsonString = os.read(os.pwd/"src"/"test"/"resources"/"simple.json")
   val jsonString = os.read(os.pwd/"src"/"test"/"resources"/"simple2.json")
   val data:ujson.Value.Value = ujson.read(jsonString)
    source=Some(UJsonValue(data.obj))
    elementIters = new mutable.ArrayDeque();
   // reduce((ujsonVal))
    val m=flattenAsMap()
    val str=m.map({
      case(k,v) => {
        println("k--"+k)
        println("v--"+v)
        (k+":"+v)
      }
    }).mkString("||")
    println("str--"+str)

//    //data.value // LinkedHashMap("first_name" -> Str("Phil"), "last_name" -> Str("Hellmuth"), "birth_year" -> Num(1964.0))
//    //val data = ujson.read( TestFlatenner.json1)
//    println(s"json data $data")
//    var elementItersnew:mutable.ArrayDeque[IndexedPeekIterator[Any]] = new mutable.ArrayDeque();
//    val data1=data.obj.get("abc")
//
//   val obj= data1.get match {
////     case ujson.Null => null
////     case ujson.True => 1
////     case ujson.False => 0
//     case ujson.Str(s) => s
//     case ujson.Num(d) => d
//     case ujson.Arr(items) => {
//       println("arr")
//       items
//     }
//     case ujson.Obj(items) =>{
//       println("obj")
//       items
//     }
//    }
//    println(s"items--$obj")
   // var b=handle(data,elementItersnew)
   // handleMapObject()



  }

  def  flattenAsMap():mutable.Map[String, Object]= {
    //source=new JacksonJsonCore().parse(json)
   // println("source--"+source)
    // flattenedMap = new JsonifyLinkedHashMapNew()
    println("flattenAsMap before reduce")
    reduce(source)
    println("flattenAsMap after reduce")
    while (!elementIters.isEmpty) {
      // IndexedPeekIterator<?> deepestIter = elementIters.getLast();
      var deepestIter:IndexedPeekIterator[_] = elementIters.last
      println("deepestIter last--"+deepestIter)
      if(!deepestIter.hasNext()) {
        println("deepestIter has next and removing last--")
        elementIters.removeLast();
        println("elementIters sizedeepestIter after removing last--"+elementIters.size)
      } else if (deepestIter.peekAdvanced().isInstanceOf[Entry[String,_ <: JsonValBase[_]]]) {
        // @SuppressWarnings("unchecked")
        println("deepestIter peekAdvanced--")
        var mem:Entry[String,_ <: JsonValBase[_]] =
          deepestIter.next().asInstanceOf[Entry[String,_ <: JsonValBase[_]]];
        System.out.println("else if flattenAsMap mem value--"+mem.getValue);
        reduce(Some(mem.getValue));
      }
//      else
//      {
//        println("before else JsonValueBase--");
//        var val3:JsonValBase[_] = deepestIter.next().asInstanceOf[JsonValBase[_]];
//        println("else JsonValueBase--"+ToStringBuilder.reflectionToString(val3));
//       // val1.
//       // val3.asString
//        reduce(Some(val3));
//      }
    }

    flattenedMap.asScala
    //flattenedMap = new JsonifyLinkedHashMapNew();


  }

  def fetchObject(source2:Option[JsonValBase[_]]): Option[IndexedPeekIterator[_]] = {
    if (source2.get.isObject && source2.get.asObject.iterator.hasNext) {
      // System.out.println("if--"+source2);
      println("reduce if---" + source2)
      Some(IndexedPeekIterator(source2.get.asObject()))
    }
    else
      None
  }

  def fetchArray(source2:Option[JsonValBase[_]]): Option[IndexedPeekIterator[_]] = {
    if (source2.get.isArray() && source2.get.asArray().iterator.hasNext) {
      // System.out.println("if--"+source2);
      println("reduce if---" + source2)
      Some(IndexedPeekIterator(source2.get.asArray()))
    }
    else
      None
  }


  def reduce(source2:Option[JsonValBase[_]]): Unit ={
    val indexedPeekIterator=fetchObject(source2).orElse(fetchArray(source2)).orElse(None)// .map(e =>e)
    val o=indexedPeekIterator match {
      case Some(o) => {
        elementIters.addOne(o)
        println(s"elementIters size ${elementIters.size}")
      };
      case None => {
        var key: String = computeKeyNew()
        flattenedMap.put(key, source2.get.asString)
      }
      //case _ =>
    }

//    if (source2.isObject && source2.asObject.iterator.hasNext) {
//      // System.out.println("if--"+source2);
//      println("reduce if---" + source2)
//     val coll= IndexedPeekIterator(source2.asObject())
//      //elementIters.addOne(IndexedPeekIterator(source2.asObject()));
//      elementIters.addOne(coll);
//    }
//    else if(source2.isArray() && source2.asArray().iterator.hasNext) {
//      elementIters.addOne(IndexedPeekIterator(source2.asArray()));
//    }
//    else
//    {
//      println("reduce else---"+source2.asValue())
//      println("elementIters size---" + elementIters.size)
//      //var key:String = computeKey()
//      var key:String = computeKeyNew()
//      flattenedMap.put(key,source2.asString)
//      println("reduce computed key---"+key)
//    }
    //System.out.println("else--");



  }

  def computeKeyNew(): String= {
    val sb = new StringBuilder();
    println("computeKey")
    for (iter <- elementIters) {
      val obj=iter.getCurrent();
      obj match {

        case b: Entry[String,_] => {
          var key = b.getKey
          println("computeKey--key" + key)
          if (sb.length() != 0) sb.append(separator)
          sb.append(key);
        }
        case _ => {
          sb.append(".")
          sb.append(iter.getIndex())
          println("computeKey--key--index" + sb)
        }
      }
    }
    sb.toString()
  }

  def computeKeyNew1(elementItersnew:mutable.ArrayDeque[IndexedPeekIterator[Any]]): String= {
    val sb = new StringBuilder();
    println("computeKey")
    for (iter: IndexedPeekIterator[Any] <- elementItersnew) {
      val obj=iter.getCurrent();
      obj match {

        case b: Tuple2[String,String] => {
          var key = b._1
          println("computeKey--key" + key)
          if (sb.length() != 0) sb.append(",")
          sb.append(key);
        }
        case _ => {
          sb.append(".")
          sb.append(iter.getIndex())
          println("computeKey--key--index" + sb)
        }
      }
    }
    sb.toString()
  }

  def lazyIterateEx():Unit={
    var arr:mutable.ArrayDeque[IndexedPeekIterator[String]] = new mutable.ArrayDeque();
    val list: List[String] = List.apply("Truck", "Car", "Bike")
    var pItera = new IndexedPeekIterator[String](list.iterator)
    val nextT=pItera.hasNext()
    println(s"nextT--$nextT")
    arr.addOne(pItera)

    LazyList.iterate(arr) { case s => {
      println(s"the element is $s")
      var pItera=s.removeLast()
      //   pItera
      //if(pItera.hasNext()) {
      println(s"pitera")
      // s.next()
      //}
      arr
    }
    }.takeWhile(v => {
      println(s"take while")
      !v.isEmpty

      //v.i
    }).foreach((v) => {
      println(s"for each")
    })
  }

  def reduceOld(source2:JsonValBase[_]): Unit ={

    if (source2.isObject && source2.asObject.iterator.hasNext) {
      // System.out.println("if--"+source2);
      println("reduce if---" + source2)
      val coll= IndexedPeekIterator(source2.asObject())
      //elementIters.addOne(IndexedPeekIterator(source2.asObject()));
      elementIters.addOne(coll);
    }
    else if(source2.isArray() && source2.asArray().iterator.hasNext) {
      elementIters.addOne(IndexedPeekIterator(source2.asArray()));
    }
    else
    {
      println("reduce else---"+source2.asValue())
      println("elementIters size---" + elementIters.size)
      //var key:String = computeKey()
      var key:String = computeKeyNew()
      flattenedMap.put(key,source2.asString)
      println("reduce computed key---"+key)
    }
    //System.out.println("else--");



  }
}
