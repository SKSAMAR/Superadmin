package com.fintech.superadmin.util.service

import com.fintech.superadmin.BuildConfig
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


object KotEncryption {

    private fun cipher(opmode: Int, secretKey: String): Cipher {

        if (secretKey.length != 32) throw RuntimeException("SecretKey length is not 32 chars")
        val c = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val sk = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "AES")
        val iv = IvParameterSpec(secretKey.substring(0, 16).toByteArray(Charsets.UTF_8))
        c.init(opmode, sk, iv)
        return c
    }

    fun encrypt(str: String): String {
        val secretKey = "ThisForSomethrtAndBeSuterity2325"
        return try {
            val encrypted =
                cipher(Cipher.ENCRYPT_MODE, secretKey).doFinal(str.toByteArray(Charsets.UTF_8))
            String(android.util.Base64.encode(encrypted, android.util.Base64.DEFAULT))
        } catch (e: Exception) {
            e.message ?: "Some authentication error: ${e.localizedMessage}"
        }
    }

    fun decrypt(str: String): String {
        val secretKey = "ThisForSomethrtAndBeSuterity2325"
        return try {
            val byteStr = android.util.Base64.decode(
                str.toByteArray(Charsets.UTF_8),
                android.util.Base64.DEFAULT
            )
            String(cipher(Cipher.DECRYPT_MODE, secretKey).doFinal(byteStr))
        } catch (e: Exception) {
            e.message ?: "Some authentication error: ${e.localizedMessage}"
        }
    }
}

