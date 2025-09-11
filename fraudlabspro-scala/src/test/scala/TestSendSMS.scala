import com.fraudlabspro._
import java.util

object TestSendSMS {
  def main(args: Array[String]): Unit = {
    // Configures FraudLabs Pro API key
    FraudLabsPro.APIKEY = "YOUR_API_KEY"
    // Send SMS Verification API
    val sms = new SMSVerification
    // Sets SMS details for authentication purpose
    val data = new util.Hashtable[String, String]
    data.put("tel", "+123456789")
    data.put("country_code", "US")
    data.put("mesg", "Hi, your OTP is <otp>.")
    data.put("otp_timeout", "3600")
    val result = sms.sendSMS(data)
    println(result)
  }
}
