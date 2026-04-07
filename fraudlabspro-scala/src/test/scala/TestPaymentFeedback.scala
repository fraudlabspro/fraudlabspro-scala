import com.fraudlabspro.*

import java.util

object TestPaymentFeedback {
  def main(args: Array[String]): Unit = {
    // Configures FraudLabs Pro API key
    FraudLabsPro.APIKEY = "YOUR_API_KEY"
    // Get Payment Feedback API
    val pay = new Payment
    // Sets feedback details
    val data = new util.Hashtable[String, String]
    data.put("email", "hh5566@gmail.com")
    data.put("status", "declined")
    data.put("message", "Call Issuer. Pick Up Card. (2047)")
    data.put("fraudlabspro_id", "20260131-O263CR")
    val result = pay.feedback(data)
    println(result)
  }
}
