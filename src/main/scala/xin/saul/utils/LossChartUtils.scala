package xin.saul.utils

import scala.io.Source

object LossChartUtils {
  val DATA_PATH = "d:\\pipi\\ms\\20180408data.txt"

  val ECODING = "utf-8"

  val REG_EXP = "( [0-9]{1,5}:|[^0-9.]*)"

//  def main(args: Array[String]): Unit = {
//    var i =300
//    val loss = mapLog2List(DATA_PATH).drop(i).map(_(1)).toList
//    import scalax.chart.api._
//    //    XYLineChart(loss).saveAsSVG("img\\chart.svg")
//    XYLineChart(Stream.from(i,1).zip(loss)).saveAsSVG("img\\chart0408.SVG")
////    XYLineChart(Stream.from(i,1).zip(loss)).saveAsJPEG("img\\chart.jpg")
//        scalax.chart.api.XYAreaChart(Stream.from(i,1).zip(loss)).saveAsSVG("img\\ring0408.svg")
//  }


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
