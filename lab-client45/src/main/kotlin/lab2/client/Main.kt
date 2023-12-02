package lab2.client

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    var id = 0L
    var name = ""
    var producedBy = ""
    var price = 0.0f
    var sellAmount = -1L
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
        if (args[i] == "--mode") {
            mode = RequestType.valueOf(args[i + 1])
        }
    }

    if (mode == RequestType.None) {
        System.err.println("Usage: --id [id]\n--name [name]\n--producedBy [produced by]\n--price [price]\n--sellAmount [sell amount]\n--mode [create, read, update, delete]\n")
        exitProcess(1)
    }

    val product = Product(id, name, producedBy, price, sellAmount)

    var responseBody = ""
    var responseCode = 0

    val client = OkHttpClient()
    val mediaType = "application/json; charset=utf-8".toMediaType()

    when (mode) {
        RequestType.Create -> {
            val jsonString = Json.encodeToString(product)

            val request = Request.Builder()
                .url("http://localhost:8080/api/rest/new")
                .post(jsonString.toRequestBody(mediaType))
                .build()

            val response = client.newCall(request).execute()
            responseBody = response.body?.string() ?: ""
            responseCode = response.code
        }
        RequestType.Read -> {
            val request = Request.Builder()
                .url("http://localhost:8080/api/rest/${product.id}")
                .get()
                .build()

            val response = client.newCall(request).execute()
            responseBody = response.body?.string() ?: ""
            responseCode = response.code
        }
        RequestType.Update -> {
            val jsonString = Json.encodeToString(product)

            val request = Request.Builder()
                .url("http://localhost:8080/api/rest/update")
                .put(jsonString.toRequestBody(mediaType))
                .build()

            val response = client.newCall(request).execute()
            responseBody = response.body?.string() ?: ""
            responseCode = response.code
        }
        RequestType.Delete -> {
            val request = Request.Builder()
                .url("http://localhost:8080/api/rest/delete/${product.id}")
                .delete()
                .build()

            val response = client.newCall(request).execute()
            responseBody = response.body?.string() ?: ""
            responseCode = response.code
        }
        else -> {}
    }

    println("Response code: $responseCode")
    println("Response body: $responseBody")
}