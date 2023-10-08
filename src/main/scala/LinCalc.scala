package pl.piasta.lincalc.scala

import common.Constants.IMAGE_ASSETS

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.image.Image

object LinCalc extends JFXApp3 {
    override def start(): Unit = {
        stage = new PrimaryStage {
            minWidth = 321.0
            minHeight = 508.0
            icons += new Image(s"$IMAGE_ASSETS/favicon.png")
        }
    }
}
