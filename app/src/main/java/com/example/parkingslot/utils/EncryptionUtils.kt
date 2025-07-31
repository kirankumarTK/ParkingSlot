package com.example.parkingslot.utils

import android.content.SharedPreferences
import android.util.Base64
import androidx.core.content.edit
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

/* Using AES encryption to encrypt password & other key */
class EncryptionUtils @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
    private val networkUtils: NetworkUtils,
    private val encryptPreference: SharedPreferences,
    private val loggerUtils: LoggerUtils
) {

    val ivSize = 16

    internal fun encrypt(inputData: String, key: String): String {
        val iv = ByteArray(ivSize)
        SecureRandom().nextBytes(iv)
        val ivSpec = IvParameterSpec(iv)
        val encryptKey = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, encryptKey, ivSpec)

        val encrypted = cipher.doFinal(inputData.toByteArray(Charsets.UTF_8))
        val combined = iv + encrypted // prepend IV to ciphertext

        return Base64.encodeToString(combined, Base64.NO_WRAP)
    }

    internal fun decrypt(encryptedBase64: String, key: String): String {
        val combined = Base64.decode(encryptedBase64, Base64.NO_WRAP)

        val iv = combined.copyOfRange(0, ivSize)
        val encrypted = combined.copyOfRange(ivSize, combined.size)

        val ivSpec = IvParameterSpec(iv)

        val encryptKey = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, encryptKey, ivSpec)

        val decrypted = cipher.doFinal(encrypted)
        return String(decrypted, Charsets.UTF_8)
    }

    internal fun provideEncryptKey(): String {
        var encryptKey = ""
        encryptKey = encryptPreference.getString("encrypt_key", "").toString()
        if (encryptKey.isBlank() && networkUtils.isNetworkAvailable()) {
            remoteConfig.fetchAndActivate().addOnCompleteListener {
                if (it.isSuccessful) {
                    encryptPreference.edit {
                        putString(
                            "encrypt_key", remoteConfig.getString("encryption_key")
                        )
                    }
                    encryptKey = encryptPreference.getString("encrypt_key", "").toString()
                } else {

                }
            }
        }
        loggerUtils.info("Encrypt", encryptKey)
        return encryptKey
    }
}