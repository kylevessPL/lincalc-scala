package pl.piasta.lincalc.scala
package ui.control

import common.extensions.Extensions.{useMaxHeight, useMaxWidth}

import scalafx.scene.control.Button

open class CircleButton(text: String) extends Button(text) {
    useMaxWidth = true
    useMaxHeight = true
    styleClass += "circle-btn"
}
