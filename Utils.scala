package xin.saul.vd

import scala.reflect.io.File
import scala.xml.Node

object utils {
  val IMAGE_SIZE = (960, 540)

  val TARGET_DIRECTORY = "C:\\Users\\Saulxk\\Desktop\\我的文件夹\\临时文件\\xml\\"

  def main(args: Array[String]): Unit = {
    val directory = File.apply("C:\\Users\\Saulxk\\Desktop\\我的文件夹\\临时文件\\DETRAC-Train-Annotations-XML").toDirectory
    val (wi, hi) = IMAGE_SIZE
    directory.files.foreach(handleAnnotationXML(_,wi,hi))
  }


  private def handleAnnotationXML(file: File, wi: Int, hi: Int) = {
    var i=1
    scala.xml.XML.load(file.toURL).child.
      filter(_.label == "frame").map(
      _.child.filter(_.label == "target_list").
        head.child.filter(_.label == "target").
        map(_.head.child.filter(x => x.label == "box" || x.label == "attribute").
          map(extractData(wi, hi, _))).
        map(map2OutputStream(_))
    ).foreach(x => {
      val target = File.apply(s"$TARGET_DIRECTORY${file.name.substring(0,9)}img${getOrder(i)}.txt")
      if (!target.exists) {
        target.createFile()
        val writer = target.writer(true, "UTF-8")
        i += 1
        x.foreach(writer.write(_))
        writer.flush()
      }
    })
  }

  private def getOrder(i: Int): String = {
    (i>999,i>99,i>9) match {
      case (true,_,_)=>s"00$i"
      case (false,true,_)=>s"000$i"
      case (false,false,true)=>s"0000$i"
      case _=>s"00000$i"
    }
  }
  private def map2OutputStream(ls: Seq[String]) = {
    ls match {
      case List(a, b) => s"$b $a\r\n"
    }
  }

  private def extractData(wi: Int, hi: Int, x: Node) = {
    x.label match {
      case "box" => map2LocationRatio(wi, hi, x)
      case "attribute" => map2CarType(x)
    }
  }

  private def map2LocationRatio(wi: Int, hi: Int, x: Node) = {
    List("left", "top", "width", "height").
      map(x.attribute(_).map(_.text.toFloat)).
      map(_.get) match {
      case List(a, b, c, d) => (a / wi, b / hi, math.min(1,(a + c) / wi),math.min(1,(b + d) / hi)) match {
        case (x1, y1, x2, y2) => s"$x1 $y1 $x2 $y2"
      }
    }
  }

  private def map2CarType(x: Node) = {
    x.attributes("vehicle_type").text match {
      case "car" => "1"
      case "van" => "2"
      case "bus" => "3"
      case "others"=>"4"
    }
  }
}

