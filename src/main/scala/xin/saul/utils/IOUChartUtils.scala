package xin.saul.utils

import scala.io.Source

object IOUChartUtils {
  val DATA_PATH = "d:\\pipi\\ms\\20180408iou.txt"

  val ECODING = "utf-8"

  val REG_EXP = "[^0-9.]*"

  def main(args: Array[String]): Unit = {
    val i =0
    val loss = mapLog2List(DATA_PATH).map(_(0)).sliding(128,128).map(x=>x.sum/x.size).drop(i).toList
    import scalax.chart.api._
//        XYLineChart(loss).saveAsSVG("img\\chart.svg")
    XYLineChart(Stream.from(i,1).zip(loss)).saveAsSVG("img\\IOUchart0408.SVG")
//    XYLineChart(Stream.from(i,1).zip(loss)).saveAsJPEG("img\\chart.jpg")
//        scalax.chart.api.XYAreaChart(Stream.from(i,1).zip(loss)).saveAsSVG("img\\ring0408.svg")
  }


  private def mapLog2List(datapath: String) = {
    Source.fromFile(datapath, ECODING).
      getLines().
      map(_.split(",")).
      map(
        _.map(
          _.replaceAll(REG_EXP, "")
        )
      ).map(_.map(_.toDouble))
  }
}
