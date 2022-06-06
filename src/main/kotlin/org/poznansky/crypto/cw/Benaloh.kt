package org.poznansky.crypto.cw

import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

const val MAX_BITS = 32
const val cert = 10
val rnd = Random()
val ONE = BigInteger.ONE

data class PublicKey(
    val y: BigInteger,
    val r: BigInteger,
    val n: BigInteger,
) {
    override fun toString(): String {
        return "PublicKey(y=$y, r=$r, n=$n)"
    }
}

data class PrivateKey(
    val p: BigInteger,
    val q: BigInteger,
) {
    override fun toString(): String {
        return "PrivateKey(p=$p, q=$q)"
    }
}

data class Keys(val pub: PublicKey, val priv: PrivateKey) {
    override fun toString(): String {
        return "Keys(pub=$pub, priv=$priv)"
    }
}


fun generateBenaloh(): Keys {
    val (r, p, q) = generateRPQ()
    val n = p * q
    val y = chooseY(p, q, r)

    return Keys(PublicKey(y, r, n), PrivateKey(p, q))
}


fun encryptBenaloh(key: PublicKey, input: Path, output: Path) {
    val (y, r, n) = key


    val u = rnd.big(MAX_BITS * 2, max = n, min = ONE)

    val bytes: ByteArray = Files.readAllBytes(input)

    val encrypted: ByteArray = bytes.map { b -> b.toInt().and(0xFF).toBig() }
        .onEach { check(it >= BigInteger.ZERO) }
        .map { y.modPow(it, n) * u.modPow(r, n) % n }
        .map { it.toByteArray() }.map { ByteArray(8 - it.size) { 0 } + it }
        .onEach { check(it.size == 8) }
        .reduce(ByteArray::plus)

    Files.write(output, encrypted)
}


fun decryptBenaloh(keys: Keys, input: Path, output: Path) {
    val (pub, priv) = keys
    val (y, r, n) = pub
    val (p, q) = priv

    val bytes: ByteArray = Files.readAllBytes(input)

    val x = y.modPow((p - ONE) * (q - ONE) / r, n)

    val decrypted = bytes.asSequence().chunked(8)
        .map { chunk -> BigInteger(chunk.toByteArray()) }
        .onEach { check(it >= BigInteger.ZERO) }
        .map { it.modPow(((p - ONE) * (q - ONE)) / r, p * q) }
        .map { a ->
            generateSequence(BigInteger.ZERO) { it + ONE }
                .dropWhile { md -> x.modPow(md, n) != a }
                .first()
        }
        .onEach { check(it < 256.toBig()) }
        .map { it.toByte() }
        .toList()
        .toByteArray()

    Files.write(output, decrypted)
}


fun generateRPQ(): Triple<BigInteger, BigInteger, BigInteger> {
    val r = 263.toBig()

    //r*(LARGE/r) - > +r -> +r

//    val p = generateSequence(r * 1000.toBig()) { it + r }
//        .dropWhile { !(it + ONE).isProbablePrime(cert) }
//        .first() + ONE

    val p = generateSequence(r * BigInteger(MAX_BITS / 2, cert, rnd)) { it + r }
        .map { it + ONE }
        .dropWhile { !it.isProbablePrime(cert) }
        .first()

    val q = generateSequence { BigInteger(MAX_BITS, cert, rnd) }
        .dropWhile { r.gcd(it - ONE) != ONE }
        .first()

    return Triple(r, p, q)
}


fun chooseY(p: BigInteger, q: BigInteger, r: BigInteger): BigInteger {
    val n = p * q
    val phi = (p - ONE) * (q - ONE)

    while (true) {
        val y = rnd.big(MAX_BITS * 2, max = n, min = ONE)
        if (y.modPow(phi / r, n) != ONE) {
            return y
        }
    }
}


fun Int.toBig() = BigInteger.valueOf(this.toLong())


fun BigInteger.pow(exponent: BigInteger): BigInteger {
    var b = this
    var e = exponent
    var res = BigInteger.ONE
    while (e.signum() > 0) {
        if (e.testBit(0)) res = res.multiply(b)
        b = b.multiply(b)
        e = e.shiftRight(1)
    }
    return res
}


fun Random.big(bits: Int, max: BigInteger? = null, min: BigInteger? = null): BigInteger {
    while (true) {
        val t = BigInteger(bits, this)
        if (max?.let { t < it } != false && min?.let { t >= it } != false) {
            return t
        }
    }
}
