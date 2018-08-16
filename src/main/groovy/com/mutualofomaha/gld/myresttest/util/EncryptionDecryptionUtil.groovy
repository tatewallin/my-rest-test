package com.mutualofomaha.gld.myresttest.util

import org.apache.commons.codec.binary.Base64
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * See: http://www.javamex.com/tutorials/cryptography/block_modes_java.shtml
 */
@Service
class EncryptionDecryptionUtil {
    private static final String xform = "AES/CBC/PKCS5Padding"
    private static final byte[] iv = [ 0x0a, 0x01, 0x02, 0x03, 0x04, 0x0b, 0x0c, 0x0d, 0x01, 0x02, 0x03, 0x00, 0x00, 0x06, 0x02, 0x05 ]

    String encrypt(String clearText, String base64EncodedKeyString) {
        return Base64.encodeBase64String(crypt(Cipher.ENCRYPT_MODE, clearText.getBytes(), base64EncodedKeyString))
    }

    String decrypt(String base64EncodedEncryptedText, String base64EncodedKeyString) {
        return new String(crypt(Cipher.DECRYPT_MODE, Base64.decodeBase64(base64EncodedEncryptedText), base64EncodedKeyString))
    }

    private byte[] crypt(int mode, byte[] textToCryptInByteArray, String base64EncodedKeyString) {
        byte[] decodedKeyStringBytes = Base64.decodeBase64(base64EncodedKeyString.getBytes())
        SecretKey key = new SecretKeySpec(decodedKeyStringBytes, "AES")
        IvParameterSpec ips = new IvParameterSpec(iv)

        Cipher cipher = Cipher.getInstance(xform)
        cipher.init(mode, key, ips)

        return cipher.doFinal(textToCryptInByteArray)
    }

    // This is only used when generating a SecretKey instance. After that, the generated SecretKey will be stored.
    String generateKeyString() {
        KeyGenerator kg = KeyGenerator.getInstance("AES")
        kg.init(128)
        byte[] key = kg.generateKey().getEncoded()
        return Base64.encodeBase64String(key)
    }
}