package pl.piasta.lincalc.scala

import common.Constants.IMAGE_ASSETS
import ui.view.MainView

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.image.Image

object LinCalc extends JFXApp3 {
    private lazy val mainView = MainView()

    override def start(): Unit = {
        stage = new PrimaryStage {
            minWidth = 321.0
            minHeight = 508.0
            icons += new Image(s"$IMAGE_ASSETS/favicon.png")
            scene = new Scene(mainView.root)
            title = mainView.title
        }
    }
}
