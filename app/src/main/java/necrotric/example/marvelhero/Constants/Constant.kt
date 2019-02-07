package necrotric.example.marvelhero.Constants

import java.security.MessageDigest

object Constant {

        val publicKey = "49196ef22fc45448d85934b565a84e74"
        val ts = 1
        val hash = calcHash()

        private fun calcHash(): String{
            val md5Hash = "MD5"
            val privateKey = "6c639657a2a0ee3715a18e32cfcdc26c78e3a132"
            val combinedString = ts.toString() + privateKey + publicKey
            val hex = "0123456789abcdef"
            val keyBytes = MessageDigest.getInstance(md5Hash).digest(combinedString.toByteArray())
            return keyBytes.joinToString(separator = "", transform = {
                    a -> String(charArrayOf(hex[a.toInt() shr 4 and 0x0f], hex[a.toInt() and 0x0f]))
            })
        }
    }

