package org.smirnov.crypto.cw

import java.math.BigInteger
import java.util.*

fun PBlockEncrypt(value: Int, pBlock: ByteArray): Int {
    var result = 0
    for (i in 0 until pBlock.size)
        result = result or (value shr pBlock.size - pBlock[i] and 1 shl pBlock.size - i - 1)
    return result
}

fun PBlockDecrypt(value: Int, pBlock: ByteArray): Int {
    var result = 0
    for (i in 0 until pBlock.size)
        result = result or (value shr pBlock.size - i - 1 and 1 shl pBlock.size - pBlock[i])
    return result
}

fun Legandr(a: BigInteger, p: BigInteger): BigInteger {
    if (a == 1.toBigInteger()) return 1.toBigInteger()
    if (a.mod(2.toBigInteger()) == BigInteger.ZERO)
        return Legandr(a / BigInteger.TWO, p) * ((-1).toBigInteger()
            .pow(p.pow(2) - 1.toBigInteger()) / 8.toBigInteger())

    return Legandr(p.mod(a), a) * (-1).toBigInteger()
        .pow(((a - 1.toBigInteger()) * ((p - 1.toBigInteger()) / 4.toBigInteger())))
}

fun Jacobi(a1: BigInteger, b1: BigInteger): BigInteger {
    var a = a1
    var b = b1
    var r = BigInteger.ONE
    while (a != BigInteger.ZERO) {
        var t = BigInteger.ZERO
        while (a.and(BigInteger.ONE) == BigInteger.ZERO) {
            t += BigInteger.ONE
            a = a.shiftRight(1)
        }
        if (t.and(BigInteger.ONE) != BigInteger.ZERO) {
            var temp = b.mod(8.toBigInteger())
            if (temp == 3.toBigInteger() || temp == 5.toBigInteger())
                r = -r
        }

        var a4 = a.mod(4.toBigInteger())
        var b4 = b.mod(4.toBigInteger())
        if (a4 == 3.toBigInteger() && b4 == 3.toBigInteger())
            r = -r
        var c = a
        a = b.mod(c)
        b = c
    }
    return r
}



fun power(x: Int, y: Int, p: Int): Int {
    var x = x
    var y = y
    var res = 1 

    
    
    x = x % p
    while (y > 0) {

        
        if (y and 1 == 1) res = res * x % p

        
        y = y shr 1 
        x = x * x % p
    }
    return res
}





fun isPrimeFermat(n: Int, k: Int): Boolean {
    
    var k = k
    if (n <= 1 || n == 4) return false
    if (n <= 3) return true

    
    while (k > 0) {
        
        
        val a = 2 + (Math.random() % (n - 4)).toInt()

        
        if (power(a, n - 1, n) != 1) return false
        k--
    }
    return true
}






fun miillerTest(d: Int, n: Int): Boolean {

    
    
    var d = d
    val a = 2 + (Math.random() % (n - 4)).toInt()

    
    var x = power(a, d, n)
    if (x == 1 || x == n - 1) return true

    
    
    
    
    
    while (d != n - 1) {
        x = x * x % n
        d *= 2
        if (x == 1) return false
        if (x == n - 1) return true
    }

    
    return false
}






fun isPrimeMillerRabin(n: Int, k: Int): Boolean {

    
    if (n <= 1 || n == 4) return false
    if (n <= 3) return true

    
    
    var d = n - 1
    while (d % 2 == 0) d /= 2

    
    for (i in 0 until k) if (!miillerTest(d, n)) return false
    return true
}



fun modulo(
    base: Long,
    exponent: Long,
    mod: Long
): Long {
    var exponent = exponent
    var x: Long = 1
    var y = base
    while (exponent > 0) {
        if (exponent % 2 == 1L) x = x * y % mod
        y = y * y % mod
        exponent = exponent / 2
    }
    return x % mod
}



fun calculateJacobian(a: Long, n: Long): Long {
    var a = a
    var n = n
    if (n <= 0 || n % 2 == 0L) return 0
    var ans = 1L
    if (a < 0) {
        a = -a 
        if (n % 4 == 3L) ans = -ans 
    }
    if (a == 1L) return ans 
    while (a != 0L) {
        if (a < 0) {
            a = -a 
            if (n % 4 == 3L) ans = -ans 
        }
        while (a % 2 == 0L) {
            a /= 2
            if (n % 8 == 3L || n % 8 == 5L) ans = -ans
        }
        val temp = a
        a = n
        n = temp
        if (a % 4 == 3L && n % 4 == 3L) ans = -ans
        a %= n
        if (a > n / 2) a = a - n
    }
    return if (n == 1L) ans else 0
}

fun solovoyStrassen(
    p: Long,
    iteration: Int
): Boolean {
    if (p < 2) return false
    if (p != 2L && p % 2 == 0L) return false
    
    val rand = Random()
    for (i in 0 until iteration) {

        
        val r: Long = Math.abs(rand.nextLong())
        val a = r % (p - 1) + 1
        val jacobian = (p + calculateJacobian(a, p)) % p
        val mod = modulo(a, (p - 1) / 2, p)
        if (jacobian == 0L || mod != jacobian) return false
    }
    return true
}

fun main(args: Array<String>) {

}