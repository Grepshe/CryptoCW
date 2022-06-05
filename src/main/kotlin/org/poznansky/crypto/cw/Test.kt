package org.poznansky.crypto.cw

import java.math.BigInteger
import java.nio.file.Path

val CAT_FILE = "cat.jpg"
val MIDDLE_FILE = "decrypted"
val OUT_CAT = "encrcat.jpg"
val TEXT_FILE = "test.txt"
val TEXT_OUT = "encrtext.txt"

fun main(args: Array<String>) {
//    val keys = generateBenaloh()
//    val (pub, priv) = keys
//    encryptBenaloh(pub, Path.of(CAT_FILE), Path.of(MIDDLE_FILE))
//    decryptBenaloh(keys, Path.of(MIDDLE_FILE), Path.of(OUT_CAT))
    val start = BigInteger("0123456789abcdeffedcba9876543210", 16)
    val encr = encryptCamellia(BigInteger("0123456789abcdeffedcba9876543210", 16), start)
    val decr = decryptCamellia(BigInteger("0123456789abcdeffedcba9876543210", 16), encr)
    if()
    println("Hi")
}