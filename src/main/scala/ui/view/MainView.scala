package pl.piasta.lincalc.scala
package ui.view

import common.Constants.STYLESHEET
import ui.view.fragment.{KeypadFragment, ScreenFragment}

import scalafx.scene.Parent
import scalafx.scene.layout.VBox

class MainView extends UIComponent {
    private val vm = ViewModel()

    private val screenFragment = ScreenFragment(vm)
    private val keypadFragment = KeypadFragment(vm)

    override val title: String = "LinCalc"

    val root: Parent = new VBox {
        stylesheets += getClass.getResource(STYLESHEET).toExternalForm
        children = Seq(screenFragment.root, keypadFragment.root)
    }
}
