import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    var id = 0L
    var name = ""
    var producedBy = ""
    var price = 0.0f
    var sellAmount = -1L
    var address = ""
    var mode = RequestType.None

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
        if (args[i] == "--address") {
            address = args[i + 1]
        }
        if (args[i] == "--mode") {
            mode = RequestType.valueOf(args[i + 1])
        }
    }

    if (mode == RequestType.None) {
        System.err.println("Usage: --id [id]\n--name [name]\n--producedBy [produced by]\n--price [price]\n--sellAmount [sell amount]\n--address [address]\n--mode [create, read, update, delete]")
        exitProcess(1)
    }

    val createBody = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
				  xmlns:gs="http://rbetik12.com/generated">
                <soapenv:Header/>
                <soapenv:Body>
                    <gs:createProductRequest>
                    <gs:name>${name}</gs:name>
                    <gs:producedBy>${producedBy}</gs:producedBy>
                    <gs:price>${price}</gs:price>
                    <gs:sellAmount>${sellAmount}</gs:sellAmount>
                    </gs:createProductRequest>
                </soapenv:Body>
            </soapenv:Envelope>
            """.trimIndent()

    val readBody = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
				  xmlns:gs="http://rbetik12.com/generated">
                <soapenv:Header/>
                <soapenv:Body>
                    <gs:readProductRequest>
                    <gs:id>${id}</gs:id>
                    <gs:name>${name}</gs:name>
                    <gs:producedBy>${producedBy}</gs:producedBy>
                    <gs:price>${price}</gs:price>
                    <gs:sellAmount>${sellAmount}</gs:sellAmount>
                    </gs:readProductRequest>
                </soapenv:Body>
            </soapenv:Envelope>
            """.trimIndent()

    val updateBody = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
				  xmlns:gs="http://rbetik12.com/generated">
                <soapenv:Header/>
                <soapenv:Body>
                    <gs:updateProductRequest>
                    <gs:id>${id}</gs:id>
                    <gs:name>${name}</gs:name>
                    <gs:producedBy>${producedBy}</gs:producedBy>
                    <gs:price>${price}</gs:price>
                    <gs:sellAmount>${sellAmount}</gs:sellAmount>
                    </gs:updateProductRequest>
                </soapenv:Body>
                </soapenv:Envelope>
            """.trimIndent()

    val deleteBody = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
				  xmlns:gs="http://rbetik12.com/generated">
                <soapenv:Header/>
                <soapenv:Body>
                    <gs:deleteProductRequest>
                    <gs:id>${id}</gs:id>
                    </gs:deleteProductRequest>
                </soapenv:Body>
                </soapenv:Envelope>
            """.trimIndent()

    val requestBody = when (mode) {
        RequestType.Create -> createBody
        RequestType.Read -> readBody
        RequestType.Update -> updateBody
        RequestType.Delete -> deleteBody
        else -> {
            ""
        }
    }

    val client = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder()
        .uri(URI.create(address))
        .header("Content-Type", "text/xml")
        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
        .build()

    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    println(response.body())
}