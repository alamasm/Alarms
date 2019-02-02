package com.example.egor.alarms.Model.Cryptography

import java.security.MessageDigest

class AndroidCrypter() : CryptInterface {
    private val digest: MessageDigest = MessageDigest.getInstance("MD5")

    override fun getHash(s: String): String {
        val HEX_CHARS = "0123456789ABCDEF"
        val bytes = digest.digest(s.toByteArray())
        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(HEX_CHARS[i shr 4 and 0x0f])
            result.append(HEX_CHARS[i and 0x0f])
        }

        return result.toString()
    }
}