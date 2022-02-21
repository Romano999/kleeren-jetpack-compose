package nl.romano.kleeren.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.romano.kleeren.model.MProduct
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    // val sales: MutableState<MProduct> = mutableStateOf(null)
    private val arrivalsRef = db.collection("arrivals")
    private val salesRef = db.collection("sales")
    var salesProducts: MutableList<MProduct> = mutableListOf()
    val arrivalsProducts: MutableList<MProduct> = mutableListOf()

    init {
        getSales()
        getArrivals()
    }

    fun getSales() {
        viewModelScope.launch {
            salesRef.get().addOnSuccessListener {
                it.documents.forEach {
                    val product = MProduct.toProduct(it.data!!)
                    salesProducts.add(product)
                }
            }
        }
    }

    fun getArrivals() {
        viewModelScope.launch {
            arrivalsRef.get().addOnSuccessListener {
                it.documents.forEach {
                    val product = MProduct.toProduct(it.data!!)
                    arrivalsProducts.add(product)
                }
            }
        }
    }
}
