package nl.romano.kleeren.screens.createaccount

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.romano.kleeren.model.MUser
import nl.romano.kleeren.model.UserCredentials
import javax.inject.Inject

@HiltViewModel
class CreateAccountScreenViewModel @Inject constructor() : ViewModel() {
    // val loadingState = MutableStateFlow()
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    private val loading: LiveData<Boolean> = _loading

    fun createUserWithEmailAndPassword(
        userCredentials: UserCredentials,
        route: () -> Unit
    ) {
        if (_loading.value == false) {
            _loading.value = true
            val email: String = userCredentials.email.trim()
            val password: String = userCredentials.password.trim()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val displayName = task.result?.user?.email?.split('@')?.get(0)
                        createUser(displayName)
                        route()
                    } else {
                        Log.d("Create", "createUserWithEmailAndPassword: ${task.result}")
                    }
                    _loading.value = false
                }
        }
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = MUser(
            id = null,
            userId = userId.toString(),
            displayName = displayName.toString(),
            favorites = emptyList(),
            shoppingCart = emptyList()
        ).toMap()

        FirebaseFirestore
            .getInstance()
            .collection("users")
            .add(user)
    }
}
