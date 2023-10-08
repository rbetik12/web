import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublisher
import java.net.http.HttpResponse

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            var id = 0L
            var name = ""
            var producedBy = ""
            var price = 0.0f
            var sellAmount = -1L

            for (i in args.indices) {
                if (args[i] == "--id") {
                    id = args[i + 1].toLong()
                }
                if (args[i] == "--name") {
                    name = args[i + 1]
                }
                if (args[i] == "--producedBy") {
                    producedBy = args[i + 1]
                }
                if (args[i] == "--price") {
                    price = args[i + 1].toFloat()
                }
                if (args[i] == "--sellAmount") {
                    sellAmount = args[i + 1].toLong()
                }
            }

            val requestBody = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                				  xmlns:gs="http://rbetik12.com/generated">
                    <soapenv:Header/>
                    <soapenv:Body>
                        <gs:getProductRequest>
                            <gs:id>${id}</gs:id>
                            <gs:name>${name}</gs:name>
                            <gs:producedBy>${producedBy}</gs:producedBy>
                            <gs:price>${price}</gs:price>
                            <gs:sellAmount>${sellAmount}</gs:sellAmount>
                        </gs:getProductRequest>
                    </soapenv:Body>
                </soapenv:Envelope>
            """.trimIndent()

            val client = HttpClient.newBuilder().build()
            val request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/products/soap"))
                .header("Content-Type", "text/xml")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build()

            val response = client.send(request, HttpResponse.BodyHandlers.ofString())
            println(response.body())
        }
    }
}