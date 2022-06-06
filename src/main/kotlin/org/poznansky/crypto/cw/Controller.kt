package org.poznansky.crypto.cw

import javafx.event.ActionEvent
import javafx.fxml.Initializable
import javafx.fxml.FXML
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.Pane
import javafx.stage.FileChooser
import java.math.BigInteger
import java.net.URL
import java.nio.file.Path
import java.util.*

class Controller : Initializable {

    @FXML
    lateinit var paneMain: Pane

    @FXML
    lateinit var inpPaR: TextField

    @FXML
    lateinit var inpPaN: TextField

    @FXML
    lateinit var inpPaP: TextField

    @FXML
    lateinit var inpPaQ: TextField

    @FXML
    lateinit var inpPaY: TextField

    @FXML
    lateinit var InpPaKey: TextField

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
    fun onKeygen(actionEvent: ActionEvent?) {
        when (choiceBoxMeth.value) {
            "Benaloh" -> {
                val keys = generateBenaloh()
                inpPaY.text = keys.pub.y.toString()
                inpPaR.text = keys.pub.r.toString()
                inpPaN.text = keys.pub.n.toString()
                inpPaP.text = keys.priv.p.toString()
                inpPaQ.text = keys.priv.q.toString()
            }
            "Camellia" -> {
                val key = BigInteger(128, Random())
                InpPaKey.text = key.toString(10)
            }
        }
    }

    @FXML
    fun onEncrypt(actionEvent: ActionEvent?) {
        outputFile?.let { outfile ->
            inputFile?.let { infile ->
                when (choiceBoxMeth.value) {
                    "Benaloh" -> {
                        val pubk = PublicKey(
                            BigInteger(inpPaY.text, 10),
                            BigInteger(inpPaR.text, 10),
                            BigInteger(inpPaN.text, 10)
                        )
                        encryptBenaloh(pubk, infile, outfile)
                    }
                    "Camellia" -> {
                        val key = BigInteger(InpPaKey.text, 10)
                        encryptCamellia(key, infile, outfile)
                    }
                }
            }
        }
    }

    @FXML
    fun onDecrypt(actionEvent: ActionEvent?) {
        outputFile?.let { outfile ->
            inputFile?.let { infile ->
                when (choiceBoxMeth.value) {
                    "Benaloh" -> {
                        val pubk = PublicKey(
                            BigInteger(inpPaY.text, 10),
                            BigInteger(inpPaR.text, 10),
                            BigInteger(inpPaN.text, 10)
                        )
                        val privk = PrivateKey(
                            BigInteger(inpPaP.text, 10),
                            BigInteger(inpPaQ.text, 10)
                        )
                        val keys = Keys(pubk, privk)
                        decryptBenaloh(keys, infile, outfile)
                    }
                    "Camellia" -> {
                        val key = BigInteger(InpPaKey.text, 10)
                        decryptCamellia(key, infile, outfile)
                    }
                }
            }
        }
    }
}