package org.smirnov.crypto.cw

import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

const val MAX_BITS = 32
const val cert = 10
val rnd = Random()
val ONE = BigInteger.ONE

    fun powmod(x: BigInteger, y: BigInteger, p: BigInteger): BigInteger {
        var x = x
        var y = y
        var res = BigInteger.ONE // Initialize result
        x = x % p // Update x if it is more than or
        // equal to p
        while (y > BigInteger.ZERO) {
            // If y is odd, multiply x with result
            if (y and BigInteger.ONE > BigInteger.ZERO) res = res * x % p

            // y must be even now
            y = y shr 1 // y = y/2
            x = x * x % p
        }
        return res
    }

    // Function to calculate k for given a, b, m
    fun discreteLogarithm(a: BigInteger, b: BigInteger, m: BigInteger): BigInteger {
        val n = (Math.sqrt(m.toDouble()) + 1).toInt().toBig()
        val value = Array<BigInteger>(m.toInt()) { _: Int -> BigInteger.ZERO }

        // Store all values of a^(n*i) of LHS
        for (i in n.toInt() downTo 1) value[powmod(a, i.toBig() * n, m).toInt()] = i.toBig()
        for (j in 0 until n.toInt()) {
            // Calculate (a ^ j) * b and check
            // for collision
            val cur = powmod(a, j.toBig(), m) * b % m

            // If collision occurs i.e., LHS = RHS
            if (value[cur.toInt()] > BigInteger.ZERO) {
                val ans = value[cur.toInt()] * n - j.toBig()
                // Check whether ans lies below m or not
                if (ans < m) return ans
            }
        }
        return (-1).toBig()
    }

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
    val r = 277.toBig()

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
