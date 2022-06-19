package org.poznansky.crypto.cw

object MARS {
    private lateinit var K: IntArray
    private val s_box = intArrayOf(
        0x09d0c479,
        0x28c8ffe0,
        -0x7b5593c7,
        -0x62528d79,
        0x7dff9be3,
        -0x2bd97c9f,
        -0x36925e2c,
        0x7974cc93,
        -0x7a2fa7d2,
        0x2a4b5705,
        0x1ca16a62,
        -0x3c42d863,
        0x0f1f25e5,
        0x5160372f,
        -0x396a3e05,
        0x4d7ff1e4,
        -0x51a0940c,
        0x0d72ee46,
        -0xdc2176,
        -0x4e30717d,
        -0xeb6fd1e,
        0x3e981e42,
        -0x740ac14a,
        0x7f4bf8ac,
        -0x7c9ce07d,
        0x25970205,
        0x76afe784,
        0x3a7931d4,
        0x4f846450,
        0x5c64c3f6,
        0x210a5f18,
        -0x396795da,
        0x28f4e826,
        0x3a60a81c,
        -0x2cbf599c,
        0x7ea820c4,
        0x526687c5,
        0x7eddd12b,
        0x32a11d1d,
        -0x63610f7a,
        -0x7f0917cf,
        -0x5490fb53,
        0x56fb9b53,
        -0x74d1f6a4,
        -0x497aa952,
        -0x2ddaf4f3,
        0x294a7721,
        -0x1de04dad,
        -0x51ec98b7,
        -0x17d5517a,
        -0x6cc9aefc,
        -0x66bfb59a,
        0x78a784dc,
        -0x496457b5,
        0x04046793,
        0x23db5c1e,
        0x46cae1d6,
        0x2fe28134,
        0x5a223942,
        0x1863cd5b,
        -0x3e6f391d,
        0x07dfb846,
        0x6eb88816,
        0x2d0dcc4a,
        -0x5b3351a7,
        0x3798670d,
        -0x34056b6d,
        0x4f481d45,
        -0x15037358,
        -0x24eed62a,
        -0x4fbb61e0,
        0x0f5407fb,
        0x6167d9a8,
        -0x2e0ba89d,
        0x4daa96c3,
        0x3bec5958,
        -0x54545fec,
        -0x49332dff,
        0x38d6279f,
        0x02682215,
        -0x70c8932b,
        0x092c237e,
        -0x403a9a6d,
        0x32889d2c,
        -0x7ab4c16b,
        0x05bb9b43,
        0x7dcd5dcd,
        -0x5fd16d94,
        -0x51ad81b,
        0x36a1c330,
        0x3412e1ae,
        -0xda80b9e,
        0x3c4f1d71,
        0x30a2e809,
        0x68e5f551,
        -0x639e45bc,
        0x5ded0ab8,
        0x75ce09c8,
        -0x69ab06c2,
        0x698c0cca,
        0x243cb3e4,
        0x2b062b97,
        0x0f3b8d9e,
        0x00e050df,
        -0x3a29e9a,
        -0x1ca06d78,
        -0x3f86aaf3,
        0x0591aee8,
        -0x71ace18c,
        0x75fe3578,
        0x2f6d829a,
        -0x9f4de52,
        -0x6a171473,
        0x6699486b,
        -0x6fe28265,
        -0x29291cf,
        0x1090acef,
        -0x1f98f228,
        -0x254d196e,
        -0x3292bc9b,
        -0x1ac6caec,
        0x3af345f0,
        0x6241fc4d,
        0x460da3a3,
        0x7bcf3729,
        -0x740e2e20,
        0x14aac070,
        0x1587ed55,
        0x3afd7d3e,
        -0x2d0d61ff,
        0x29a9d1f6,
        -0x104ef3ad,
        -0x30c478f1,
        -0x4beb6ca4,
        0x664465ed,
        0x024acac7,
        0x59a744c1,
        0x1d2936a7,
        -0x23a7f55a,
        -0x30a8b358,
        0x040a7a10,
        0x6cd81807,
        -0x756741b4,
        -0x53315f9d,
        -0x3cc16d4b,
        -0x2e1f1fc3,
        -0x4cddae82,
        0x2092bd13,
        0x386b2c4a,
        0x52e8dd58,
        0x58656dfb,
        0x50820371,
        0x41811896,
        -0x1cc81082,
        -0x2c604ee7,
        -0x3680f20a,
        0x68fea01b,
        -0x5eaf591b,
        0x55258962,
        -0x14900be5,
        -0x28363286,
        -0x59e63262,
        -0x430f6a8a,
        0x2672c073,
        -0xffc04c4,
        0x4ab7a50b,
        0x1484126a,
        0x487ba9b1,
        -0x59b0363a,
        -0x96a82b7,
        0x38b06a75,
        -0x227fa033,
        0x63d094cf,
        -0xae36662,
        0x1aa4d343,
        -0x47b6ad6c,
        -0x31607167,
        -0x40032890,
        -0x383d8a34,
        0x378453a7,
        0x7b21be33,
        0x397f41bd,
        0x4e94d131,
        -0x6d33e068,
        0x5915ea51,
        -0x66079e49,
        -0x3667f578,
        0x1d74fd5f,
        -0x4f5b6a08,
        0x614deed0,
        -0x4a887116,
        0x5941792d,
        -0x56f3e08,
        0x33f824b4,
        -0x3b69ac8e,
        0x3ff6d550,
        0x4ca5fec0,
        -0x79cf169c,
        0x5b3fbbd6,
        0x7da26a48,
        -0x4dfcdce6,
        0x04297514,
        0x2d639306,
        0x2eb13149,
        0x16a45272,
        0x532459a0,
        -0x71a0b78e,
        -0x6993827,
        0x07128dc0,
        0x0d44db62,
        -0x50372ad3,
        0x06316131,
        -0x27c71832,
        0x1bc41d00,
        0x3a2e8c0f,
        -0x157c7c82,
        -0x467b8c83,
        0x13ba4891,
        -0x3b0746b7,
        -0x5929534d,
        -0x5dea3232,
        -0x7ca67c75,
        0x6bd1aa31,
        -0xa8622ae,
        0x21b93f93,
        -0xae8987f,
        0x187dfdde,
        -0x16b5148a,
        0x2b38fd54,
        0x431de1da,
        -0x54c6b7db,
        -0x652cfb71,
        -0x2015cd56,
        0x659473e3,
        0x623f7863,
        -0xccb93a7,
        -0x54c5497b,
        0x3346a90b,
        0x6b56443e,
        -0x3921fe08,
        -0x72bde040,
        -0x64f12ef4,
        -0x770e5e17,
        0x54c1f029,
        0x7dead57b,
        -0x72845bda,
        0x4cf5178a,
        0x551a7cca,
        0x1a9a5f08,
        -0x329ae47,
        0x25605182,
        -0x1ee0393d,
        -0x4902698a,
        0x337b3027,
        -0x483714ec,
        -0x61a02fd0,
        0x6b57e354,
        -0x526ec309,
        0x7e16688d,
        0x58872a69,
        0x2c2fc7df,
        -0x1c76333a,
        0x30738df1,
        0x0824a734,
        -0x1e868575,
        -0x5b572a85,
        0x5b5d193b,
        -0x3757cf65,
        0x73f9a978,
        0x73398d32,
        0x0f59573e,
        -0x1620d4fd,
        -0x175a4938,
        -0x7b72f8fc,
        -0x67206c3e,
        0x720a1dc3,
        0x684f259a,
        -0x6bc457b8,
        -0x59c8feae,
        -0x79c4a15d,
        -0x2e846875,
        0x6d9b58ef,
        0x0a700dd4,
        -0x58c2c941,
        -0x7195f7d7,
        -0x796a43ec,
        -0x1ca4cbb9,
        -0x6cc53a98,
        -0x776b4fde,
        0x2f511c27,
        -0x220433c4,
        0x006662b6,
        0x117c83fe,
        0x4e12b414,
        -0x3d43589a,
        0x3a2fec10,
        -0xba9dbe0,
        0x55792e2a,
        0x46f5d857,
        -0x3125da32,
        -0x3c9fe2c5,
        0x6c00ab46,
        -0x105363d8,
        -0x4c3cafb9,
        0x611dfee3,
        0x257c3207,
        -0x22a7b7e,
        0x3b14d84f,
        0x23becb64,
        -0x5f8a0c5d,
        0x088f8ead,
        0x07adf158,
        0x7796943c,
        -0x53540c3,
        -0x3f68cf33,
        -0x8986697,
        -0x25bb1613,
        0x2c854c12,
        0x35935fa3,
        0x2f057d9f,
        0x690624f8,
        0x1cb0bafd,
        0x7b0dbdc6,
        -0x7ef0dc45,
        -0x56d65e6,
        0x6d969a17,
        0x6742979b,
        0x74ac7d05,
        0x010e65c4,
        -0x795c269d,
        -0x6f84a60,
        -0x2ffbd42d,
        0x158d7d03,
        0x287a8255,
        -0x4457c991,
        0x096edc33,
        0x21916a7b,
        0x77b56b86,
        -0x6ae9dd07,
        -0x593a19b0,
        -0x7315e82f,
        -0x32739d44,
        -0x5c29cbcd,
        0x358a68fd,
        0x0f9b9d3c,
        -0x2955d6a5,
        -0x1ccc7b6,
        -0x3fff8c72,
        -0x329814d1,
        -0x1d14923e,
        -0x68cc74fe,
        0x06c9f246,
        0x419cf1ad,
        0x2b83c045,
        0x3723f18a,
        -0x34a4cf77,
        0x160bead7,
        0x5d494656,
        0x35f8a74b,
        0x1e4e6c9e,
        0x000399bd,
        0x67466880,
        -0x4be8b7cf,
        -0x530bdc4e,
        -0x357ea54d,
        0x5a6395e7,
        0x302a67c5,
        -0x7424bb95,
        0x108f8fa4,
        0x10223eda,
        -0x6d474b75,
        0x7f38d0ee,
        -0x54d8fe2c,
        0x0262d415,
        -0x50ddb5d0,
        -0x4c277546,
        -0x74d3c51,
        -0x25081090,
        -0x33682c49,
        -0x169eb494,
        0x2baebff4,
        0x70f687cf,
        0x386c9156,
        -0x31f6d11b,
        0x01e87da6,
        0x6ce91e6a,
        -0x4484337c,
        -0x386dd3e0,
        -0x62c48e03,
        0x060e41c6,
        -0x28a6f0eb,
        0x4e03bb47,
        0x183c198e,
        0x63eeb240,
        0x2ddbf49a,
        0x6d5cba54,
        -0x6dc8af51,
        -0x61ebdca,
        0x7838162b,
        0x59726c72,
        -0x7e4998a0,
        -0x44d6d93f,
        0x48a0ce0d,
        -0x593fb693,
        -0x52bcaf85,
        0x718d496a,
        -0x620fa851,
        0x44b1bde6,
        0x054356dc,
        -0x218312cb,
        -0x2ae5ec75,
        0x62088cc9,
        0x35830311,
        -0x3691035e,
        0x686f86ec,
        -0x71883498,
        0x63e1d6b8,
        -0x37f06888,
        0x79c491fd,
        0x1b4c67f2,
        0x58656dfb,
        0x50820371,
        0x41811896,
        -0x1cc81082,
        -0x2c604ee7,
        -0x3680f20a,
        0x68fea01b,
        -0x5eaf591b,
        0x55258962,
        -0x14900be5,
        -0x28363286,
        -0x59e63262,
        -0x430f6a8a,
        0x2672c073,
        -0xffc04c4,
        0x4ab7a50b,
        0x1484126a,
        0x487ba9b1,
        -0x59b0363a,
        -0x96a82b7,
        0x38b06a75,
        -0x227fa033,
        0x63d094cf,
        -0xae36662,
        0x1aa4d343,
        -0x47b6ad6c,
        -0x31607167,
        -0x40032890,
        -0x383d8a34,
        0x378453a7,
        0x7b21be33,
        0x397f41bd,
        0x4e94d131,
        -0x6d33e068,
        0x5915ea51,
        -0x66079e49,
        -0x3667f578,
        0x1d74fd5f,
        -0x4f5b6a08,
        0x614deed0,
        -0x4a887116,
        0x5941792d,
        -0x56f3e08,
        0x33f824b4,
        -0x3b69ac8e,
        0x3ff6d550,
        0x4ca5fec0,
        -0x79cf169c,
        0x5b3fbbd6,
        0x7da26a48,
        -0x4dfcdce6,
        0x04297514,
        0x2d639306,
        0x2eb13149,
        0x16a45272,
        0x532459a0,
        -0x71a0b78e,
        -0x6993827,
        0x07128dc0,
        0x0d44db62,
        -0x50372ad3,
        0x06316131,
        -0x27c71832,
        0x1bc41d00,
        0x3a2e8c0f,
        -0x157c7c82,
        -0x467b8c83,
        0x13ba4891,
        -0x3b0746b7,
        -0x5929534d,
        -0x5dea3232,
        -0x7ca67c75,
        0x6bd1aa31,
        -0xa8622ae,
        0x21b93f93,
        -0xae8987f,
        0x187dfdde,
        -0x16b5148a,
        0x2b38fd54,
        0x431de1da,
        -0x54c6b7db,
        -0x652cfb71,
        -0x2015cd56,
        0x659473e3,
        0x623f7863,
        -0xccb93a7,
        -0x54c5497b,
        0x3346a90b,
        0x6b56443e,
        -0x3921fe08,
        -0x72bde040,
        -0x64f12ef4,
        -0x770e5e17,
        0x54c1f029,
        0x7dead57b,
        -0x72845bda,
        0x4cf5178a,
        0x551a7cca,
        0x1a9a5f08,
        -0x329ae47,
        0x25605182,
        -0x1ee0393d,
        -0x4902698a,
        0x337b3027,
        -0x483714ec,
        -0x61a02fd0,
        0x6b57e354,
        -0x526ec309,
        0x7e16688d,
        0x58872a69,
        0x2c2fc7df,
        -0x1c76333a,
        0x30738df1,
        0x0824a734,
        -0x1e868575,
        -0x5b572a85,
        0x5b5d193b,
        -0x3757cf65,
        0x73f9a978,
        0x73398d32,
        0x0f59573e,
        -0x1620d4fd,
        -0x175a4938,
        -0x7b72f8fc,
        -0x67206c3e,
        0x720a1dc3,
        0x684f259a,
        -0x6bc457b8,
        -0x59c8feae,
        -0x79c4a15d,
        -0x2e846875,
        0x6d9b58ef,
        0x0a700dd4,
        -0x58c2c941,
        -0x7195f7d7,
        -0x796a43ec,
        -0x1ca4cbb9,
        -0x6cc53a98,
        -0x776b4fde,
        0x2f511c27,
        -0x220433c4,
        0x006662b6,
        0x117c83fe,
        0x4e12b414,
        -0x3d43589a,
        0x3a2fec10,
        -0xba9dbe0,
        0x55792e2a,
        0x46f5d857,
        -0x3125da32,
        -0x3c9fe2c5,
        0x6c00ab46,
        -0x105363d8,
        -0x4c3cafb9,
        0x611dfee3,
        0x257c3207,
        -0x22a7b7e,
        0x3b14d84f,
        0x23becb64,
        -0x5f8a0c5d,
        0x088f8ead,
        0x07adf158,
        0x7796943c,
        -0x53540c3,
        -0x3f68cf33,
        -0x8986697,
        -0x25bb1613,
        0x2c854c12,
        0x35935fa3,
        0x2f057d9f,
        0x690624f8,
        0x1cb0bafd,
        0x7b0dbdc6,
        -0x7ef0dc45,
        -0x56d65e6,
        0x6d969a17,
        0x6742979b,
        0x74ac7d05,
        0x010e65c4,
        -0x795c269d,
        -0x6f84a60,
        -0x2ffbd42d,
        0x158d7d03,
        0x287a8255,
        -0x4457c991,
        0x096edc33,
        0x21916a7b,
        0x77b56b86,
        -0x6ae9dd07,
        -0x593a19b0,
        -0x7315e82f,
        -0x32739d44,
        -0x5c29cbcd,
        0x358a68fd,
        0x0f9b9d3c,
        -0x2955d6a5,
        -0x1ccc7b6,
        -0x3fff8c72,
        -0x329814d1,
        -0x1d14923e,
        -0x68cc74fe,
        0x06c9f246,
        0x419cf1ad,
        0x2b83c045,
        0x3723f18a,
        -0x34a4cf77,
        0x160bead7,
        0x5d494656,
        0x35f8a74b,
        0x1e4e6c9e,
        0x000399bd,
        0x67466880,
        -0x4be8b7cf,
        -0x530bdc4e,
        -0x357ea54d,
        0x5a6395e7,
        0x302a67c5,
        -0x7424bb95,
        0x108f8fa4,
        0x10223eda,
        -0x6d474b75,
        0x7f38d0ee,
        -0x54d8fe2c,
        0x0262d415,
        -0x50ddb5d0,
        -0x4c277546,
        -0x74d3c51,
        -0x25081090,
        -0x33682c49,
        -0x169eb494,
        0x2baebff4,
        0x70f687cf,
        0x386c9156,
        -0x31f6d11b,
        0x01e87da6,
        0x6ce91e6a,
        -0x4484337c,
        -0x386dd3e0,
        -0x62c48e03,
        0x060e41c6,
        -0x28a6f0eb,
        0x4e03bb47,
        0x183c198e,
        0x63eeb240,
        0x2ddbf49a,
        0x6d5cba54,
        -0x6dc8af51,
        -0x61ebdca,
        0x7838162b,
        0x59726c72,
        -0x7e4998a0,
        -0x44d6d93f,
        0x48a0ce0d,
        -0x593fb693,
        -0x52bcaf85,
        0x718d496a,
        -0x620fa851,
        0x44b1bde6,
        0x054356dc,
        -0x218312cb,
        -0x2ae5ec75,
        0x62088cc9,
        0x35830311,
        -0x3691035e,
        0x686f86ec,
        -0x71883498,
        0x63e1d6b8,
        -0x37f06888,
        0x79c491fd,
        0x1b4c67f2
    )

    private fun rotateLeft(`val`: Int, pas: Int): Int {
        return `val` shl pas or (`val` ushr 32 - pas)
    }

    private fun rotateRight(`val`: Int, pas: Int): Int {
        return `val` ushr pas or (`val` shl 32 - pas)
    }

    //E Function
    private fun expandKey(key: ByteArray): IntArray {
        val n = key.size / 4
        val tmp = IntArray(40)
        val data = IntArray(n)
        for (i in data.indices) data[i] = 0
        var off = 0
        for (i in data.indices) {
            data[i] = key[off++].toInt() and 0xff or
                    (key[off++].toInt() and 0xff shl 8) or
                    (key[off++].toInt() and 0xff shl 16) or
                    (key[off++].toInt() and 0xff shl 24)
        }
        val T = IntArray(15)
        for (i in T.indices) {
            if (i < data.size) T[i] = data[i] else if (i == data.size) T[i] = n else T[i] = 0
        }
        for (j in 0..3) {
            for (i in T.indices) T[i] =
                T[i] xor (rotateLeft(T[Math.abs(i - 7 % 15)] xor T[Math.abs(i - 2 % 15)], 3) xor 4 * i + j)
            for (c in 0..3) for (i in T.indices) T[i] =
                T[i] + rotateLeft(s_box[(T[Math.abs(i - 1 % 15)] and 0x000001ff)], 9)
            for (i in 0..9) tmp[10 * j + i] = T[4 * i % 15]
        }
        val B = intArrayOf(-0x5b572a85, 0x5b5d193b, -0x3757cf65, 0x73f9a978)
        var j: Int
        var w: Int
        var m: Int
        var r: Int
        var p: Int
        for (i in 5..35) {
            j = tmp[i] and 0x00000003
            w = tmp[i] or 0x00000003
            m = generateMask(w)
            r = tmp[i - 1] and 0x0000001f
            p = rotateLeft(B[j], r)
            tmp[i] = w xor (p and m)
        }
        return tmp
    }

    private fun generateMask(x: Int): Int {
        var m: Int
        m = x.inv() xor (x ushr 1) and 0x7fffffff
        m = m and (m shr 1 and (m shr 2))
        m = m and (m shr 3 and (m shr 6))
        if (m == 0) return 0
        m = m shl 1
        m = m or (m shl 1)
        m = m or (m shl 2)
        m = m or (m shl 4)
        m = m or (m shl 1 and x.inv() and -0x80000000)
        return m and -0x4
    }

    //Encrypt Data Block Function
    fun encryptBloc(`in`: ByteArray): ByteArray {
        val tmp = ByteArray(`in`.size)
        var aux: Int
        val data = IntArray(`in`.size / 4)
        for (i in data.indices) data[i] = 0
        var off = 0
        for (i in data.indices) {
            data[i] = `in`[off++].toInt() and 0xff or
                    (`in`[off++].toInt() and 0xff shl 8) or
                    (`in`[off++].toInt() and 0xff shl 16) or
                    (`in`[off++].toInt() and 0xff shl 24)
        }
        var A = data[0]
        var B = data[1]
        var C = data[2]
        var D = data[3]
        A = A + K[0]
        B = B + K[1]
        C = C + K[2]
        D = D + K[3]

        //Phase 1: Forward Mixing
        for (i in 0..7) {
            B = B xor s_box[A and 0xff]
            B = B + s_box[(rotateRight(A, 8) and 0xff) + 256]
            C = C + s_box[rotateRight(A, 16) and 0xff]
            D = D xor s_box[(rotateRight(A, 24) and 0xff) + 256]
            A = rotateRight(A, 24)
            if (i == 1 || i == 5) A = A + B
            if (i == 0 || i == 4) A = A + D
            aux = A
            A = B
            B = C
            C = D
            D = aux
        }
        var R: Int
        var L: Int
        var M: Int
        var eout: IntArray

        //Main Cryptographic Core
        for (i in 0..15) {
            eout = E_func(A, K[2 * i + 4], K[2 * i + 5])
            A = rotateLeft(A, 13)
            C = C + eout[1]
            if (i < 8) {
                B = B + eout[0]
                D = D xor eout[2]
            } else {
                D = D + eout[0]
                B = B xor eout[2]
            }
            aux = A
            A = B
            B = C
            C = D
            D = aux
        }
        //Phase 3: Backwards Mixing
        for (i in 0..7) {
            if (i == 3 || i == 7) A = A - B
            if (i == 2 || i == 6) A = A - D
            B = B xor s_box[(A and 0xff) + 256]
            C = C - s_box[rotateRight(A, 24) and 0xff]
            D = D - s_box[(rotateRight(A, 16) and 0xff) + 256]
            D = D xor s_box[rotateRight(A, 8) and 0xff]
            A = rotateLeft(A, 24)
            aux = A
            A = B
            B = C
            C = D
            D = aux
        }
        A = A - K[36]
        B = B - K[37]
        C = C - K[38]
        D = D - K[39]
        data[0] = A
        data[1] = B
        data[2] = C
        data[3] = D
        for (i in tmp.indices) {
            tmp[i] = (data[i / 4] ushr i % 4 * 8 and 0xff).toByte()
        }
        return tmp
    }

    //E-Function
    private fun E_func(`in`: Int, k1: Int, k2: Int): IntArray {
        val tmp = IntArray(3)
        var M: Int
        var L: Int
        var R: Int
        M = `in` + k1
        R = rotateLeft(`in`, 13) * k2
        L = s_box[M and 0x000001ff]
        R = rotateLeft(R, 5)
        M = rotateLeft(M, R and 0x0000001f)
        L = L xor R
        R = rotateLeft(R, 5)
        L = L xor R
        L = rotateLeft(L, R and 0x0000001f)
        tmp[0] = L
        tmp[1] = M
        tmp[2] = R
        return tmp
    }

    //Decrypt Data Block Function
    fun decryptBloc(`in`: ByteArray): ByteArray {
        val tmp = ByteArray(`in`.size)
        var aux: Int
        val data = IntArray(`in`.size / 4)
        for (i in data.indices) data[i] = 0
        var off = 0
        for (i in data.indices) {
            data[i] = `in`[off++].toInt() and 0xff or
                    (`in`[off++].toInt() and 0xff shl 8) or
                    (`in`[off++].toInt() and 0xff shl 16) or
                    (`in`[off++].toInt() and 0xff shl 24)
        }
        var A = data[0]
        var B = data[1]
        var C = data[2]
        var D = data[3]
        A = A + K[36]
        B = B + K[37]
        C = C + K[38]
        D = D + K[39]

        //Phase 1 Forward Mixing
        for (i in 7 downTo 0) {
            aux = D
            D = C
            C = B
            B = A
            A = aux
            A = rotateRight(A, 24)
            D = D xor s_box[rotateRight(A, 8) and 0xff]
            D = D + s_box[(rotateRight(A, 16) and 0xff) + 256]
            C = C + s_box[rotateRight(A, 24) and 0xff]
            B = B xor s_box[(A and 0xff) + 256]
            if (i == 2 || i == 6) A = A + D
            if (i == 3 || i == 7) A = A + B
        }
        var eout: IntArray
        // Cryptographic Core
        for (i in 15 downTo 0) {
            aux = D
            D = C
            C = B
            B = A
            A = aux
            A = rotateRight(A, 13)
            eout = E_func(A, K[2 * i + 4], K[2 * i + 5])
            C = C - eout[1]
            if (i < 8) {
                B = B - eout[0]
                D = D xor eout[2]
            } else {
                D = D - eout[0]
                B = B xor eout[2]
            }
        }
        //Phase 3 Backwards Mixing
        for (i in 7 downTo 0) {
            aux = D
            D = C
            C = B
            B = A
            A = aux
            if (i == 0 || i == 4) A = A - D
            if (i == 1 || i == 5) A = A - B
            A = rotateLeft(A, 24)
            D = D xor s_box[(rotateRight(A, 24) and 0xff) + 256]
            C = C - s_box[rotateRight(A, 16) and 0xff]
            B = B - s_box[(rotateRight(A, 8) and 0xff) + 256]
            B = B xor s_box[A and 0xff]
        }
        A = A - K[0]
        B = B - K[1]
        C = C - K[2]
        D = D - K[3]
        data[0] = A
        data[1] = B
        data[2] = C
        data[3] = D
        for (i in tmp.indices) {
            tmp[i] = (data[i / 4] ushr i % 4 * 8 and 0xff).toByte()
        }
        return tmp
    }

    //Main Encryption Function
    fun encrypt(`in`: ByteArray, key: ByteArray): ByteArray {
        K = expandKey(key)
        var lenght = 0
        var i: Int
        lenght = 16 - `in`.size % 16

        var padding = if (lenght == 0) {
            ByteArray(16) { 16 }
        } else {
            ByteArray(lenght) { lenght.toByte() }
        }

        val tmp = ByteArray(`in`.size + lenght)
        var bloc = ByteArray(16)
        var count = 0
        //16 Rounds of Mayn Keyed Transformation
        i = 0
        while (i < `in`.size + lenght) {
            if (i > 0 && i % 16 == 0) {
                bloc = encryptBloc(bloc)
                System.arraycopy(bloc, 0, tmp, i - 16, bloc.size)
            }
            if (i < `in`.size) bloc[i % 16] = `in`[i] else {
                bloc[i % 16] = padding[count % 16]
                count++
            }
            i++
        }
        if (bloc.size == 16) {
            bloc = encryptBloc(bloc)
            System.arraycopy(bloc, 0, tmp, i - 16, bloc.size)
        }
        return tmp
    }

    //Main Decryption Function
    fun decrypt(`in`: ByteArray, key: ByteArray): ByteArray {
        var tmp = ByteArray(`in`.size)
        var bloc = ByteArray(16)
        K = expandKey(key)
        var i: Int
        //16 Rounds of Main Keyed Transformation
        i = 0
        while (i < `in`.size) {
            if (i > 0 && i % 16 == 0) {
                bloc = decryptBloc(bloc)
                System.arraycopy(bloc, 0, tmp, i - 16, bloc.size)
            }
            if (i < `in`.size) bloc[i % 16] = `in`[i]
            i++
        }
        bloc = decryptBloc(bloc)
        System.arraycopy(bloc, 0, tmp, i - 16, bloc.size)
        tmp = deletePadding(tmp)
        return tmp
    }

    private fun deletePadding(input: ByteArray): ByteArray {
        val padding = input.last()
        return input.dropLast(padding.toInt()).toByteArray()
    }
}