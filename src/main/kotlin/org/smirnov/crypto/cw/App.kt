package org.smirnov.crypto.cw

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class App : Application() {
    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.getResource("/design.fxml"))
        primaryStage.title = "SMIRNOV KRIPTA"
        primaryStage.scene = Scene(root, 1000.0, 600.0)
        primaryStage.isMaximized = true
        primaryStage.show()
    }

}

fun main(args: Array<String>) {
    Application.launch(*args)
}
