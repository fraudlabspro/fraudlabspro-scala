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