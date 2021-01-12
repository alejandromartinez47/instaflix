package co.instaflix.config.extensions

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser


internal fun String.toJson(): JsonObject = JsonParser()
        .parse(this)
        .asJsonObject

internal fun String.isJson() = try {
        val gson = Gson()
        gson.fromJson(this, Any::class.java)
        true
    } catch (ex: com.google.gson.JsonSyntaxException) {
        false
    }
