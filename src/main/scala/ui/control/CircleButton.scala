package pl.piasta.lincalc.scala
package ui.control

import scalafx.scene.control.Button

open class CircleButton(text: String) extends Button(text) {
    maxWidth = Double.MaxValue
    maxHeight = Double.MaxValue
    styleClass += "circle-btn"
}
