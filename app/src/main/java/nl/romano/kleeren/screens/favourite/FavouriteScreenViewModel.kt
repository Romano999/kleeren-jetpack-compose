package nl.romano.kleeren.screens.favourite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.romano.kleeren.model.MProduct
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class FavouriteScreenViewModel @Inject constructor() : ViewModel() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val productRef = db.collection("products")
    private val usersRef = db.collection("users")
    var favouriteProducts: MutableList<MProduct> = mutableListOf()
    private lateinit var auth: FirebaseAuth
    var loggedIn: Boolean by Delegates.notNull<Boolean>()

    init {
        viewModelScope.launch {
            auth = Firebase.auth
            loggedIn = auth.currentUser != null
            if (loggedIn) {
                getFavourites()
            }
        }
    }

    private fun getFavourites() {
        lateinit var documentId: String
        val userId: String = auth.currentUser?.uid.toString()

        usersRef.whereEqualTo("userId", userId).get()
            .addOnSuccessListener {
                documentId = it.documents[0].id
            }.continueWith {
                usersRef.document(documentId).get()
                    .addOnSuccessListener { products ->
                        Log.d("Favourite", "getFavourites: ${products.data?.get("favorites")}")
                        val foundFavouriteProducts = products.data?.get("favorites") as ArrayList<HashMap<String, Any>>
                        // Log.d("Favourite", "getFavourites: ${MProduct.toProductList(products.get("favorites") as List<Map<String, Any>>)}")
                        foundFavouriteProducts.forEach { foundFavouriteProduct ->
                            favouriteProducts.add(MProduct.toProduct(foundFavouriteProduct))
                            // Log.d("Favourite", "getFavourites: $foundFavouriteProduct")
                            // favouriteProducts.add(MProduct.toProduct(foundFavouriteProduct.data?.get("favorites")))
                        }
                    }
            }
    }
}
