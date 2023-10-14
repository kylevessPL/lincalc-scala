package pl.piasta.lincalc.scala
package ui.view

import scalafx.scene.Parent

trait UIComponent {
    val root: Parent
    val title: String
}
