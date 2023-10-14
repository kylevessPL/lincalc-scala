package pl.piasta.lincalc.scala
package ui.view.fragment

import ui.control.DigitalScreen
import ui.view.{UIComponent, ViewModel}

import scalafx.scene.Parent
import scalafx.scene.layout.HBox
import scalafx.scene.layout.Priority.Always

class ScreenFragment(private val vm: ViewModel) extends UIComponent {
    override val title: String = "Screen Fragment"

    override val root: Parent = new HBox {
        children += new DigitalScreen(vm.displayValue) {
            hgrow = Always
        }
    }
}
