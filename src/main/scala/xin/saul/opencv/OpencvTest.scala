package xin.saul.opencv

import org.opencv.core.{Core, CvType, Mat, Size}
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.videoio.VideoCapture


object OpencvTest {
  System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
  def main(args: Array[String]): Unit = {
    var capture = new VideoCapture("http://127.0.0.1:8090")
    val mat =new Mat(new Size(800,448),CvType.CV_16SC3)
    println(capture.isOpened)
    while(capture.isOpened){
      println(capture.isOpened)
      capture.read(mat)
      Imgcodecs.imwrite("kkk.jpg", mat)
//      capture = new VideoCapture("http://127.0.0.1:8090")
    }
  }
}
