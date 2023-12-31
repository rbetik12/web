package lab2.client

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.JavaNetCookieJar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.CookieManager
import java.net.CookiePolicy
import kotlin.system.exitProcess


fun main(args: Array<String>) {
    var id = 0L
    var name = ""
    var producedBy = ""
    var price = 0.0f
    var sellAmount = -1L
    var mode = RequestType.None
    var address = "localhost:8081"

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
        if (args[i] == "--address") {
            address = args[i + 1]
        }
    }

    if (mode == RequestType.None) {
        System.err.println("Usage: --id [id]\n--name [name]\n--producedBy [produced by]\n--price [price]\n--sellAmount [sell amount]\n--mode [create, read, update, delete]\n--address [localhost:8080]\n")
        exitProcess(1)
    }

    val product = Product(id, name, producedBy, price, sellAmount)

    var responseBody = ""
    var responseCode = 0

    val cookieManager = CookieManager()
    cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
    val cookies = JavaNetCookieJar(cookieManager)

    val client = OkHttpClient().newBuilder().cookieJar(cookies).build()
    val mediaType = "application/json; charset=utf-8".toMediaType()

    val user = User("user", "password")
    val userJson = Json.encodeToString(user)
    val request = Request.Builder()
        .url("http://${address}/api/product/auth")
        .post(userJson.toRequestBody(mediaType))
        .build()
    val response = client.newCall(request).execute()

    when (mode) {
        RequestType.Create -> {
            val jsonString = Json.encodeToString(product)

            val request = Request.Builder()
                .url("http://${address}/api/product/")
                .post(jsonString.toRequestBody(mediaType))
                .build()

            val response = client.newCall(request).execute()
            responseBody = response.body?.string() ?: ""
            responseCode = response.code
        }
        RequestType.Read -> {
            val request = Request.Builder()
                .url("http://${address}/api/product/${product.id}")
                .get()
                .build()

            val response = client.newCall(request).execute()
            responseBody = response.body?.string() ?: ""
            responseCode = response.code
        }
        RequestType.Update -> {
            val jsonString = Json.encodeToString(product)

            val request = Request.Builder()
                .url("http://${address}/api/product/")
                .put(jsonString.toRequestBody(mediaType))
                .build()

            val response = client.newCall(request).execute()
            responseBody = response.body?.string() ?: ""
            responseCode = response.code
        }
        RequestType.Delete -> {
            val request = Request.Builder()
                .url("http://${address}/api/product/${product.id}")
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