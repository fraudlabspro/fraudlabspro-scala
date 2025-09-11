import com.fraudlabspro._
import java.util

object TestVerifySMS {
  def main(args: Array[String]): Unit = {
    // Configures FraudLabs Pro API key
    FraudLabsPro.APIKEY = "YOUR_API_KEY"
    // Get Verification Result API
    val verification = new SMSVerification
    // Sets transaction ID and otp details for verification purpose
    val data = new util.Hashtable[String, String]
    data.put("tran_id", "OTP_RECEIVED")
    data.put("otp", "100038")
    val result = verification.verifySMS(data)
    println(result)
  }
}
