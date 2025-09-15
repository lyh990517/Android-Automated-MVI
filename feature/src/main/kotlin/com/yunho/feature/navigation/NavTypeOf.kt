package com.yunho.feature.navigation

import android.os.Bundle
import android.util.Base64
import androidx.navigation.NavType
import kotlinx.serialization.json.Json

inline fun <reified T> navTypeOf(isNullableAllowed: Boolean = false) = object : NavType<T>(
    isNullableAllowed = isNullableAllowed
) {
    override fun get(bundle: Bundle, key: String): T? {
        return bundle.getString(key)?.let(::parseValue)
    }

    override fun parseValue(value: String): T {
        val string = String(Base64.decode(value, Base64.URL_SAFE or Base64.NO_WRAP), Charsets.UTF_8)

        return Json.decodeFromString(string = string)
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, serializeAsValue(value))
    }

    override fun serializeAsValue(value: T): String {
        return Base64.encodeToString(
            Json.encodeToString(value).toByteArray(Charsets.UTF_8),
            Base64.URL_SAFE or Base64.NO_WRAP
        )
    }
}
