package nl.romano.kleeren.screens.product

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.romano.kleeren.model.MProduct
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ProductScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val productId = savedStateHandle.getLiveData<String>("product").value.toString()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val productRef = db.collection("products")
    private val usersRef = db.collection("users")
    private lateinit var auth: FirebaseAuth
    var loggedIn: Boolean by Delegates.notNull<Boolean>()
    var foundProduct: MutableState<MProduct?> = mutableStateOf(null)
    private var favoriteProduct: MutableState<MProduct?> = mutableStateOf(null)
    private var cartProduct: MutableState<MProduct?> = mutableStateOf(null)

    init {
        viewModelScope.launch {
            auth = Firebase.auth
            loggedIn = auth.currentUser != null
            getProduct(productId)
        }
    }

    private fun getProduct(id: String) {
        Log.d("User", "Logged in? ${auth.currentUser != null} ")
        // val user = auth.currentUser
        // user.let { Log.d("User", "getProduct: ${user?.displayName}") }
        productRef.whereEqualTo("id", productId.trim()).get()
            .addOnSuccessListener { product ->
                foundProduct = mutableStateOf(product.toObjects(MProduct::class.java).first())
            }
    }

    fun addToFavorites() {
        viewModelScope.launch {
            if (auth.currentUser == null) {
                Log.d("User", "addToFavorites: Not logged in!")
                return@launch
            }

            Log.d("User", "addToFavorites: Logged in!")
            val userId: String = auth.currentUser?.uid.toString()
            lateinit var documentId: String

            productRef.whereEqualTo("id", productId.trim()).get()
                .addOnSuccessListener { product ->
                    favoriteProduct = mutableStateOf(product.toObjects(MProduct::class.java).first())
                }.continueWith {
                    usersRef.whereEqualTo("userId", userId).get()
                        .addOnSuccessListener {
                            documentId = it.documents[0].id
                        }.continueWith {
                            usersRef.document(documentId).update("favorites", FieldValue.arrayUnion(favoriteProduct.value?.toMap()))
                                .addOnSuccessListener {
                                }
                        }
                }
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            if (auth.currentUser == null) {
                Log.d("User", "addToCart: Not logged in!")
                return@launch
            }

            Log.d("User", "addToCart: Logged in!")
            val userId: String = auth.currentUser?.uid.toString()
            lateinit var documentId: String

            productRef.whereEqualTo("id", productId.trim()).get()
                .addOnSuccessListener { product ->
                    cartProduct = mutableStateOf(product.toObjects(MProduct::class.java).first())
                }.continueWith {
                    usersRef.whereEqualTo("userId", userId).get()
                        .addOnSuccessListener {
                            documentId = it.documents[0].id
                        }.continueWith {
                            usersRef.document(documentId).update("shoppingCart", FieldValue.arrayUnion(cartProduct.value?.toMap()))
                                .addOnSuccessListener {
                                }
                        }
                }
        }
    }
}
