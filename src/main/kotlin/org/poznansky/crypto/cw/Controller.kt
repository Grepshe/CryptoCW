package org.poznansky.crypto.cw

import javafx.event.ActionEvent
import javafx.fxml.Initializable
import java.util.ResourceBundle
import javafx.fxml.FXML
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.Pane
import javafx.stage.FileChooser
import java.math.BigInteger
import java.net.URL
import java.nio.file.Path

class Controller : Initializable {

    @FXML
    lateinit var paneMain: Pane

    @FXML
    lateinit var textInputPanel: TextField

    @FXML
    lateinit var textOutputPanel: TextArea

    @FXML
    lateinit var choiceBoxMeth: ChoiceBox<String>

    var inputFile: Path? = null

    var outputFile: Path? = null

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        choiceBoxMeth.items.addAll("Benaloh", "Camellia")
        choiceBoxMeth.value = "Benaloh"
    }

    @FXML
    fun onOpenFile(actionEvent: ActionEvent?) {
        val window = paneMain.scene.window
        val fileChooser = FileChooser()
        inputFile = fileChooser.showOpenDialog(window).toPath()
    }

    @FXML
    fun onSaveFile(actionEvent: ActionEvent?) {
        val window = paneMain.scene.window
        val fileChooser = FileChooser()
        outputFile = fileChooser.showSaveDialog(window).toPath()
    }

    @FXML
    fun onEncrypt(actionEvent: ActionEvent?) {
        inputFile?.let { f ->
            when (choiceBoxMeth.value) {
                "Benaloh" -> {
//                    val (p, q, r) = TODO()
//                    textOutputPanel.text = "p:\n$p\n\nq:\n$q\n\nr:\n$r\n\n"
                }
                else -> {}
            }
        }
    }

    @FXML
    fun onDecrypt(actionEvent: ActionEvent?) {
//        outputFile?.let { f ->
//            when (choiceBoxMeth.value) {
//                "Benaloh" -> decryptBenaloh(f, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE)
//            }
//        }
    }
}