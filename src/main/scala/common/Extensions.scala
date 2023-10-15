package pl.piasta.lincalc.scala
package common

import common.Constants.{DECIMAL_DOT, EMPTY, NAN, ZERO}

import javafx.beans.binding.{Bindings, BooleanBinding}
import javafx.geometry.Insets
import scalafx.application.Platform.runLater
import scalafx.beans.property.StringProperty
import scalafx.concurrent.Task
import scalafx.scene.control.TextInputControl
import scalafx.scene.text.Font

import scala.util.Using
import scala.util.Using.resource

object Extensions {
    extension (value: String)
        def isNaN: Boolean = value == NAN

        def stripTrailingZeros: String = if value.contains(DECIMAL_DOT) && value.endsWith(ZERO) then
            val result = value.dropRightWhile(_ == '0')
            if result.endsWith(DECIMAL_DOT) then result.dropRight(1) else result
        else value

        def takeLastUntilFormerAndLatter(former: Char => Boolean, latter: Char => Boolean): String = {
            (value.length - 1 to 0 by -1).find(index =>
                index > 0 && former(value.charAt(index)) && latter(value.charAt(index - 1))
            ) match
                case Some(index) => value.substring(index)
                case None => value
        }

        def toBigDecimal: BigDecimal = {
            val expression = value match
                case v if v.isNaN => ZERO
                case v if v.endsWith(DECIMAL_DOT) => value.dropRight(1)
                case _ => value
            BigDecimal(expression)
        }

        def toFont(size: Double): Font = resource(getClass.getResourceAsStream(value)) { in =>
            Font.loadFont(in, size)
        }

        def startsWith(char: Char): Boolean = value.nonEmpty && value.charAt(0) == char

        def endsWith(char: Char): Boolean = value.nonEmpty && value.last == char

        private def dropRightWhile(predicate: Char => Boolean): String = {
            (value.length - 1 to 0 by -1).find(index => !predicate(value.charAt(index))) match
                case Some(index) => value.substring(0, index + 1)
                case None => EMPTY
        }

    extension (value: Double)
        def coerceAtLeast(minimumValue: Double): Double = if value < minimumValue then minimumValue else value

    extension (value: Option[String])
        def isNoneOrEmpty: Boolean = value match {
            case Some(value) => value.isEmpty
            case None => true
        }

        def orDefault(default: String): String = if value.isNoneOrEmpty then default else value.get

    extension (value: StringProperty)
        def isNaNValue: BooleanBinding = Bindings.equal(NAN, value)

        def valueOrDefault(default: String): String = if !value.getValue.isNaN then value.getValue else default

    extension (value: TextInputControl)
        def deselect(): Unit = value.selectRange(value.getCaretPosition, value.getCaretPosition)

    extension[T] (task: Task[T])
        def runAsync(withDaemon: Boolean = false)(onUi: T => Unit = (_: T) => ()): Unit = {
            task.onFailed = _ => task.getException.printStackTrace(System.err)
            task.onSucceeded = _ => runLater {
                onUi(task.getValue)
            }
            new Thread(task) {
                setDaemon(withDaemon)
            }.start()
        }

    extension (value: Insets)
        def horizontal: Double = (value.getLeft + value.getRight) / 2
}
