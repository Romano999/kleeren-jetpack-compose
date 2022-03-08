package nl.romano.kleeren.screens.shoppingcart

import android.util.Log
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
    var shoppingCartProducts: MutableList<MProduct> = mutableListOf()
    private lateinit var auth: FirebaseAuth
    var loggedIn: Boolean by Delegates.notNull<Boolean>()
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

    private fun getShoppingCart() {
        val documentId: String = this.documentId

        usersRef.document(documentId).get()
            .addOnSuccessListener { products ->
                val foundShoppingCartProducts = products.data?.get("shoppingCart") as ArrayList<HashMap<String, Any>>
                foundShoppingCartProducts.forEach { foundShoppingCartProduct ->
                    shoppingCartProducts.add(MProduct.toProduct(foundShoppingCartProduct))
                }
            }
    }

    fun placeOrder() {
        viewModelScope.launch {
            val documentId: String = documentId
            usersRef.document(documentId).update("shoppingCart", FieldValue.arrayRemove())
                .addOnSuccessListener {
                    Log.d("Shopping", "placeOrder: $it")
                }
        }
    }
}
