package nl.romano.kleeren.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class AccountScreenViewModel @Inject constructor() : ViewModel() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    var loggedIn: Boolean by Delegates.notNull<Boolean>()
    lateinit var user: FirebaseUser
    private lateinit var userId: String
    private lateinit var documentId: String

    init {
        viewModelScope.launch {
            auth = Firebase.auth
            loggedIn = auth.currentUser != null
            if (loggedIn) {
                user = auth.currentUser!!
            }
        }
    }

    fun signOut(afterSignOut: () -> Unit) {
        viewModelScope.launch {
            if (loggedIn) {
                auth.signOut()
            }
            afterSignOut.invoke()
        }
    }
}
