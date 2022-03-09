package nl.romano.kleeren.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
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

@HiltViewModel
class SearchScreenViewModel @Inject constructor(private val repository: UserSearchRepository) : ViewModel() {
    private val _userSearch = MutableStateFlow<List<UserSearch>>(emptyList())
    val userSearchList = _userSearch.asStateFlow()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val productRef = db.collection("products")
    private var _foundProducts = MutableStateFlow<List<MProduct>>(emptyList())
    var foundProducts = _foundProducts.asStateFlow()

    init {
        viewModelScope.launch {
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
}
