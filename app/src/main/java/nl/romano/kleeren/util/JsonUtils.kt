package nl.romano.kleeren.util

import android.content.Context

class JsonUtils {
    companion object {
        fun getJsonFromAssets(
            ctx: Context,
            fileName: String
        ): String? {
            return try {
                val input = ctx.assets.open(fileName)
                val size: Int = input.available()
                val buffer = ByteArray(size)
                input.read(buffer)
                input.close()
                String(buffer)
            } catch (exc: Exception) {
                exc.printStackTrace()
                null
            }
        }
    }
}
