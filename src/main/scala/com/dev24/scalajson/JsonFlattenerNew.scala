package com.dev24.scalajson

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.text.translate.{AggregateTranslator, EntityArrays, LookupTranslator}

import java.util
import java.util.Collections.unmodifiableMap
import java.util.Map.Entry
import scala.Console.println
import scala.collection.mutable
import scala.jdk.CollectionConverters._

class JsonFlattenerNew(json:String) {

  type M= JsonValBase[_]

  case class FlattenerTONew1(addVal:Boolean,source2:Any )

  private var separator = '.'
  private var leftBracket = '['
  private var rightBracket=']'
  private var flattenMode="NORMAL"
  private var printMode="MINIMAL"
  private var elementIters:mutable.ArrayDeque[IndexedPeekIterator[Any]] = new mutable.ArrayDeque()
  private var  flattenedMap:JsonifyLinkedHashMapNew[String,Object]=new JsonifyLinkedHashMapNew()
  private var  flattenedMap1:JsonifyLinkedHashMapNew[String,Object]=new JsonifyLinkedHashMapNew()

  def flatten():String ={
    val m=flattenAsMap()
    val str=m.map({
      case(k,v) => {
        println("k--"+k)
        println("v--"+v)
        (k+":"+v)
      }
    }).mkString("||")
    str
  }

  def getElem(iter: mutable.ArrayDeque[IndexedPeekIterator[_]], source2: Any): JsonifyLinkedHashMapNew[String, Object] = {
     val key=computeKey(iter)
     val  flattenedMapTemp:JsonifyLinkedHashMapNew[String,Object]=new JsonifyLinkedHashMapNew()
    flattenedMapTemp.put(key,source2.toString)
    flattenedMapTemp
  }



  def  flattenAsMap():mutable.Map[String, Object]= {
    //source=new JacksonJsonCore().parse(json)
    var source:Option[JsonValBase[_]] =JsonFlattenerNew.convertToJson(json)
    println("flattenAsMapNew--"+source)
    // flattenedMap = new JsonifyLinkedHashMapNew()
    var elementItersnew:mutable.ArrayDeque[IndexedPeekIterator[_]] = new mutable.ArrayDeque();
    var  flattenedMap1:JsonifyLinkedHashMapNew[String,Object]=new JsonifyLinkedHashMapNew()
    println("flattenAsMapNew before reduce")
    var b=reduce(source,elementItersnew,flattenedMap1)
    println("flattenAsMapNew after reduce"+b)
    println("elementItersnew size"+elementItersnew.size)
    LazyList.iterate(elementItersnew) { case l =>
      var deepestIter:IndexedPeekIterator[_] = l.last
      println("deepestIter last--"+deepestIter)
      if(!deepestIter.hasNext()) {
        println("deepestIter has next and removing last--")
        l.removeLast();
        println("elementIters sizedeepestIter after removing last--"+l)
        elementItersnew
      }
      else if (deepestIter.peekAdvanced().isInstanceOf[Entry[String,_ <: JsonValBase[_]]]) {
        // @SuppressWarnings("unchecked")
        println("deepestIter peekAdvanced--")
        var mem:Entry[String,_ <: JsonValBase[_]] =
          deepestIter.next().asInstanceOf[Entry[String,_ <: JsonValBase[_]]];
        println("else if flattenAsMap mem value--"+mem.getValue);
        reduce(Some(mem.getValue),elementItersnew,flattenedMap1);
        elementItersnew
      }
      else
      {
        println("before else JsonValueBase--");
      //  var val1= deepestIter.next().asInstanceOf[JsonValBase[_]];
        var val1=Some(deepestIter.next().asInstanceOf[JsonValBase[JacksonJsonVal]]);
        println("else JsonValueBase--"+ToStringBuilder.reflectionToString(val1));
        reduce(val1,elementItersnew,flattenedMap1);
    //    createKey(val1.asString,elementItersnew,flattenedMap1)
    //  val flatternerTo=new FlattenerTO1(compute,source2)
      //  flatternerTo
        elementItersnew
      }
    }.takeWhile((v) =>{
      println("take while")
      !v.isEmpty
    } ).foreach((v) => {
      println("for each")
      println("iterator--")
      val t5=v.iterator
      val t6=v.head.getCurrent();
      //val flatneer=v._2
      println(s"get current iterator--$t6")
      println("bool"+b+"sss"+t5.foreach(p => println("print contents"+p)))
    })
    // flattenedMap.asScala
    flattenedMap1.asScala
  }


  def updateMap(source2:Option[_ <: JsonValBase[_]],elementItersnew: mutable.ArrayDeque[IndexedPeekIterator[_]], flattenedMap: JsonifyLinkedHashMapNew[String, Object]) = {
    var key: String = computeKey(elementItersnew)
    //        println("in reduceNew None --key--" + key)
    flattenedMap.put(key, source2.get.asString)

  }



//  def cast[T <: JsonValBase[T]: ClassTag](x: Any): Option[_ <:JsonValBase[_]] = x match {
//    case v: T=> Some(v)
//    case _ => None //  Option.empty[JsonValBase]
//  }
  def createKey(v:String,elementItersnew:mutable.ArrayDeque[IndexedPeekIterator[_]],flattenedMap:JsonifyLinkedHashMapNew[String,Object])  ={
    var key: String = computeKey(elementItersnew)
    println(s"in createKey--$key---value --$v")
    //  flattenedMap.put(key, source2.get.asString)
    flattenedMap.put(key, v)

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

  def reduce(source2:Option[_ <: JsonValBase[_]],elementItersnew:mutable.ArrayDeque[IndexedPeekIterator[_]],flattenedMap:JsonifyLinkedHashMapNew[String,Object])  = {
    val indexedPeekIterator = fetchObject(source2).orElse(fetchArray(source2)).orElse(None) // .map(e =>e)
    val o = indexedPeekIterator match {
      case Some(o) => {
        elementItersnew.addOne(o)
        println(s"elementIters size ${elementItersnew.size}")
      };
      case None => {
        println("in reduceNew None")
        var key: String = computeKey(elementItersnew)
        println("in reduceNew None --key--" + key)
        flattenedMap.put(key, source2.get.asString)
      }
    }
  }


  def computeKey(elementItersnew:mutable.ArrayDeque[IndexedPeekIterator[_]]): String= {
    val sb = new StringBuilder();
    println("computeKey")
    for (iter <- elementItersnew) {
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
}

trait JsonFlattenableNew[JVC<:JsonValBase[JVC]]{
  def serialize(t: String): Option[JsonValBase[JVC]]
}
object JsonFlattenerNew {


  implicit object JacksonJsonFlattenable extends JsonFlattenableNew[JacksonJsonVal]{
    def serialize(json: String) = {
      var source:JsonValBase[JacksonJsonVal] =new JacksonJsonCore().parse(json)
      Some(source)
    }
  }

  def convertToJson[JVC<:JsonValBase[JVC]](x: String): Option[JsonValBase[_]] = {
    implicitly[JsonFlattenableNew[JacksonJsonVal]].serialize(x)
  }

  def defaultTransalator(): AggregateTranslator = {
    val translator = new LookupTranslator(unmodifiableMap(new util.HashMap[CharSequence, CharSequence] {
      put("\"", "\\\"")
      put("\\", "\\\\")
    }))
    val lookLookupTranslator = new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE)
    new AggregateTranslator(translator, lookLookupTranslator)
  }

}