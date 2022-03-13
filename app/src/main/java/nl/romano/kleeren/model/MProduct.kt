package nl.romano.kleeren.model

import java.math.RoundingMode
import java.util.UUID

class MProduct(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val price: Double,
    val productUrl: String?
) {
    constructor() : this("", "", "", 0.0, "")

    companion object {
        fun toProduct(map: Map<String, Any>): MProduct {
            return MProduct(
                map["id"].toString(),
                map["name"].toString(),
                map["description"].toString(),
                map["price"] as Double,
                map["productUrl"] as String?
            )
        }

        fun toProductList(map: List<Map<String, Any>>): List<MProduct> {
            val products: ArrayList<MProduct> = ArrayList()

            map.forEach { item -> products.add(this.toProduct(item)) }

            return products
        }
    }

    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "id" to id,
            "name" to name,
            "description" to description,
            "price" to price.toBigDecimal().setScale(2, RoundingMode.UNNECESSARY).toDouble(),
            "productUrl" to productUrl.toString()
        )
    }
}
