package xin.saul.gui

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.property.{DoubleProperty, StringProperty}
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{ChoiceDialog, Label}
import scalafx.scene.layout.BorderPane

object Main extends JFXApp {
      stage = new PrimaryStage {
        title = "Hello"
        scene = new Scene() {
          root = new BorderPane() {
            padding = Insets(25)
            center = new Label("Hello World")
          }
        }
      }
    val k = DoubleProperty(1)
    val n = DoubleProperty(1)
    val o = DoubleProperty(1)
    o <== k + n
    k.onChange((_, _, _) => print(s"${o()} ${n()}"))
    k() = 9.0;
    val a = new StringProperty
    val b = new StringProperty
    import scalafx.Includes._
    b <== when(a.length() > 0) choose a otherwise a.length().toString
    a() = "sss"
    print(b.value)
//    new Alert(AlertType.Information,"HELLO").showAndWait()
//  }
val choices = Seq("a", "b", "c")
  val dialog = new ChoiceDialog[String](defaultChoice = "b", choices = choices) {
    initOwner(stage)
    title = "Choice Dialog"
    headerText = "Look, a Choice Dialog."
    contentText = "Choose your letter:"
  }

  val result = dialog.showAndWait()

  result match {
    case Some(choice) => println("Your choice: " + choice)
    case None         => println("No selection")
  }
}
