package org.poznansky.crypto.cw

import java.math.BigInteger
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

//    val key = BigInteger("0123456789abcdeffedcba9876543210", 16)
//    val start = BigInteger(
//        "0123456789abcdeffedcba9876543210", 16
//    )

//    val encr = encryptCamellia(key, start)
//    val decr = decryptCamellia(key, encr)
//    if (start == decr)
//        println(encr.toString(16))
//    else
//        println("Govno")

    /*val key1 = BigInteger("0123456789abcdeffedcba9876543210", 16)
    val rawData = BigInteger("0123456789abcdeffedcba9876543210", 16)
    val encryptedData = encryptCamelliaBLOCK(key1, rawData)
    val decryptedData = decryptCamelliaBLOCK(key1, encryptedData)
    if(rawData==decryptedData)
    {
        println("RAW")
        println(rawData.toByteArray().joinToString(", "))
        println(rawData.toString(10))
        println("ENCR")
        println(encryptedData.toByteArray().joinToString(", "))
        println(encryptedData.toString(10))
    }

    println("STEP2")




    //Files.write(Path.of(TEXT_FILE), "555555555555555555555555555555550123456789abcdeffedcba987654321055555555555555555555555555555555".decodeHex())
    val key = BigInteger("0123456789abcdeffedcba9876543210", 16)
    encryptCamellia(key, Path.of(CAT_FILE), Path.of(MIDDLE_FILE))
    decryptCamellia(key, Path.of(MIDDLE_FILE), Path.of(OUT_CAT))
    if(isEqual(Path.of(CAT_FILE), Path.of(OUT_CAT))){
        println("OK")
    }


    println("Finish")*/
        val cipherText = "CipherText"
        val key = "10102312"
    val CTbyte = cipherText.toByteArray()
    val Keybyte = key.toByteArray()
    val enc = MARS.encrypt(CTbyte, Keybyte)

    val dec = MARS.decrypt(enc, Keybyte);

    println("OK")

}