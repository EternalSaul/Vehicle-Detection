package xin.saul.utils

import scala.reflect.io.File
import scala.xml.Node

object DarknetTrainingDatasetUtils {
  val IMAGE_SIZE = (960, 540)
  val TARGET_DIRECTORY = "C:\\Users\\Saulxk\\Desktop\\我的文件夹\\临时文件\\xml\\"
  var (car, van, other, bus) = (0, 0, 0, 0)

//  def main(args: Array[String]): Unit = {
//    val directory = File.apply("C:\\Users\\Saulxk\\Desktop\\我的文件夹\\临时文件\\DETRAC-Train-Annotations-XML").toDirectory
//    val (wi, hi) = IMAGE_SIZE
//    directory.files.foreach(file =>
//      handleAnnotationXML(file, wi, hi).foreach(x => {
//        WriteResult2TargetFile(file, x._2, x._1)
//      })
//    )
//  }


  private def handleAnnotationXML(file: File, wi: Int, hi: Int) = {
    println(file.name)
    scala.xml.XML.load(file.toURL).child.
      filter(_.label == "frame").map(x =>
      (
        x.attributes("num").text.toInt,
        mapTargetData2DarknentFormat(wi, hi, x)
      )
    )
  }

  private def mapTargetData2DarknentFormat(wi: Int, hi: Int, x: Node) = {
    x.child.filter(_.label == "target_list").
      head.child.filter(_.label == "target").
      map(_.head.child.filter(x => x.label == "box" || x.label == "attribute").
        map(extractData(wi, hi, _))).
      map(map2OutputStream(_))
  }

  private def WriteResult2TargetFile(file: File, x: Seq[String], i: Int) = {
    val target = File.apply(s"$TARGET_DIRECTORY${file.name.substring(0, 9)}img${getOrder(i)}.txt")
//    if (!target.exists) {
      target.createFile()
      val writer = target.writer(false, "UTF-8")
      x.foreach(writer.write(_))
      writer.flush()
//    }

  }

  private def getOrder(i: Int): String = {
    (i > 999, i > 99, i > 9) match {
      case (true, _, _) => s"0$i"
      case (false, true, _) => s"00$i"
      case (false, false, true) => s"000$i"
      case _ => s"0000$i"
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
      case List(a, b, c, d) => ((a+c/2) / wi, (b+d/2) / hi, math.min(1, c / wi), math.min(1, d / hi)) match {
        case (x1, y1, x2, y2) => s"$x1 $y1 $x2 $y2"
      }
    }
  }

  private def map2CarType(x: Node) = {
    x.attributes("vehicle_type").text match {
      case "car" => "0"
      case "van" => "1"
      case "bus" => "2"
      case "others" => "3"
    }
  }

  private def mapCountCarType(x: Node) = {
    x.attributes("vehicle_type").text match {
      case "car" => car += 1
      case "van" => van += 1
      case "bus" => bus += 1
      case "others" => other += 1
    }
    val total: Double = car + van + other + bus
    println(s"$car(${car / total}) $van(${van / total}) $bus(${bus / total}) $other(${other / total})")
  }
}

