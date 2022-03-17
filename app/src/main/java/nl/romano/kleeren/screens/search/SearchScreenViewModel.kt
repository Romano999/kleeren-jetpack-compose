package nl.romano.kleeren.screens.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import nl.romano.kleeren.model.MProduct
import nl.romano.kleeren.model.UserSearch
import nl.romano.kleeren.repository.UserSearchRepository
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class SearchScreenViewModel @Inject constructor(private val repository: UserSearchRepository) : ViewModel() {
    private val _userSearch = MutableStateFlow<List<UserSearch>>(emptyList())
    val userSearchList = _userSearch.asStateFlow()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val productRef = db.collection("products")
    private var _foundProducts = MutableStateFlow<List<MProduct>>(emptyList())
    var foundProducts = _foundProducts.asStateFlow()
    private val usersRef = db.collection("users")
    private lateinit var auth: FirebaseAuth
    var loggedIn: Boolean by Delegates.notNull<Boolean>()
    var foundProduct: MutableState<MProduct?> = mutableStateOf(null)
    private var cartProduct: MutableState<MProduct?> = mutableStateOf(null)

    init {
        viewModelScope.launch {
            auth = Firebase.auth
            loggedIn = auth.currentUser != null
            repository.getUserSearches().distinctUntilChanged().collect { listOfUserSearches ->
                _userSearch.value = listOfUserSearches
            }
        }
    }

    fun searchProducts(userSearch: UserSearch) {
        viewModelScope.launch {
            productRef.whereGreaterThanOrEqualTo("name", userSearch.searchTerm).get().addOnSuccessListener { products ->
                _foundProducts.value = products.toObjects(MProduct::class.java)
                Log.d("Product", "searchProducts: Product found")
                Log.d("Product", "searchProducts: Product list size: $foundProducts")
            }.addOnCanceledListener {
                Log.d("Product", "searchProducts: Product cancelled!")
            }
        }
    }
    fun addUserSearch(userSearch: UserSearch) = viewModelScope.launch { repository.insert(userSearch) }
    fun delete(userSearch: UserSearch) = viewModelScope.launch { repository.delete(userSearch) }

    fun addToFirebaseProductList(
        productId: String,
        updateField: String,
        onSuccessAction: (MProduct) -> Unit,
        onFailureAction: () -> Unit
    ) {
        viewModelScope.launch {
            if (auth.currentUser == null) {
                return@launch
            }

            val userId: String = auth.currentUser?.uid.toString()
            addProductToFirebaseArr(
                productId,
                userId,
                updateField,
                onSuccessAction,
                onFailureAction
            )
        }
    }

    private fun addProductToFirebaseArr(
        productId: String,
        userId: String,
        updateField: String,
        onSuccessAction: (MProduct) -> Unit,
        onFailureAction: () -> Unit
    ) {
        lateinit var documentId: String
        productRef.whereEqualTo("id", productId.trim()).get()
            .addOnSuccessListener { product ->
                cartProduct = mutableStateOf(product.toObjects(MProduct::class.java).first())
            }.continueWith {
                usersRef.whereEqualTo("userId", userId).get()
                    .addOnSuccessListener {
                        documentId = it.documents[0].id
                    }.continueWith {
                        usersRef.document(documentId).update(updateField, FieldValue.arrayUnion(cartProduct.value?.toMap()))
                            .addOnSuccessListener {
                                if (cartProduct.value != null) {
                                    onSuccessAction.invoke(cartProduct.value!!)
                                }
                                Log.d("Product", "addProductToFirebaseArr: Done")
                            }.addOnFailureListener {
                                onFailureAction.invoke()
                                Log.d("Product", "addProductToFirebaseArr: Failed")
                            }
                    }
            }
    }
}
