package nl.romano.kleeren.screens.favourite

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
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
    var favouriteProducts = mutableStateListOf<MProduct>()
    private lateinit var auth: FirebaseAuth
    var loggedIn: Boolean by Delegates.notNull<Boolean>()
    private val _deletingProduct = MutableLiveData(false)
    val deletingProduct: LiveData<Boolean> = _deletingProduct
    private lateinit var documentId: String
    private lateinit var userId: String

    init {
        viewModelScope.launch {
            auth = Firebase.auth
            userId = auth.currentUser?.uid.toString()
            loggedIn = auth.currentUser != null
            if (loggedIn) {
                getFavourites()
            }
        }
    }

    fun deleteProduct(
        product: MProduct
    ) {
        viewModelScope.launch {
            if (_deletingProduct.value == false) {
                _deletingProduct.value = true
                val documentId: String = documentId
                usersRef.document(documentId).update("favorites", FieldValue.arrayRemove(product))
                    .addOnSuccessListener {
                        Log.d("Shopping", "placeOrder: $it")
                    }
                _deletingProduct.value = false
            }
        }
    }

    fun getFavourites() {
        favouriteProducts.clear()
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
