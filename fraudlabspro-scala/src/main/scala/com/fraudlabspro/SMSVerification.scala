package com.fraudlabspro

import java.io.IOException
import java.net.URL
import java.net.URLEncoder
import java.util
import scala.jdk.CollectionConverters._

class SMSVerification {
  /** Send SMS Verification API
   * Send an SMS with verification code and a custom message for authentication purpose.
   *
   * @param data
   * Parameters that required to send SMS verification
   * @return string
   *         Returns SMS verification results in JSON || XML format
   */
  def sendSMS(data: util.Hashtable[String, String]): String = try {
    val dataStr = new StringBuilder
    data.put("source", FraudLabsPro.SOURCE)
    data.put("source_version", FraudLabsPro.VERSION)
    val scalaMap = (data: java.util.Map[String, String]).asScala // pick Map converter
    // iterate over Java map as Scala map
    for ((k, v) <- scalaMap) {
      dataStr.append("&").append(k)
        .append("=")
        .append(URLEncoder.encode(v, "UTF-8"))
    }
    val post = "key=" + FraudLabsPro.APIKEY + dataStr
    Http.post(new URL("https://api.fraudlabspro.com/v2/verification/send"), post)
  } catch {
    case e: IOException =>
      throw new RuntimeException(e)
  }

  /** Get Verification Result API
   * Verify that an OTP sent by the Send SMS Verification API is valid.
   *
   * @param data
   * Parameters that required to get SMS verification results
   * @return string
   *         Returns sms verification results in JSON || XML format
   */
  def verifySMS(data: util.Hashtable[String, String]): String = try {
    val dataStr = new StringBuilder
    data.put("source", FraudLabsPro.SOURCE)
    data.put("source_version", FraudLabsPro.VERSION)
    val scalaMap = (data: java.util.Map[String, String]).asScala // pick Map converter
    // iterate over Java map as Scala map
    for ((k, v) <- scalaMap) {
      dataStr.append("&").append(k)
        .append("=")
        .append(URLEncoder.encode(v, "UTF-8"))
    }
    Http.get(new URL("https://api.fraudlabspro.com/v2/verification/result?key=" + FraudLabsPro.APIKEY + dataStr))
  } catch {
    case e: Exception =>
      throw new RuntimeException(e)
  }
}