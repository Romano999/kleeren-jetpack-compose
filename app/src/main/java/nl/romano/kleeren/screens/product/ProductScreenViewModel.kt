package nl.romano.kleeren.screens.product

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.romano.kleeren.model.MProduct
import javax.inject.Inject

@HiltViewModel
class ProductScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val productId = savedStateHandle.getLiveData<String>("product")
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val productRef = db.collection("products")
    private val usersRef = db.collection("users")
    private val auth: FirebaseAuth = Firebase.auth
    var foundProduct: MutableState<MProduct?> = mutableStateOf(null)

    init {
        getProduct(productId.value.toString())
    }

    private fun getProduct(id: String) {
        viewModelScope.launch {
            productRef.whereEqualTo("id", productId.value?.trim()).get()
                .addOnSuccessListener { product ->
                    foundProduct = mutableStateOf(product.toObjects(MProduct::class.java).first())
                }
        }
    }

    private fun addToFavorites() {
        viewModelScope.launch {
            if (auth.currentUser != null) {
                return@launch
            }
            val userId: String = auth.currentUser?.uid.toString()
            // usersRef.document().
            // .whereEqualTo("userId", userId.trim())
        }
    }
}
