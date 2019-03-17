package com.bheaver.ngl4.aa.utils
import java.security.MessageDigest
import java.math.BigInteger

object MD5Utils {
  val md = MessageDigest.getInstance("MD5")
  def md5HashString(s: String): String = {
    val digest = md.digest(s.getBytes)
    val bigInt = new BigInteger(1,digest)
    val hashedString = bigInt.toString(16)
    hashedString
  }
}
