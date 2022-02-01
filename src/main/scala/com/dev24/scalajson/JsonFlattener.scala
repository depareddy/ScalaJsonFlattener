package com.dev24.scalajson

// import com.example.Test1.elementIters
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.text.translate.{AggregateTranslator, EntityArrays, LookupTranslator}

import java.util
import java.util.Collections.unmodifiableMap
import java.util.Map.Entry
import scala.collection.mutable
import scala.jdk.CollectionConverters._

case class FlattenerTO(addVal:Boolean,source2:Any )





class JsonFlattener(json:String) {

  type M= JsonValBase[JacksonJsonVal]

  case class FlattenerTO1(addVal:Boolean,source2:M )

  private var separator = '.'
  private var leftBracket = '['
  private var rightBracket=']'
  private var flattenMode="NORMAL"
  private var printMode="MINIMAL"
  private var elementIters:mutable.ArrayDeque[IndexedPeekIterator[Any]] = new mutable.ArrayDeque();

   var source:JsonValBase[JacksonJsonVal]=_

  private var  flattenedMap:JsonifyLinkedHashMapNew[String,Object]=new JsonifyLinkedHashMapNew()

  private var  flattenedMap1:JsonifyLinkedHashMapNew[String,Object]=new JsonifyLinkedHashMapNew()

  def flatten():String ={
    //var source1:JsonValBase[_]=new JacksonJsonCore().parse(json)
    //source1
  //  val m=flattenAsMap()
    val m=flattenAsMapNew()
    val str=m.map({
      case(k,v) => {
        println("k--"+k)
        println("v--"+v)
        (k+":"+v)
      }
    }).mkString("||")
    str
  }


  def  flattenAsMap():mutable.Map[String, Object]= {
    source=new JacksonJsonCore().parse(json)
    println("source--"+source)
   // flattenedMap = new JsonifyLinkedHashMapNew()
    println("flattenAsMap before reduce")
    reduce(source)
    println("flattenAsMap after reduce")
    while (!elementIters.isEmpty) {
      // IndexedPeekIterator<?> deepestIter = elementIters.getLast();
      var deepestIter:IndexedPeekIterator[Any] = elementIters.last
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
        reduce(mem.getValue);
      }
      else
        {
          println("before else JsonValueBase--");
          var val1:JsonValBase[_] = deepestIter.next().asInstanceOf[JsonValBase[_]];
          println("else JsonValueBase--"+ToStringBuilder.reflectionToString(val1));
          reduce(val1);
        }
    }

    flattenedMap.asScala
    //flattenedMap = new JsonifyLinkedHashMapNew();


  }


  def getElem(iter: mutable.ArrayDeque[IndexedPeekIterator[Any]], source2: Any): JsonifyLinkedHashMapNew[String, Object] = {
     val key=computeKeyNew1(iter)
     val  flattenedMapTemp:JsonifyLinkedHashMapNew[String,Object]=new JsonifyLinkedHashMapNew()
    flattenedMapTemp.put(key,source2.toString)
    flattenedMapTemp
  }

  def  flattenAsMapNew():mutable.Map[String, Object]= {
    source=new JacksonJsonCore().parse(json)
    println("flattenAsMapNew--"+source)
    // flattenedMap = new JsonifyLinkedHashMapNew()
     var elementItersnew:mutable.ArrayDeque[IndexedPeekIterator[Any]] = new mutable.ArrayDeque();
     var  flattenedMap1:JsonifyLinkedHashMapNew[String,Object]=new JsonifyLinkedHashMapNew()
    println("flattenAsMapNew before reduce")
    var b=reduceNew(source,elementItersnew)
    println("flattenAsMapNew after reduce"+b)
    println("elementItersnew size"+elementItersnew.size)
    LazyList.iterate((elementItersnew,b)) { case (l,b) =>
      var deepestIter:IndexedPeekIterator[Any] = l.last
      println("deepestIter last--"+deepestIter)
      if(!deepestIter.hasNext()) {
        println("deepestIter has next and removing last--")
        l.removeLast();
        println("elementIters sizedeepestIter after removing last--"+l)
        var a = None : Option[Any]
        val flatternerTo=new FlattenerTO1(false,null)
        (elementItersnew,flatternerTo)
      }
      else if (deepestIter.peekAdvanced().isInstanceOf[Entry[String,_ <: JsonValBase[_]]]) {
        // @SuppressWarnings("unchecked")
        println("deepestIter peekAdvanced--")
        var mem:Entry[String,_ <: JsonValBase[JacksonJsonVal]] =
          deepestIter.next().asInstanceOf[Entry[String,_ <: JsonValBase[JacksonJsonVal]]];
        System.out.println("else if flattenAsMap mem value--"+mem.getValue);
        val b=reduceNew(mem.getValue,elementItersnew);
        (elementItersnew,b)
      }
      else
      {
        println("before else JsonValueBase--");
        var val1:JsonValBase[JacksonJsonVal] = deepestIter.next().asInstanceOf[JsonValBase[JacksonJsonVal]];
        println("else JsonValueBase--"+ToStringBuilder.reflectionToString(val1));
        val b= reduceNew(val1,elementItersnew);
        (elementItersnew,b)
      }
    }.takeWhile((v) => !v._1.isEmpty).foreach((v) => {
      println("iterator--")
      val t5=v._1.iterator
      val flatneer=v._2
      println("bool"+b+"sss"+t5.foreach(p => println("print contents"+p)))
      if(flatneer.addVal)
        {
         var map:JsonifyLinkedHashMapNew[String,Object]=getElem(v._1,flatneer.source2)
          println("bool true"+b+"sss"+t5.foreach(p => println("print contents"+p)))
          flattenedMap1.putAll(map)
        }
    })
    // flattenedMap.asScala
    flattenedMap1.asScala
   // while (!elementIters.isEmpty) {
      // IndexedPeekIterator<?> deepestIter = elementIters.getLast();

    //}

   // flattenedMap.asScala
    //flattenedMap = new JsonifyLinkedHashMapNew();


  }


  def reduce(source2:JsonValBase[_]): Unit ={

    if (source2.isObject && source2.asObject.iterator.hasNext) {
      // System.out.println("if--"+source2);
      println("reduce if---" + source2)
      elementIters.addOne(IndexedPeekIterator(source2.asObject()));
    }
    else if(source2.isArray() && source2.asArray().iterator.hasNext) {
      elementIters.addOne(IndexedPeekIterator(source2.asArray()));
    }
    else
      {
        println("reduce else---"+source2)
        println("elementIters size---" + elementIters.size)
        //var key:String = computeKey()
        var key:String = computeKeyNew()
        flattenedMap.put(key,source2)
        println("reduce computed key---"+key)
      }
        //System.out.println("else--");



  }

  def reduceNew(source2:JsonValBase[JacksonJsonVal],elementItersnew:mutable.ArrayDeque[IndexedPeekIterator[Any]])  ={

    var compute=false
    if (source2.isObject && source2.asObject.iterator.hasNext) {
      // System.out.println("if--"+source2);
      println("reduce if---" + source2)
      elementItersnew.addOne(IndexedPeekIterator(source2.asObject()));
    }
    else if(source2.isArray() && source2.asArray().iterator.hasNext) {
      elementItersnew.addOne(IndexedPeekIterator(source2.asArray()));
    }
    else
    {
      println("reduce else---"+source2)
      println("elementIters size---" + elementIters.size)
      //var key:String = computeKey()
      var key:String = computeKeyNew1(elementItersnew)
      flattenedMap.put(key,source2)
      println("reduce computed key---"+key)
      compute=true
    }
    //System.out.println("else--");
   // val flatternerTo=new FlattenerTO(compute,source2)
   val flatternerTo=new FlattenerTO1(compute,source2)
    flatternerTo

  }


  def computeKey(): String= {
    val sb = new StringBuilder();
    println("computeKey")
    for (iter: IndexedPeekIterator[Any] <- elementIters) {
      if (iter.getCurrent().isInstanceOf[Entry[String,_ <: JsonValBase[_]]]) {
        var key=iter.getCurrent().asInstanceOf[Entry[String,_ <: JsonValBase[_]]].getKey
        println("computeKey--key"+key)
        if (sb.length() != 0) sb.append(separator)
        sb.append(key);
//        String key =
//          ((Entry<String, ? extends JsonValueBase<?>>) iter.getCurrent())
//        .getKey();

      }
      else
        {
          sb.append(".")
          sb.append(iter.getIndex())
          println("computeKey--key--index"+sb)
        }

    }
  sb.toString()
  }

  def computeKeyNew(): String= {
    val sb = new StringBuilder();
    println("computeKey")
    for (iter: IndexedPeekIterator[Any] <- elementIters) {
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





//  public JsonFlattenerNew(String json) {
//    jsonCore = new JacksonJsonCoreNew();
//    source = jsonCore.parse(json);
//  }

}
object JsonFlattener {

  def defaultTransalator(): AggregateTranslator = {

    val translator = new LookupTranslator(unmodifiableMap(new util.HashMap[CharSequence, CharSequence] {
      put("\"", "\\\"")
      put("\\", "\\\\")
    }))
    val lookLookupTranslator = new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE)
    new AggregateTranslator(translator, lookLookupTranslator)
    /* val translator= new LookupTranslator(unmodifiableMap(new HashMap<CharSequence, CharSequence>() {
        private static final long serialVersionUID = 1L;
      {
        put("\"", "\\\"");
        put("\\", "\\\\");
      }
      })), new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE)));*/
  }

}