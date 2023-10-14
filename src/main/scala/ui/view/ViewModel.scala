package pl.piasta.lincalc.scala
package ui.view

import common.Constants.{DECIMAL_DOT, EMPTY, NAN, SIGN_EQUALS, SIGN_MINUS, ZERO}
import common.Extensions.{endsWith, isNaN, isNoneOrEmpty, orDefault, runAsync, startsWith, stripTrailingZeros, takeLastWhileInclusive, toBigDecimal, valueOrDefault}
import math.MathExtensions.{cos, percentage, sin, sqrt}
import math.MathFunction.{Cosine, Negation, Percentage, Sine, SquareRoot}
import math.{MathEvaluator, MathFunction, MathOperator}

import scalafx.beans.property.StringProperty
import scalafx.concurrent.Task

import scala.util.Try

class ViewModel {
    val displayValue: StringProperty = StringProperty(ZERO)
    private var currentExpression = EMPTY
    private var latestInput: Option[String] = None

    def clear(): Unit = displayValue.getValue match
        case value if !value.isNaN =>
            displayValue.setValue(ZERO)
            latestInput = None
        case _ => clearAll()

    def clearAll(): Unit = {
        displayValue.setValue(ZERO)
        currentExpression = EMPTY
        latestInput = None
    }

    def doInput(input: String): Unit = {
        if currentExpression.endsWith(SIGN_EQUALS) then currentExpression = EMPTY
        val value = displayValue.valueOrDefault(ZERO) match
            case display if display != ZERO || input != ZERO =>
                if display != ZERO && latestInput.contains(display) then display + input
                else input
            case _ => ZERO
        displayValue.setValue(value)
        latestInput = Some(value)
        if currentExpression.endsWith(SIGN_EQUALS) then currentExpression = EMPTY
    }

    def undoInput(): Unit = latestInput.foreach { value =>
        val result = value.dropRight(1) match
            case drop if drop != EMPTY && drop != s"$SIGN_MINUS$ZERO" => drop
            case _ => ZERO
        displayValue.setValue(result)
        latestInput = Some(result)
    }

    def evaluate(): Unit = if currentExpression.nonEmpty then
        Task[String] {
            if MathOperator.values.exists { op => currentExpression.lastOption.contains(op.sign) } then
                currentExpression += displayValue.getValue
            else
                val part = currentExpression.dropRight(1).takeLastWhileInclusive { ch =>
                    !MathOperator.values.exists {
                        ch == _.sign
                    }
                }
                currentExpression = displayValue.getValue + part
            MathEvaluator.evaluateExpression(currentExpression) match
                case Some(value) => value.toString
                case None => NAN
        }.runAsync() { value =>
            displayValue.setValue(value.stripTrailingZeros)
            currentExpression += SIGN_EQUALS
            latestInput = None
        }

    def doMathOperation(operation: MathOperator): Unit = {
        Task[Option[String]] {
            if latestInput.isNoneOrEmpty && MathOperator.values.exists { op => currentExpression.lastOption.contains(op.sign) } then
                currentExpression = currentExpression.dropRight(1) + operation.sign
                None
            else
                if currentExpression.endsWith(SIGN_EQUALS) then currentExpression = EMPTY
                currentExpression += displayValue.getValue + operation.sign
                MathEvaluator.evaluateExpression(currentExpression.dropRight(1)) match
                    case Some(value) => Some(value.toString)
                    case None => Some(NAN)
        }.runAsync() { value =>
            value.foreach { res => displayValue.setValue(res.stripTrailingZeros) }
            latestInput = None
        }
    }

    def convertToDecimal(): Unit = {
        if currentExpression.endsWith(SIGN_EQUALS) then clearAll()
        val value = latestInput.orDefault(ZERO)
        if !value.contains(DECIMAL_DOT) then
            displayValue.setValue(value + DECIMAL_DOT)
            latestInput = Some(displayValue.getValue)
    }

    def convertByFunction(function: MathFunction): Unit = {
        Task[String] {
            val number = displayValue.getValue.toBigDecimal
            Try {
                function match
                    case Sine => number.sin.toString()
                    case Cosine => number.cos.toString()
                    case SquareRoot => number.sqrt.toString()
                    case Percentage => number.percentage.toString()
                    case Negation => negateInput()
            }.getOrElse(NAN)
        }.runAsync(true) { value =>
            displayValue.setValue(value.stripTrailingZeros)
            if currentExpression.endsWith(SIGN_EQUALS) then currentExpression = EMPTY
            if function != Negation then latestInput = None
        }
    }

    private def negateInput() = if displayValue.getValue != ZERO then
        displayValue.getValue match
            case value if value.startsWith(SIGN_MINUS) => value.drop(1)
            case _ => s"$SIGN_MINUS${displayValue.getValue}"
    else displayValue.getValue
}
