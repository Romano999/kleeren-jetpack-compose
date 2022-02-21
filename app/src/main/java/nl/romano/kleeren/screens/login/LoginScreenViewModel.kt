package nl.romano.kleeren.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.romano.kleeren.model.UserCredentials
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor() : ViewModel() {
    // val loadingState = MutableStateFlow()
    private val auth: FirebaseAuth = Firebase.auth

    // private val _loading = MutableLiveData(false)
    // private val loading: LiveData<Boolean> = _loading

    fun signInWithEmailAndPassword(userCredentials: UserCredentials, navigate: () -> Unit) =
        viewModelScope.launch {
            try {
                val email: String = userCredentials.email.trim()
                val password: String = userCredentials.password.trim()

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Login", "task: ${task.result}")
                            navigate()
                        } else {
                            Log.d("Login", "task failed: ${task.result}")
                        }
                    }
                    .addOnFailureListener { task ->
                        Log.d("Login", "task failed: ${task.message}")
                    }
                    .addOnCanceledListener {
                        Log.d("Login", "signInWithEmailAndPassword: Cancelled")
                    }
            } catch (exception: Exception) {
                Log.d("Login", "signInWithEmailAndPassword: $exception")
            }
        }
}
