package nl.romano.kleeren.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.romano.kleeren.model.MProduct
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val arrivalsRef: CollectionReference = db.collection("arrivals")
    private val salesRef: CollectionReference = db.collection("sales")
    var salesProducts: MutableList<MProduct> = mutableListOf()
    val arrivalsProducts: MutableList<MProduct> = mutableListOf()

    init {
        getSales()
        getArrivals()
    }

    private fun getSales() {
        viewModelScope.launch {
            salesRef.get().addOnSuccessListener {
                it.documents.forEach {
                    val product: MProduct = MProduct.toProduct(it.data!!)
                    salesProducts.add(product)
                }
            }
        }
    }

    private fun getArrivals() {
        viewModelScope.launch {
            arrivalsRef.get().addOnSuccessListener {
                it.documents.forEach {
                    val product: MProduct = MProduct.toProduct(it.data!!)
                    arrivalsProducts.add(product)
                }
            }
        }
    }
}
