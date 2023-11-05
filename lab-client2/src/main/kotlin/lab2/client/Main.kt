package lab2.client

import generated.ProductEndpointJavaStub
import generated.ProductEndpointJavaStub.StatusResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.system.exitProcess


fun downloadFileAsString(url: String): String {
    val connection = java.net.URL(url).openConnection() as HttpURLConnection
    connection.requestMethod = "GET"

    val responseCode = connection.responseCode
    if (responseCode == HttpURLConnection.HTTP_OK) {
        val inputStream = connection.inputStream
        val reader = BufferedReader(InputStreamReader(inputStream))
        val content = StringBuilder()
        var line: String?

        while (reader.readLine().also { line = it } != null) {
            content.append(line)
        }

        reader.close()
        return content.toString()
    } else {
        throw Exception("HTTP request failed with response code $responseCode")
    }
}

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

    var stub = ProductEndpointJavaStub()
    stub._getServiceClient().options.soapVersionURI = org.apache.axiom.soap.SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI;

    var resp = StatusResponse()
    resp.code = 200

    when (mode) {
        RequestType.Update -> {
            val updateRequest = ProductEndpointJavaStub.UpdateProductRequest()
            updateRequest.id = id
            updateRequest.name = name
            updateRequest.price = price
            updateRequest.producedBy = producedBy
            updateRequest.sellAmount = sellAmount

            var updateOp = ProductEndpointJavaStub.Update()
            updateOp.request = updateRequest

            resp = stub.update(updateOp)._return
        }
        RequestType.Create -> {
            val createRequest = ProductEndpointJavaStub.CreateProductRequest()
            createRequest.name = name
            createRequest.price = price
            createRequest.producedBy = producedBy
            createRequest.sellAmount = sellAmount

            val createOp = ProductEndpointJavaStub.Create()
            createOp.request = createRequest

            resp = stub.create(createOp)._return
        }
        RequestType.Read -> {
            val readRequest = ProductEndpointJavaStub.ReadProductRequest()
            readRequest.name = name
            readRequest.price = price
            readRequest.producedBy = producedBy
            readRequest.sellAmount = sellAmount
            readRequest.id = id

            val readOp = ProductEndpointJavaStub.Read()
            readOp.request = readRequest

            val products = stub.read(readOp)._return
            for (i in 0 until products.products.size) {
                val product = products.products[i]
                println("Product #${i + 1}:")
                println("\tID: ${product.id}")
                println("\tName: ${product.name}")
                println("\tProduced By: ${product.producedBy}")
                println("\tPrice: ${product.price}")
                println("\tSell Amount: ${product.sellAmount}")
            }
        }
        RequestType.Delete -> {
            val deleteRequest = ProductEndpointJavaStub.DeleteProductRequest()
            deleteRequest.id = id

            val deleteOp = ProductEndpointJavaStub.Delete()
            deleteOp.request = deleteRequest

            resp = stub.delete(deleteOp)._return
        }
        else -> {}
    }

    println("Status code: ${resp.code}")
    println("Status message: ${resp.errorMessage ?: "None"}")

//    traverseXML(xsd.documentElement, "")

//    val createBody = """
//                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
//				  xmlns:gs="http://rbetik12.com/generated">
//                <soapenv:Header/>
//                <soapenv:Body>
//                    <gs:createProductRequest>
//                    <gs:name>${name}</gs:name>
//                    <gs:producedBy>${producedBy}</gs:producedBy>
//                    <gs:price>${price}</gs:price>
//                    <gs:sellAmount>${sellAmount}</gs:sellAmount>
//                    </gs:createProductRequest>
//                </soapenv:Body>
//            </soapenv:Envelope>
//            """.trimIndent()
//
//    val readBody = """
//                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
//				  xmlns:gs="http://rbetik12.com/generated">
//                <soapenv:Header/>
//                <soapenv:Body>
//                    <gs:readProductRequest>
//                    <gs:id>${id}</gs:id>
//                    <gs:name>${name}</gs:name>
//                    <gs:producedBy>${producedBy}</gs:producedBy>
//                    <gs:price>${price}</gs:price>
//                    <gs:sellAmount>${sellAmount}</gs:sellAmount>
//                    </gs:readProductRequest>
//                </soapenv:Body>
//            </soapenv:Envelope>
//            """.trimIndent()
//
//    val updateBody = """
//                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
//				  xmlns:gs="http://rbetik12.com/generated">
//                <soapenv:Header/>
//                <soapenv:Body>
//                    <gs:updateProductRequest>
//                    <gs:id>${id}</gs:id>
//                    <gs:name>${name}</gs:name>
//                    <gs:producedBy>${producedBy}</gs:producedBy>
//                    <gs:price>${price}</gs:price>
//                    <gs:sellAmount>${sellAmount}</gs:sellAmount>
//                    </gs:updateProductRequest>
//                </soapenv:Body>
//                </soapenv:Envelope>
//            """.trimIndent()
//
//    val deleteBody = """
//                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
//				  xmlns:gs="http://rbetik12.com/generated">
//                <soapenv:Header/>
//                <soapenv:Body>
//                    <gs:deleteProductRequest>
//                    <gs:id>${id}</gs:id>
//                    </gs:deleteProductRequest>
//                </soapenv:Body>
//                </soapenv:Envelope>
//            """.trimIndent()
//
//    val requestBody = when (mode) {
//        RequestType.Create -> createBody
//        RequestType.Read -> readBody
//        RequestType.Update -> updateBody
//        RequestType.Delete -> deleteBody
//        else -> {
//            ""
//        }
//    }
//
//    val client = HttpClient.newBuilder().build()
//    val request = HttpRequest.newBuilder()
//        .uri(URI.create(address))
//        .header("Content-Type", "text/xml")
//        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
//        .build()
//
//    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
//    println(response.body())
}