package nl.romano.kleeren.util

import com.google.firebase.firestore.FirebaseFirestore
import nl.romano.kleeren.model.MProduct
import nl.romano.kleeren.model.MUser

/*
    This class is made to check if the firebase connecting to this project
    contains the necessary collections for this project to function correctly.
 */
class InitializeFirestore {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val usersRef = db.collection("users")
    private val salesRef = db.collection("sales")
    private val productRef = db.collection("products")
    private val arrivalsRef = db.collection("arrivals")
    private val products: List<MProduct> = WebshopProducts.fillWebshopWithProducts()

    fun initializeFirestoreCollectionsIfEmpty() {
        initializeUsersCollectionIfEmpty()
        initializeSalesCollectionIfEmpty()
        initializeProductsCollectionIfEmpty()
        initializeArrivalsCollectionIfEmpty()
    }

    private fun initializeUsersCollectionIfEmpty() {
        val user = MUser(userId = "", displayName = "", favorites = emptyList(), shoppingCart = emptyList())

        usersRef.get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    db.collection("users").document(user.id).set(user.toMap())
                }
            }
    }

    private fun initializeSalesCollectionIfEmpty() {
        salesRef.get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    for (i in 0..4) {
                        db.collection("sales").document().set(products[i].toMap())
                    }
                }
            }
    }

    private fun initializeProductsCollectionIfEmpty() {
        productRef.get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    products.forEach { product ->
                        db.collection("products").document().set(product.toMap())
                    }
                }
            }
    }

    private fun initializeArrivalsCollectionIfEmpty() {
        arrivalsRef.get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    for (i in 6..11) {
                        db.collection("arrivals").document().set(products[i].toMap())
                    }
                }
            }
    }
}
