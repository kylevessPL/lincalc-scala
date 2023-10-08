package pl.piasta.lincalc.scala
package common.extensions

import javafx.geometry.Insets
import scalafx.application.Platform
import scalafx.application.Platform.runLater
import scalafx.concurrent.Task
import scalafx.scene.layout.Region
import scalafx.scene.text.Font

import java.lang.invoke.MethodHandles
import scala.util.Using

object Extensions {
    extension (value: String)
        def toFont(size: Double): Font = Using(MethodHandles.lookup().lookupClass().getResourceAsStream(value)) { in =>
            Font.loadFont(in, size)
        }

    extension (value: Double)
        def coerceAtLeast(minimumValue: Double): Double = if (value < minimumValue) minimumValue else value

    extension[T] (task: Task[T])
        def runAsync(withDaemon: Boolean = false)(onUi: T => Unit = _ => ()): Unit = {
            task.onSucceeded = _ => runLater {
                onUi(task.value)
            }
            new Thread(task) {
                daemon = withDaemon
            }.start()
        }

    extension (value: Insets)
        def horizontal: Double = (value.left + value.right) / 2

    extension (value: Region)
        def useMaxWidth: Boolean = value.maxWidth == Double.MaxValue
        def useMaxWidth_=(value: Boolean): Unit = {
            if (value) value.maxWidth = Double.MaxValue
        }

        def useMaxHeight: Boolean = value.maxHeight == Double.MaxValue
        def useMaxHeight_=(value: Boolean): Unit = {
            if (value) value.maxHeight = Double.MaxValue
        }
}
