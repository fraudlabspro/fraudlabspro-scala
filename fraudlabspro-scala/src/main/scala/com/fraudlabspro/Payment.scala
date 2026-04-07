package com.fraudlabspro

import java.io.IOException
import java.net.{URL, URLEncoder}
import java.util
import scala.jdk.CollectionConverters.*

class Payment {
  /** Payment Feedback API
   * Report the final payment status back to the system, helping improve fraud detection and risk assessment.
   *
   * @param data
   * Parameters that required to send payment feedback
   * @return string
   *         Returns feedback results in JSON || XML format
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
    Http.post(new URL("https://api.fraudlabspro.com/v2/payment/feedback"), post)
  } catch {
    case e: IOException =>
      throw new RuntimeException(e)
  }
}