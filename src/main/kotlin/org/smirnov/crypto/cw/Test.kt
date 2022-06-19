package org.smirnov.crypto.cw

import java.nio.file.Files
import java.nio.file.Path

val CAT_FILE = "cat.jpg"
val MIDDLE_FILE = "decrypted"
val OUT_CAT = "encrcat.jpg"
val TEXT_FILE = "test.txt"
val TEXT_OUT = "encrtext.txt"

fun String.decodeHex(): ByteArray {
    check(length % 2 == 0) { "Must have an even length" }

    return chunked(2)
        .map { it.toInt(16).toByte() }
        .toByteArray()
}

private fun isEqual(firstFile: Path, secondFile: Path): Boolean {
    if (Files.size(firstFile) != Files.size(secondFile)) {
        return false
    }
    val first = Files.readAllBytes(firstFile)
    val second = Files.readAllBytes(secondFile)
    return first!!.contentEquals(second)
}

fun main(args: Array<String>) {
//    val keys = generateBenaloh()
//    val (pub, priv) = keys
//    encryptBenaloh(pub, Path.of(CAT_FILE), Path.of(MIDDLE_FILE))
//    decryptBenaloh(keys, Path.of(MIDDLE_FILE), Path.of(OUT_CAT))
    val cipherText = "CipherText"
    val key = "10102312"
    val CTbyte = cipherText.toByteArray()
    val Keybyte = key.toByteArray()
    val enc = MARS.encrypt(CTbyte, Keybyte)

    val dec = MARS.decrypt(enc, Keybyte);

    println("OK")

}