package nl.romano.kleeren.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.romano.kleeren.model.MProduct
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val arrivalsRef: CollectionReference = db.collection("arrivals")
    private val salesRef: CollectionReference = db.collection("sales")
    private var _arrivalsProducts = MutableStateFlow<List<MProduct>>(emptyList())
    var arrivalsProducts = _arrivalsProducts.asStateFlow()
    private var _salesProducts = MutableStateFlow<List<MProduct>>(emptyList())
    var salesProducts = _salesProducts.asStateFlow()

    init {
        getSales()
        getArrivals()
    }

    private fun getSales() {
        viewModelScope.launch {
            salesRef.get().addOnSuccessListener { products ->
                _salesProducts.value = products.toObjects(MProduct::class.java)
            }
        }
    }

    private fun getArrivals() {
        viewModelScope.launch {
            arrivalsRef.get().addOnSuccessListener { products ->
                _arrivalsProducts.value = products.toObjects(MProduct::class.java)
            }
        }
    }
}
