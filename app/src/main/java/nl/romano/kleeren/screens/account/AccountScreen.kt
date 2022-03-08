package nl.romano.kleeren.screens.account

import android.widget.Toast
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseUser
import nl.romano.kleeren.component.BottomNavigationBar
import nl.romano.kleeren.component.RoundButton
import nl.romano.kleeren.navigation.KleerenScreens
import nl.romano.kleeren.ui.theme.Green

@Composable
fun AccountScreen(
    navController: NavController,
    viewModel: AccountScreenViewModel = hiltViewModel()
) {
    val loggedIn = viewModel.loggedIn
    val user: FirebaseUser? = if (loggedIn) viewModel.user else null
    val context = LocalContext.current

    val onSignOutButtonClick: () -> Unit = {
        viewModel.signOut {
            Toast.makeText(context, "Signed out", Toast.LENGTH_SHORT).show()
            navController.navigate(KleerenScreens.HomeScreen.route)
        }
    }

    val onSignInButtonClick: () -> Unit = {
        navController.navigate(KleerenScreens.LoginScreen.route)
    }

    Scaffold(bottomBar = {
        BottomAppBar(
            elevation = 5.dp
        ) {
            BottomNavigationBar(navController = navController)
        }
    }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your Account",
                fontSize = MaterialTheme.typography.caption.fontSize.times(3)
            )
            if (loggedIn) {
                LoggedInScreen(
                    user?.displayName,
                    onSignOutButtonClick
                )
            } else {
                NotLoggedInScreen(
                    onSignInButtonClick
                )
            }
        }
    }
}

@Composable
fun LoggedInScreen(
    username: String?,
    onSignOutButtonClick: () -> Unit
) {
    Column {
        Text(text = "Welcome $username")
        SignOutButton {
            onSignOutButtonClick.invoke()
        }
    }
}

@Composable
fun NotLoggedInScreen(onSignInButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.92f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "You are currently not logged in.",
                fontSize = MaterialTheme.typography.caption.fontSize.times(1.5)
            )
            Text(
                text = "Log in to see your account.",
                fontStyle = FontStyle.Italic
            )
            RoundButton(
                onClick = onSignInButtonClick,
                text = "Login in",
                backgroundColor = Green,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(top = 20.dp)
            )
        }
    }
}

@Composable
fun SignOutButton(onSignOutButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        RoundButton(
            onClick = onSignOutButtonClick,
            text = "Sign out",
            backgroundColor = Green,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(top = 20.dp)
        )
    }
}
