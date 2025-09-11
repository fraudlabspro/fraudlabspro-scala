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
