package nl.romano.kleeren.model

data class MUser(
    val id: String?,
    val userId: String,
    val displayName: String,
    val favorites: List<MProduct>,
    val shoppingCart: List<MProduct>
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "userId" to this.userId,
            "displayName" to this.displayName,
            "favorites" to this.favorites,
            "shoppingCart" to this.shoppingCart
        )
    }
}
