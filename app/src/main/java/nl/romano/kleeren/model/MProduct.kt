package nl.romano.kleeren.model

import java.util.UUID

class MProduct(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val price: Long,
    val productImageURL: String?
) {
    companion object {
        fun toProduct(map: Map<String, Any>): MProduct {
            return MProduct(
                map["id"].toString(),
                map["name"].toString(),
                map["description"].toString(),
                map["price"] as Long,
                map["product_url"] as String?
            )
        }
    }

    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "id" to id,
            "name" to name,
            "description" to description,
            "price" to price,
            "product_url" to productImageURL.toString()
        )
    }
}
