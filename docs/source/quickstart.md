# Quickstart

## Dependencies

This module requires API key to function. You may subscribe a free API key at [https://www.fraudlabspro.com](https://www.fraudlabspro.com)

## Sample Codes

### Validate Order

You can validate your order as below:

```scala
import com.fraudlabspro._
import java.util

object TestValidate {
  def main(args: Array[String]): Unit = {
    // Configures FraudLabs Pro API key
    FraudLabsPro.APIKEY = "YOUR_API_KEY"

    // Screen Order API
    val order = new Order
    // Sets order details
    val data = new util.Hashtable[String, String]
    data.put("ip", "146.112.62.105") // IP parameter is mandatory

    data.put("first_name", "Hector")
    data.put("last_name", "Henderson")
    data.put("email", "hh5566@gmail.com")
    data.put("user_phone", "561-628-8674")
    // Billing information
    data.put("bill_addr", "1766 PowderHouse Road")
    data.put("bill_city", "West Palm Beach")
    data.put("bill_state", "FL")
    data.put("bill_country", "US")
    data.put("bill_zip_code", "33401")
    data.put("number", "4556553172971283")
    // Order information
    data.put("user_order_id", "67398")
    data.put("user_order_memo", "Online shop")
    data.put("amount", "79.89")
    data.put("quantity", "1")
    data.put("currency", "USD")
    data.put("payment_gateway", "stripe")
    data.put("payment_mode", order.CREDIT_CARD) // Please refer reference section for full list of payment methods

    // Shipping information
    data.put("ship_first_name", "Hector")
    data.put("ship_last_name", "Henderson")
    data.put("ship_addr", "4469 Chestnut Street")
    data.put("ship_city", "Tampa")
    data.put("ship_state", "FL")
    data.put("ship_zip_code", "33602")
    data.put("ship_country", "US")
    val result = order.validate(data) // Sends order details to FraudLabs Pro
    println(result)
  }
}
```

### Get Transaction

You can get the details of a transaction as below:

```scala
import com.fraudlabspro._
import java.util

object TestGetTransaction {
  def main(args: Array[String]): Unit = {
    // Configures FraudLabs Pro API key
    FraudLabsPro.APIKEY = "YOUR_API_KEY"
    // Get Order Result API
    val orderResults = new Order
    // Sets order ID to return all available information regarding the order
    val data = new util.Hashtable[String, String]
    data.put("id", "20180709-NHAEUK")
    val result = orderResults.getTransaction(data) // Obtains order results from FraudLabs Pro
    println(result)
  }
}
```

### Feedback

You can approve, reject or blacklist a transaction as below:

```scala
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
```

### Send SMS Verification

You can send SMS verification for authentication purpose as below:

```scala
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
```

### Get SMS Verification Result

You can verify the OTP sent by Fraudlabs Pro SMS verification API as below:

```scala
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
```