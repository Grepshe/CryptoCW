package org.poznansky.crypto.cw

import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path


val SBOX1ARR = arrayOf(
    intArrayOf(112, 130, 44, 236, 179, 39, 192, 229, 228, 133, 87, 53, 234, 12, 174, 65),
    intArrayOf(35, 239, 107, 147, 69, 25, 165, 33, 237, 14, 79, 78, 29, 101, 146, 189),
    intArrayOf(134, 184, 175, 143, 124, 235, 31, 206, 62, 48, 220, 95, 94, 197, 11, 26),
    intArrayOf(166, 225, 57, 202, 213, 71, 93, 61, 217, 1, 90, 214, 81, 86, 108, 77),
    intArrayOf(139, 13, 154, 102, 251, 204, 176, 45, 116, 18, 43, 32, 240, 177, 132, 153),
    intArrayOf(223, 76, 203, 194, 52, 126, 118, 5, 109, 183, 169, 49, 209, 23, 4, 215),
    intArrayOf(20, 88, 58, 97, 222, 27, 17, 28, 50, 15, 156, 22, 83, 24, 242, 34),
    intArrayOf(254, 68, 207, 178, 195, 181, 122, 145, 36, 8, 232, 168, 96, 252, 105, 80),
    intArrayOf(170, 208, 160, 125, 161, 137, 98, 151, 84, 91, 30, 149, 224, 255, 100, 210),
    intArrayOf(16, 196, 0, 72, 163, 247, 117, 219, 138, 3, 230, 218, 9, 63, 221, 148),
    intArrayOf(135, 92, 131, 2, 205, 74, 144, 51, 115, 103, 246, 243, 157, 127, 191, 226),
    intArrayOf(82, 155, 216, 38, 200, 55, 198, 59, 129, 150, 111, 75, 19, 190, 99, 46),
    intArrayOf(233, 121, 167, 140, 159, 110, 188, 142, 41, 245, 249, 182, 47, 253, 180, 89),
    intArrayOf(120, 152, 6, 106, 231, 70, 113, 186, 212, 37, 171, 66, 136, 162, 141, 250),
    intArrayOf(114, 7, 185, 85, 248, 238, 172, 10, 54, 73, 42, 104, 60, 56, 241, 164),
    intArrayOf(64, 40, 211, 123, 187, 201, 67, 193, 21, 227, 173, 244, 119, 199, 128, 158)
)

val MASK8 = BigInteger("ff", 16)
val MASK32 = BigInteger("ffffffff", 16)
val MASK64 = BigInteger("ffffffffffffffff", 16)
val MASK128 = BigInteger("ffffffffffffffffffffffffffffffff", 16)
val C1 = BigInteger("A09E667F3BCC908B", 16)
val C2 = BigInteger("B67AE8584CAA73B2", 16)
val C3 = BigInteger("C6EF372FE94F82BE", 16)
val C4 = BigInteger("54FF53A5F1D36F1C", 16)
val C5 = BigInteger("10E527FADE682D1D", 16)
val C6 = BigInteger("B05688C2B3E6C1FD", 16)

fun encryptCamellia(key: BigInteger, input: Path, output: Path) {
    val KL = key
    val KR = BigInteger.ZERO
    val (KA, KB) = getKAKB(KL, KR)

    val kw1 = KL.cycleShiftLeft128(0) shr 64
    val kw2 = KL.cycleShiftLeft128(0) and MASK64
    val k1 = KA.cycleShiftLeft128(0) shr 64
    val k2 = KA.cycleShiftLeft128(0) and MASK64
    val k3 = KL.cycleShiftLeft128(15) shr 64
    val k4 = KL.cycleShiftLeft128(15) and MASK64
    val k5 = KA.cycleShiftLeft128(15) shr 64
    val k6 = KA.cycleShiftLeft128(15) and MASK64
    val ke1 = KA.cycleShiftLeft128(30) shr 64
    val ke2 = KA.cycleShiftLeft128(30) and MASK64
    val k7 = KL.cycleShiftLeft128(45) shr 64
    val k8 = KL.cycleShiftLeft128(45) and MASK64
    val k9 = KA.cycleShiftLeft128(45) shr 64
    val k10 = KL.cycleShiftLeft128(60) and MASK64
    val k11 = KA.cycleShiftLeft128(60) shr 64
    val k12 = KA.cycleShiftLeft128(60) and MASK64
    val ke3 = KL.cycleShiftLeft128(77) shr 64
    val ke4 = KL.cycleShiftLeft128(77) and MASK64
    val k13 = KL.cycleShiftLeft128(94) shr 64
    val k14 = KL.cycleShiftLeft128(94) and MASK64
    val k15 = KA.cycleShiftLeft128(94) shr 64
    val k16 = KA.cycleShiftLeft128(94) and MASK64
    val k17 = KL.cycleShiftLeft128(111) shr 64
    val k18 = KL.cycleShiftLeft128(111) and MASK64
    val kw3 = KA.cycleShiftLeft128(111) shr 64
    val kw4 = KA.cycleShiftLeft128(111) and MASK64

    fun ecryptBlock128(M: BigInteger): BigInteger {
        var D1 = M shr 64 // Шифруемое сообщение делится на две 64-битные части
        var D2 = M and MASK64
        D1 = D1 xor kw1 // Предварительное забеливание
        D2 = D2 xor kw2
        D2 = D2 xor F(D1, k1)
        D1 = D1 xor F(D2, k2)
        D2 = D2 xor F(D1, k3)
        D1 = D1 xor F(D2, k4)
        D2 = D2 xor F(D1, k5)
        D1 = D1 xor F(D2, k6)
        D1 = FL(D1, ke1) // FL

        D2 = FLINV(D2, ke2) // FLINV

        D2 = D2 xor F(D1, k7)
        D1 = D1 xor F(D2, k8)
        D2 = D2 xor F(D1, k9)
        D1 = D1 xor F(D2, k10)
        D2 = D2 xor F(D1, k11)
        D1 = D1 xor F(D2, k12)
        D1 = FL(D1, ke3) // FL

        D2 = FLINV(D2, ke4) // FLINV

        D2 = D2 xor F(D1, k13)
        D1 = D1 xor F(D2, k14)
        D2 = D2 xor F(D1, k15)
        D1 = D1 xor F(D2, k16)
        D2 = D2 xor F(D1, k17)
        D1 = D1 xor F(D2, k18)
        D2 = D2 xor kw3 // Финальное забеливание

        D1 = D1 xor kw4
        val C = (D2 shl 64) or D1

        return C
    }

    val bytes: ByteArray = Files.readAllBytes(input)

    val encrypted: ByteArray =
        bytes.asSequence().chunked(16)
            .map { chunk -> BigInteger( chunk.toByteArray()) }
            .onEach { check(it.toByteArray().size <= 16) }
            .map { ecryptBlock128(it) }
            .map { it.toByteArray() }
            .map { it.dropWhile { b -> b.toInt() == 0 }.toByteArray() }
            .onEach { check(it.size <= 16) }
            .map { ByteArray(16 - it.size) { 0 } + it }
            .reduce(ByteArray::plus)

    Files.write(output, encrypted)
}

fun decryptCamellia(key: BigInteger, input: Path, output: Path) {
    val KL = key
    val KR = BigInteger.ZERO
    val (KA, KB) = getKAKB(KL, KR)

    val kw3 = KL.cycleShiftLeft128(0) shr 64
    val kw4 = KL.cycleShiftLeft128(0) and MASK64
    val k18 = KA.cycleShiftLeft128(0) shr 64
    val k17 = KA.cycleShiftLeft128(0) and MASK64
    val k16 = KL.cycleShiftLeft128(15) shr 64
    val k15 = KL.cycleShiftLeft128(15) and MASK64
    val k14 = KA.cycleShiftLeft128(15) shr 64
    val k13 = KA.cycleShiftLeft128(15) and MASK64
    val ke4 = KA.cycleShiftLeft128(30) shr 64
    val ke3 = KA.cycleShiftLeft128(30) and MASK64
    val k12 = KL.cycleShiftLeft128(45) shr 64
    val k11 = KL.cycleShiftLeft128(45) and MASK64
    val k10 = KA.cycleShiftLeft128(45) shr 64
    val k9 = KL.cycleShiftLeft128(60) and MASK64
    val k8 = KA.cycleShiftLeft128(60) shr 64
    val k7 = KA.cycleShiftLeft128(60) and MASK64
    val ke2 = KL.cycleShiftLeft128(77) shr 64
    val ke1 = KL.cycleShiftLeft128(77) and MASK64
    val k6 = KL.cycleShiftLeft128(94) shr 64
    val k5 = KL.cycleShiftLeft128(94) and MASK64
    val k4 = KA.cycleShiftLeft128(94) shr 64
    val k3 = KA.cycleShiftLeft128(94) and MASK64
    val k2 = KL.cycleShiftLeft128(111) shr 64
    val k1 = KL.cycleShiftLeft128(111) and MASK64
    val kw1 = KA.cycleShiftLeft128(111) shr 64
    val kw2 = KA.cycleShiftLeft128(111) and MASK64

    fun decryptBlock128(M: BigInteger): BigInteger {
        var D1 = M shr 64 // Шифруемое сообщение делится на две 64-битные части
        var D2 = M and MASK64
        D1 = D1 xor kw1 // Предварительное забеливание
        D2 = D2 xor kw2
        D2 = D2 xor F(D1, k1)
        D1 = D1 xor F(D2, k2)
        D2 = D2 xor F(D1, k3)
        D1 = D1 xor F(D2, k4)
        D2 = D2 xor F(D1, k5)
        D1 = D1 xor F(D2, k6)
        D1 = FL(D1, ke1) // FL

        D2 = FLINV(D2, ke2) // FLINV

        D2 = D2 xor F(D1, k7)
        D1 = D1 xor F(D2, k8)
        D2 = D2 xor F(D1, k9)
        D1 = D1 xor F(D2, k10)
        D2 = D2 xor F(D1, k11)
        D1 = D1 xor F(D2, k12)
        D1 = FL(D1, ke3) // FL

        D2 = FLINV(D2, ke4) // FLINV

        D2 = D2 xor F(D1, k13)
        D1 = D1 xor F(D2, k14)
        D2 = D2 xor F(D1, k15)
        D1 = D1 xor F(D2, k16)
        D2 = D2 xor F(D1, k17)
        D1 = D1 xor F(D2, k18)
        D2 = D2 xor kw3 // Финальное забеливание

        D1 = D1 xor kw4
        val C = (D2 shl 64) or D1

        return C
    }

    val bytes: ByteArray = Files.readAllBytes(input)

    val decrypted = bytes.asSequence().chunked(16)
        .map { chunk -> BigInteger(ByteArray(1) { 0 } + chunk.toByteArray()) }
        .onEach { check(it >= BigInteger.ZERO) }
        .map { decryptBlock128(it) }
        .map { it.toByteArray() }
        .onEach { check(it.size <= 16) }
        .map { ByteArray(16 - it.size) { 0 } + it }
        .reduce(ByteArray::plus)

    Files.write(output, decrypted)
}

fun getKAKB(KL: BigInteger, KR: BigInteger): Pair<BigInteger, BigInteger> {
    var D1 = KL.xor(KR).shiftRight(64)
    var D2 = KL.xor(KR).and(MASK64)
    D2 = D2 xor F(D1, C1)
    D1 = D1 xor F(D2, C2)
    D1 = D1 xor (KL shr 64)
    D2 = D2 xor (KL and MASK64)
    D2 = D2 xor F(D1, C3)
    D1 = D1 xor F(D2, C4)
    val KA = D1 shl 64 or D2
    D1 = KA xor KR shr 64
    D2 = KA xor KR and MASK64
    D2 = D2 xor F(D1, C5)
    D1 = D1 xor F(D2, C6)
    val KB = D1 shl 64 or D2
    return Pair(KA, KB)
}

fun F(F_IN: BigInteger, KE: BigInteger): BigInteger {
    val x = F_IN.xor(KE)
    var t1 = x.shiftRight(56)
    var t2 = x.shiftRight(48).and(MASK8)
    var t3 = x.shiftRight(40).and(MASK8)
    var t4 = x.shiftRight(32).and(MASK8)
    var t5 = x.shiftRight(24).and(MASK8)
    var t6 = x.shiftRight(16).and(MASK8)
    var t7 = x.shiftRight(8).and(MASK8)
    var t8 = x.and(MASK8)
    t1 = SBOX1(t1)
    t2 = SBOX2(t2)
    t3 = SBOX3(t3)
    t4 = SBOX4(t4)
    t5 = SBOX2(t5)
    t6 = SBOX3(t6)
    t7 = SBOX4(t7)
    t8 = SBOX1(t8)
    val y1 = t1.xor(t3).xor(t4).xor(t6).xor(t7).xor(t8)
    val y2 = t1.xor(t2).xor(t4).xor(t5).xor(t7).xor(t8)
    val y3 = t1.xor(t2).xor(t3).xor(t5).xor(t6).xor(t8)
    val y4 = t2.xor(t3).xor(t4).xor(t5).xor(t6).xor(t7)
    val y5 = t1 xor t2 xor t6 xor t7 xor t8
    val y6 = t2 xor t3 xor t5 xor t7 xor t8
    val y7 = t3 xor t4 xor t5 xor t6 xor t8
    val y8 = t1 xor t4 xor t5 xor t6 xor t7

    val F_OUT = y1 shl 56 or (y2 shl 48) or (y3 shl 40) or (y4 shl 32) or (y5 shl 24) or (y6 shl 16) or (y7 shl 8) or y8

    return F_OUT
}

fun FL(FL_IN: BigInteger, KE: BigInteger): BigInteger {
    var x1 = FL_IN shr 32
    var x2 = FL_IN and MASK32
    var k1 = KE shr 32
    var k2 = KE and MASK32
    x2 = x2 xor ((x1 and k1).cycleShiftLeft32(1));
    x1 = x1 xor (x2 or k2)
    val FL_OUT = x1 shl 32 or x2
    return FL_OUT
}

fun FLINV(FLINV_IN: BigInteger, KE: BigInteger): BigInteger {
    var y1 = FLINV_IN shr 32
    var y2 = FLINV_IN and MASK32
    var k1 = KE shr 32
    var k2 = KE and MASK32
    y1 = y1 xor (y2 or k2)
    y2 = y2 xor ((y1 and k1).cycleShiftLeft32(1));
    val FLINV_OUT = y1 shl 32 or y2
    return FLINV_OUT
}

fun SBOX1(t: BigInteger): BigInteger {
    return SBOX1ARR[t.and(0xF0.toBig()).toInt() shr 8][t.and(0x0F.toBig()).toInt()].toBig()
}

fun SBOX2(t: BigInteger): BigInteger {
    return SBOX1(t).cycleShiftLeft8(1)
}

fun SBOX3(t: BigInteger): BigInteger {
    return SBOX1(t).cycleShiftLeft8(7)
}

fun SBOX4(t: BigInteger): BigInteger {
    return SBOX1(t.cycleShiftLeft8(1))
}

fun BigInteger.cycleShiftLeft8(count: Int) = this.shiftLeft(count).or(this.shiftRight(8 - count)).and(MASK8)

fun BigInteger.cycleShiftLeft32(count: Int) = this.shiftLeft(count).or(this.shiftRight(32 - count)).and(MASK32)

fun BigInteger.cycleShiftLeft128(count: Int) = this.shiftLeft(count).or(this.shiftRight(128 - count)).and(MASK128)

fun encryptCamelliaBLOCK(key: BigInteger, value: BigInteger):BigInteger {
    val KL = key
    val KR = BigInteger.ZERO
    val (KA, KB) = getKAKB(KL, KR)

    val kw1 = KL.cycleShiftLeft128(0) shr 64
    val kw2 = KL.cycleShiftLeft128(0) and MASK64
    val k1 = KA.cycleShiftLeft128(0) shr 64
    val k2 = KA.cycleShiftLeft128(0) and MASK64
    val k3 = KL.cycleShiftLeft128(15) shr 64
    val k4 = KL.cycleShiftLeft128(15) and MASK64
    val k5 = KA.cycleShiftLeft128(15) shr 64
    val k6 = KA.cycleShiftLeft128(15) and MASK64
    val ke1 = KA.cycleShiftLeft128(30) shr 64
    val ke2 = KA.cycleShiftLeft128(30) and MASK64
    val k7 = KL.cycleShiftLeft128(45) shr 64
    val k8 = KL.cycleShiftLeft128(45) and MASK64
    val k9 = KA.cycleShiftLeft128(45) shr 64
    val k10 = KL.cycleShiftLeft128(60) and MASK64
    val k11 = KA.cycleShiftLeft128(60) shr 64
    val k12 = KA.cycleShiftLeft128(60) and MASK64
    val ke3 = KL.cycleShiftLeft128(77) shr 64
    val ke4 = KL.cycleShiftLeft128(77) and MASK64
    val k13 = KL.cycleShiftLeft128(94) shr 64
    val k14 = KL.cycleShiftLeft128(94) and MASK64
    val k15 = KA.cycleShiftLeft128(94) shr 64
    val k16 = KA.cycleShiftLeft128(94) and MASK64
    val k17 = KL.cycleShiftLeft128(111) shr 64
    val k18 = KL.cycleShiftLeft128(111) and MASK64
    val kw3 = KA.cycleShiftLeft128(111) shr 64
    val kw4 = KA.cycleShiftLeft128(111) and MASK64

    fun ecryptBlock128(M: BigInteger): BigInteger {
        var D1 = M shr 64 // Шифруемое сообщение делится на две 64-битные части
        var D2 = M and MASK64
        D1 = D1 xor kw1 // Предварительное забеливание
        D2 = D2 xor kw2
        D2 = D2 xor F(D1, k1)
        D1 = D1 xor F(D2, k2)
        D2 = D2 xor F(D1, k3)
        D1 = D1 xor F(D2, k4)
        D2 = D2 xor F(D1, k5)
        D1 = D1 xor F(D2, k6)
        D1 = FL(D1, ke1) // FL

        D2 = FLINV(D2, ke2) // FLINV

        D2 = D2 xor F(D1, k7)
        D1 = D1 xor F(D2, k8)
        D2 = D2 xor F(D1, k9)
        D1 = D1 xor F(D2, k10)
        D2 = D2 xor F(D1, k11)
        D1 = D1 xor F(D2, k12)
        D1 = FL(D1, ke3) // FL

        D2 = FLINV(D2, ke4) // FLINV

        D2 = D2 xor F(D1, k13)
        D1 = D1 xor F(D2, k14)
        D2 = D2 xor F(D1, k15)
        D1 = D1 xor F(D2, k16)
        D2 = D2 xor F(D1, k17)
        D1 = D1 xor F(D2, k18)
        D2 = D2 xor kw3 // Финальное забеливание

        D1 = D1 xor kw4
        val C = (D2 shl 64) or D1

        return C
    }

    return ecryptBlock128(value)
}

fun decryptCamelliaBLOCK(key: BigInteger, value: BigInteger):BigInteger {
    val KL = key
    val KR = BigInteger.ZERO
    val (KA, KB) = getKAKB(KL, KR)

    val kw3 = KL.cycleShiftLeft128(0) shr 64
    val kw4 = KL.cycleShiftLeft128(0) and MASK64
    val k18 = KA.cycleShiftLeft128(0) shr 64
    val k17 = KA.cycleShiftLeft128(0) and MASK64
    val k16 = KL.cycleShiftLeft128(15) shr 64
    val k15 = KL.cycleShiftLeft128(15) and MASK64
    val k14 = KA.cycleShiftLeft128(15) shr 64
    val k13 = KA.cycleShiftLeft128(15) and MASK64
    val ke4 = KA.cycleShiftLeft128(30) shr 64
    val ke3 = KA.cycleShiftLeft128(30) and MASK64
    val k12 = KL.cycleShiftLeft128(45) shr 64
    val k11 = KL.cycleShiftLeft128(45) and MASK64
    val k10 = KA.cycleShiftLeft128(45) shr 64
    val k9 = KL.cycleShiftLeft128(60) and MASK64
    val k8 = KA.cycleShiftLeft128(60) shr 64
    val k7 = KA.cycleShiftLeft128(60) and MASK64
    val ke2 = KL.cycleShiftLeft128(77) shr 64
    val ke1 = KL.cycleShiftLeft128(77) and MASK64
    val k6 = KL.cycleShiftLeft128(94) shr 64
    val k5 = KL.cycleShiftLeft128(94) and MASK64
    val k4 = KA.cycleShiftLeft128(94) shr 64
    val k3 = KA.cycleShiftLeft128(94) and MASK64
    val k2 = KL.cycleShiftLeft128(111) shr 64
    val k1 = KL.cycleShiftLeft128(111) and MASK64
    val kw1 = KA.cycleShiftLeft128(111) shr 64
    val kw2 = KA.cycleShiftLeft128(111) and MASK64

    fun decryptBlock128(M: BigInteger): BigInteger {
        var D1 = M shr 64 // Шифруемое сообщение делится на две 64-битные части
        var D2 = M and MASK64
        D1 = D1 xor kw1 // Предварительное забеливание
        D2 = D2 xor kw2
        D2 = D2 xor F(D1, k1)
        D1 = D1 xor F(D2, k2)
        D2 = D2 xor F(D1, k3)
        D1 = D1 xor F(D2, k4)
        D2 = D2 xor F(D1, k5)
        D1 = D1 xor F(D2, k6)
        D1 = FL(D1, ke1) // FL

        D2 = FLINV(D2, ke2) // FLINV

        D2 = D2 xor F(D1, k7)
        D1 = D1 xor F(D2, k8)
        D2 = D2 xor F(D1, k9)
        D1 = D1 xor F(D2, k10)
        D2 = D2 xor F(D1, k11)
        D1 = D1 xor F(D2, k12)
        D1 = FL(D1, ke3) // FL

        D2 = FLINV(D2, ke4) // FLINV

        D2 = D2 xor F(D1, k13)
        D1 = D1 xor F(D2, k14)
        D2 = D2 xor F(D1, k15)
        D1 = D1 xor F(D2, k16)
        D2 = D2 xor F(D1, k17)
        D1 = D1 xor F(D2, k18)
        D2 = D2 xor kw3 // Финальное забеливание

        D1 = D1 xor kw4
        val C = (D2 shl 64) or D1

        return C
    }

    return decryptBlock128(value)
}