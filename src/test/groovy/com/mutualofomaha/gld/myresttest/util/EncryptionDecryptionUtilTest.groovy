package com.mutualofomaha.gld.myresttest.util

import spock.lang.Shared
import spock.lang.Specification

class EncryptionDecryptionUtilTest extends Specification{

    @Shared
    String baselineEncryptedValue = "6LzJ/EVDZEen1LCo8MzWNA=="
    @Shared
    String baseLinePw = "Thi5IsD@Pa55"
    @Shared
    EncryptionDecryptionUtil util = new EncryptionDecryptionUtil()

    def "test encryption2"(){
        given:
        String encryptedValue = util.encrypt("g1d@svc#", "0HGi0PLnLaofVeEAI+M85g==")

        expect: true
        baselineEncryptedValue == encryptedValue
    }

    def "test encryption"(){
        given:
            String encryptedValue = util.encrypt(baseLinePw, R.Security.CRYPT_KEY)
        expect: true
           baselineEncryptedValue == encryptedValue
    }

    def "test decryption"(){
        given:
            String decryptedValue = util.decrypt(baselineEncryptedValue, R.Security.CRYPT_KEY)
        expect:
            decryptedValue == baseLinePw

    }

    def "test key generation"(){
        given:
            EncryptionDecryptionUtil util = new EncryptionDecryptionUtil()
            String keyString  = util.generateKeyString()
            println keyString
        expect:
            keyString != null
    }
}
