package com.fraudlabspro

import java.net.{URL, URLEncoder}
import java.security.MessageDigest
import java.text.DecimalFormat
import java.util
import scala.jdk.CollectionConverters.*

/**
 * FraudLabsPro Order module.
 * Validates order for possible fraud and feedback user decision.
 */
class Order {
  /**
   * Order statuses.
   *
   * string
   */
  final val APPROVE = "APPROVE"
  final val REJECT = "REJECT"
  final val REJECT_BLACKLIST = "REJECT_BLACKLIST"
  /**
   * Payment methods.
   *
   * string
   */
  final val CREDIT_CARD = "CREDITCARD"
  final val PAYPAL = "PAYPAL"
  final val CASH_ON_DELIVERY = "COD"
  final val BANK_DEPOSIT = "BANKDEPOSIT"
  final val GIFT_CARD = "GIFTCARD"
  final val CRYPTO = "CRYPTO"
  final val WIRE_TRANSFER = "WIRED"
  final val OTHERS = "OTHERS"

  /** Screen Order API
   * Screen an order transaction for payment fraud.
   * This REST API will detect all possibles fraud traits based on the input parameters supplied.
   * The more input parameter supplied, the higher accuracy of fraud detection.
   *
   * @param data
   * Parameters that required to screen orders
   * @return string
   *         Returns processed orders in JSON || XML format
   */
  def validate(data: util.Hashtable[String, String]): String = {
    try {
      if (data.get("email") != null && data.get("email").nonEmpty) {
        data.put("email_domain", data.get("email").substring(data.get("email").indexOf("@") + 1)) // gets the email domain
        data.put("email_hash", doHash(data.get("email"))) // generates email hash
      }
      if (data.get("username") != null && data.get("username").nonEmpty) {
        data.put("username_hash", doHash(data.get("username"))) // generates username hash
        data.remove("username")
      }
      if (data.get("password") != null && data.get("password").nonEmpty) {
        data.put("password_hash", doHash(data.get("password"))) // generates password hash
        data.remove("password")
      }
      if (data.get("user_phone") != null && data.get("user_phone").nonEmpty) data.put("user_phone", data.get("user_phone").replaceAll("[^0-9]", ""))
      if (data.get("currency") == null || data.get("currency").isEmpty) data.put("currency", "USD") //  default currency is USD
      if (data.get("number") != null && data.get("number").nonEmpty) {
        data.put("bin_no", data.get("number").substring(0, 6))
        data.put("card_hash", doHash(data.get("number"))) // generates card hash
      }
      if (data.get("amount") != null && data.get("amount").nonEmpty) data.put("amount", new DecimalFormat("#.00").format(data.get("amount").toDouble))
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
      Http.post(new URL("https://api.fraudlabspro.com/v2/order/screen"), post)
    } catch {
      case e: Exception =>
        throw new RuntimeException(e)
    }
  }

  /** Feedback Order API
   * Update status of a transaction from pending-manual-review to APPROVE, REJECT or IGNORE.
   * The FraudLabs Pro algorithm will improve the formula in determine the FraudLabs Pro score using the data collected.
   *
   * @param data
   * Parameters that required to set feedback
   * @return string
   *         Returns order feedback in JSON || XML format
   */
  def feedback(data: util.Hashtable[String, String]): String = try {
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
    Http.post(new URL("https://api.fraudlabspro.com/v2/order/feedback"), post)
  } catch {
    case e: Exception =>
      throw new RuntimeException(e)
  }

  /** Get Order Result API
   * Retrieve an existing transaction from FraudLabs Pro fraud detection system.
   *
   * @param data
   * Parameters that required to get order results
   * @return string
   *         Returns order results in JSON || XML format
   */
  def getTransaction(data: util.Hashtable[String, String]): String = try {
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
    Http.get(new URL("https://api.fraudlabspro.com/v2/order/result?key=" + FraudLabsPro.APIKEY + dataStr))
  } catch {
    case e: Exception =>
      throw new RuntimeException(e)
  }

  /** To hash a string to protect its real value
   *
   * @param value
   * Value to be hashed
   * @return
   * Returns hashed string value
   */
  private def doHash(s: String): String = {
    // Step 1: initial prefix
    var hash = "fraudlabspro_" + s

    // Step 2: 65536 iterations of SHA-1
    val sha1 = MessageDigest.getInstance("SHA-1")
    for (_ <- 0 until 65536) {
      // reset & compute digest each iteration
      sha1.reset()
      sha1.update(("fraudlabspro_" + hash).getBytes("UTF-8"))
      val h = sha1.digest()
      // hex-encode
      hash = h.map("%02x".format(_)).mkString
    }

    // Step 3: final SHA-256
    val sha256 = MessageDigest.getInstance("SHA-256")
    sha256.update(hash.getBytes("UTF-8"))
    val h2 = sha256.digest()

    h2.map("%02x".format(_)).mkString
  }
}