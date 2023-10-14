package pl.piasta.lincalc.scala
package ui.control

import common.Constants.{DIGITAL_FONT, FONT_ASSETS}
import common.Extensions.{coerceAtLeast, deselect, horizontal, runAsync, toFont}

import javafx.beans.value.ChangeListener
import scalafx.beans.property.StringProperty
import scalafx.concurrent.Task
import scalafx.scene.Cursor.Hand
import scalafx.scene.control.TextField
import scalafx.scene.text.{Font, Text}

private val FONT_SIZE_MIN = 18.0
private val digitalFont = s"$FONT_ASSETS/$DIGITAL_FONT".toFont(64.0)

class DigitalScreen(private val property: StringProperty) extends TextField {
    editable = false
    font = digitalFont
    cursor = Hand
    text.bind(property)
    text.addListener((_, oldValue, newValue) => {
        if (oldValue.nonEmpty) updateAsync(newValue)
    })
    selectedText.addListener((_, _, _) => this.deselect())
    Array(width, height).foreach { element =>
        element.onChange((_, oldValue, _) => if oldValue != 0 then updateAsync(this.getText))
    }
    styleClass += "digital-screen"

    private def updateAsync(value: String): Unit = {
        Task(calculateFont(value)).runAsync(true) { newFont =>
            this.font = newFont
            this.positionCaret(this.getText.length)
        }
    }

    private def calculateFont(newValue: String): Font = {
        val tmpText = new Text(newValue) {
            font = digitalFont
        }
        val textWidth = tmpText.getLayoutBounds.getWidth
        val maxWidth = this.getWidth - 2 * this.getPadding.horizontal - 2
        if (textWidth > maxWidth) {
            val calculatedSize = digitalFont.size * maxWidth / textWidth
            Font.font(digitalFont.family, calculatedSize.coerceAtLeast(FONT_SIZE_MIN))
        } else digitalFont
    }
}
