package dev.b3nedikt.restring.example

import android.content.Context
import dev.b3nedikt.restring.PluralKeyword
import java.io.IOException
import java.util.*

object Utils {
    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getStrings(data: List<ExampleKey>): Map<String, CharSequence> {
        val map = mutableMapOf<String, CharSequence>()

        data.forEach { key ->
            if (key.type == "LABEL") {
                key.value?.let { map.put(key.key, it) }
            }

        }


        return map
    }

    fun getStringArrays(data: List<ExampleKey>, keyToShow: String): Map<String, Array<CharSequence>> {
        val map = mutableMapOf<String, Array<CharSequence>>()

        data.forEach { data ->
            if (data.type == "ARRAY" && data.key == keyToShow) {
                val arrayValues = mutableListOf<CharSequence>();
                data.values?.forEach { value ->
                    arrayValues.add(value.value)
                }

                map.put(data.key, arrayValues.toTypedArray())
            }
        }

        return map
    }

    fun getQuantityStrings(data: List<ExampleKey>, keyToShow: String): Map<String, Map<PluralKeyword, CharSequence>> {
        val map = mutableMapOf<String, Map<PluralKeyword, CharSequence>>()

//        data.forEach { data ->
//            if (data.type == "QUANTITY" && data.key == keyToShow) {
//                val arrayValues = mutableListOf<CharSequence>();
//                data.values?.forEach { value ->
//                    arrayValues.add(value.value)
//                }
//
//                map.put(data.key, arrayValues.toTypedArray())
//            }
//        }

        return map
    }
}
