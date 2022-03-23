package nl.romano.kleeren.screens.shoppingcart

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
class ShoppingCartScreenViewModel @Inject constructor() : ViewModel() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val usersRef = db.collection("users")
    var shoppingCartProducts = mutableStateListOf<MProduct>()
    private lateinit var auth: FirebaseAuth
    var loggedIn: Boolean by Delegates.notNull<Boolean>()
    private val _deletingProduct = MutableLiveData(false)
    val deletingProduct: LiveData<Boolean> = _deletingProduct
    private lateinit var userId: String
    private lateinit var documentId: String

    init {
        viewModelScope.launch {
            auth = Firebase.auth
            loggedIn = auth.currentUser != null
            if (loggedIn) {
                userId = auth.currentUser?.uid.toString()
                usersRef.whereEqualTo("userId", userId).get()
                    .addOnSuccessListener {
                        documentId = it.documents[0].id
                    }.continueWith {
                        if (loggedIn) {
                            getShoppingCart()
                        }
                    }
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
                usersRef.document(documentId).update("shoppingCart", FieldValue.arrayRemove(product))
                    .addOnSuccessListener {
                        Log.d("Shopping", "placeOrder: $it")
                    }
                _deletingProduct.value = false
            }
        }
    }

    fun placeOrder() {
        viewModelScope.launch {
            val documentId: String = documentId
            usersRef.document(documentId).update("shoppingCart", emptyList<MProduct>())
                .addOnSuccessListener {
                    Log.d("Shopping", "placeOrder: $it")
                }
        }
    }

    fun getShoppingCart() {
        val documentId: String = this.documentId
        shoppingCartProducts.clear()
        usersRef.document(documentId).get()
            .addOnSuccessListener { products ->
                val foundShoppingCartProducts = products.data?.get("shoppingCart") as ArrayList<HashMap<String, Any>>
                foundShoppingCartProducts.forEach { foundShoppingCartProduct ->
                    shoppingCartProducts.add(MProduct.toProduct(foundShoppingCartProduct))
                }
            }
    }
}
