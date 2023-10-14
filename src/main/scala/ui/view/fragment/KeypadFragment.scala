package pl.piasta.lincalc.scala
package ui.view.fragment

import common.Extensions.isNaNValue
import math.MathFunction.{Cosine, Negation, Percentage, Sine, SquareRoot}
import math.MathOperator.{Add, Divide, Multiply, Subtract}
import ui.control.{ClearButton, DigitInputButton, FunctionButton, InputFormatButton, OperatorButton, TrigonometricFunctionButton}
import ui.view.{UIComponent, ViewModel}

import javafx.event.ActionEvent
import scalafx.scene.layout.Priority.Always
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, RowConstraints}
import scalafx.scene.{Node, Parent}

class KeypadFragment(private val vm: ViewModel) extends UIComponent {
    override val title: String = "Keypad Fragment"

    override val root: Parent = new GridPane {
        add(new ClearButton("CE") {
            onAction = (_: ActionEvent) => vm.clear()
        }, 0, 0)
        add(new ClearButton("C") {
            onAction = (_: ActionEvent) => vm.clearAll()
        }, 1, 0)
        add(new TrigonometricFunctionButton("sin") {
            onAction = (_: ActionEvent) => vm.convertByFunction(Sine)
            disableWhenNaN(this)
        }, 2, 0)
        add(new TrigonometricFunctionButton("cos") {
            onAction = (_: ActionEvent) => vm.convertByFunction(Cosine)
            disableWhenNaN(this)
        }, 3, 0)
        add(new FunctionButton("⁺∕₋") {
            onAction = (_: ActionEvent) => vm.convertByFunction(Negation)
            disableWhenNaN(this)
        }, 0, 1)
        add(new FunctionButton("%") {
            onAction = (_: ActionEvent) => vm.convertByFunction(Percentage)
            disableWhenNaN(this)
        }, 1, 1)
        add(new FunctionButton("√") {
            onAction = (_: ActionEvent) => vm.convertByFunction(SquareRoot)
            disableWhenNaN(this)
        }, 2, 1)
        add(new OperatorButton("÷") {
            onAction = (_: ActionEvent) => vm.doMathOperation(Divide)
            disableWhenNaN(this)
        }, 3, 1)
        add(new DigitInputButton("7") {
            onAction = (_: ActionEvent) => vm.doInput(text.getValue)
        }, 0, 2)
        add(new DigitInputButton("8") {
            onAction = (_: ActionEvent) => vm.doInput(text.getValue)
        }, 1, 2)
        add(new DigitInputButton("9") {
            onAction = (_: ActionEvent) => vm.doInput(text.getValue)
        }, 2, 2)
        add(new OperatorButton("×") {
            onAction = (_: ActionEvent) => vm.doMathOperation(Multiply)
            disableWhenNaN(this)
        }, 3, 2)
        add(new DigitInputButton("4") {
            onAction = (_: ActionEvent) => vm.doInput(text.getValue)
        }, 0, 3)
        add(new DigitInputButton("5") {
            onAction = (_: ActionEvent) => vm.doInput(text.getValue)
        }, 1, 3)
        add(new DigitInputButton("6") {
            onAction = (_: ActionEvent) => vm.doInput(text.getValue)
        }, 2, 3)
        add(new OperatorButton("-") {
            onAction = (_: ActionEvent) => vm.doMathOperation(Subtract)
            disableWhenNaN(this)
        }, 3, 3)
        add(new DigitInputButton("1") {
            onAction = (_: ActionEvent) => vm.doInput(text.getValue)
        }, 0, 4)
        add(new DigitInputButton("2") {
            onAction = (_: ActionEvent) => vm.doInput(text.getValue)
        }, 1, 4)
        add(new DigitInputButton("3") {
            onAction = (_: ActionEvent) => vm.doInput(text.getValue)
        }, 2, 4)
        add(new OperatorButton("+") {
            onAction = (_: ActionEvent) => vm.doMathOperation(Add)
            disableWhenNaN(this)
        }, 3, 4)
        add(new DigitInputButton("0") {
            onAction = (_: ActionEvent) => vm.doInput(text.getValue)
        }, 0, 5)
        add(new InputFormatButton(".") {
            onAction = (_: ActionEvent) => vm.convertToDecimal()
            disableWhenNaN(this)
        }, 1, 5)
        add(new InputFormatButton("⌫") {
            onAction = (_: ActionEvent) => vm.undoInput()
            disableWhenNaN(this)
        }, 2, 5)
        add(new OperatorButton("=") {
            onAction = (_: ActionEvent) => vm.evaluate()
            disableWhenNaN(this)
        }, 3, 5)
        vgrow = Always
        styleClass += "keypad-pane"
        columnConstraints = (0 to 3).map { _ =>
            new ColumnConstraints() {
                hgrow = Always
                percentWidth = 25.0
            }
        }
        rowConstraints = (0 to 5).map { _ =>
            new RowConstraints() {
                vgrow = Always
            }
        }
    }

    private def disableWhenNaN(node: Node): Unit = node.disable.bind(vm.displayValue.isNaNValue)
}
