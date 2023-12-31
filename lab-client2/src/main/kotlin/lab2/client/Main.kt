package lab2.client

import lab2.generated.ProductWebServiceStub
import org.apache.axis2.AxisFault
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.system.exitProcess

fun fileToBase64(filePath: String): String {
    val path = Path.of(filePath)
    val fileBytes = Files.readAllBytes(path)
    val base64String = Base64.getEncoder().encodeToString(fileBytes)
    return base64String
}

fun main(args: Array<String>) {
    var id = 0L
    var name = ""
    var producedBy = ""
    var price = 0.0f
    var sellAmount = -1L
    var mode = RequestType.None
    var filepath = ""

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
        if (args[i] == "--mode") {
            mode = RequestType.valueOf(args[i + 1])
        }
        if (args[i] == "--file") {
            filepath = args[i + 1]
        }
    }

    if (mode == RequestType.None) {
        System.err.println("Usage: --id [id]\n--name [name]\n--producedBy [produced by]\n--price [price]\n--sellAmount [sell amount]\n--mode [create, read, update, delete, binary]\n--filepath [path]")
        exitProcess(1)
    }

    val stub = ProductWebServiceStub()
    stub._getServiceClient().options.soapVersionURI = org.apache.axiom.soap.SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI;

    var resp = ProductWebServiceStub.Response()
    resp.code = -1
    resp.message = "Error"

    val product = ProductWebServiceStub.Product()
    product.id = id
    product.name = name
    product.price = price
    product.producedBy = producedBy
    product.sellAmount = sellAmount
    product.username = "user"
    product.password = "password"

    when (mode) {
        RequestType.Update -> {
            val updateRequest = ProductWebServiceStub.Update()
            updateRequest.arg0 = product

            val updateOp = ProductWebServiceStub.UpdateE()
            updateOp.update = updateRequest

            try {
                resp = stub.update(updateOp).updateResponse._return
            } catch (ex: AxisFault) {
                resp.message = ex.message
            }
        }
        RequestType.Create -> {
            val createRequest = ProductWebServiceStub.Create()
            createRequest.arg0 = product

            val createOp = ProductWebServiceStub.CreateE()
            createOp.create = createRequest

            try {
                resp = stub.create(createOp).createResponse._return
            } catch (ex: AxisFault) {
                resp.message = ex.message
            }
        }
        RequestType.Read -> {
            val request = ProductWebServiceStub.Request()
            request.id = product.id
            request.username = "user"
            request.password = "password"

            val readRequest = ProductWebServiceStub.Read()
            readRequest.arg0 = request
            val readOp = ProductWebServiceStub.ReadE()
            readOp.read = readRequest

            var products: Array<ProductWebServiceStub.Product> = arrayOf()

            try {
                products = stub.read(readOp).readResponse._return
            } catch (ex: AxisFault) {
                resp.message = ex.message
            }
            for (i in products.indices) {
                val productRet = products[i]
                println("Product #${i + 1}:")
                println("\tID: ${productRet.id}")
                println("\tName: ${productRet.name}")
                println("\tProduced By: ${productRet.producedBy}")
                println("\tPrice: ${productRet.price}")
                println("\tSell Amount: ${productRet.sellAmount}")
            }
        }
        RequestType.Delete -> {
            val deleteRequest = ProductWebServiceStub.Request()
            deleteRequest.id = product.id
            deleteRequest.username = "user"
            deleteRequest.password = "password"

            val ddelete = ProductWebServiceStub.Delete()
            ddelete.arg0 = deleteRequest

            val deleteOp = ProductWebServiceStub.DeleteE()
            deleteOp.delete = ddelete

            try {
                resp = stub.delete(deleteOp).deleteResponse._return
            } catch (ex: AxisFault) {
                resp.message = ex.message
            }
        }
        RequestType.Binary -> {
            val binaryRequest = ProductWebServiceStub.ReceiveBinary()
            binaryRequest.arg0 = ProductWebServiceStub.Binary()
            binaryRequest.arg0.filename = filepath
            binaryRequest.arg0.payload = fileToBase64(filepath)

            val binaryOp = ProductWebServiceStub.ReceiveBinaryE()
            binaryOp.receiveBinary = binaryRequest

            try {
                resp = stub.receiveBinary(binaryOp).receiveBinaryResponse._return
            } catch (ex: AxisFault) {
                resp.message = ex.message
            }
        }
        else -> {}
    }

    println("Status code: ${resp.code}")
    println("Status message: ${resp.message}")
}