package nl.romano.kleeren.screens.createaccount

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import nl.romano.kleeren.model.UserCredentials

class CreateAccountScreenViewModel : ViewModel() {
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
        val user = mutableMapOf<String, Any>()
        user["user_id"] = userId.toString()
        user["display_name"] = displayName.toString()

        FirebaseFirestore
            .getInstance()
            .collection("users")
            .add(user)
    }
}
