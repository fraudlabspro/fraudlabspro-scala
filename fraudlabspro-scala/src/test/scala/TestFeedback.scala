import com.fraudlabspro._
import java.util

object TestFeedback {
  def main(args: Array[String]): Unit = {
    // Configures FraudLabs Pro API key
    FraudLabsPro.APIKEY = "YOUR_API_KEY"
    // Feedback Order API
    val fb = new Order
    // Sets feedback details
    val data = new util.Hashtable[String, String]
    data.put("id", "20180709-NHAEUK")
    data.put("action", fb.REJECT) // Please refer to reference section for full list of feedback statuses

    val result = fb.feedback(data) // Sends feedback details to FraudLabs Pro
    println(result)
  }
}
