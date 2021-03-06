package nl.romano.kleeren.screens.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import nl.romano.kleeren.R
import nl.romano.kleeren.component.InputField
import nl.romano.kleeren.component.RoundButton
import nl.romano.kleeren.model.UserCredentials
import nl.romano.kleeren.navigation.KleerenScreens
import nl.romano.kleeren.ui.theme.Green

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {
    val context: Context = LocalContext.current

    val emailInput: MutableState<String> = rememberSaveable {
        mutableStateOf("")
    }

    val passwordInput: MutableState<String> = rememberSaveable {
        mutableStateOf("")
    }

    val onLoginButtonCLick: (UserCredentials) -> Unit = { userCredentials ->
        if (emailInput.value.isEmpty() || passwordInput.value.isEmpty()) {
            Toast.makeText(context, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.signInWithEmailAndPassword(
                userCredentials,
                onSuccessAction = {
                    Toast.makeText(context, "Logged in successfully", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                onFailureAction = {
                    Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    val onRegisterButtonCLick: () -> Unit = {
        navController.navigate(KleerenScreens.CreateAccountScreen.route)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.loginscreen_background),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
        Surface(
            color = Color.DarkGray.copy(0.5f),
            modifier = Modifier.fillMaxHeight()
        ) {
            Column {
                LoginHeader()
                Spacer(modifier = Modifier.fillMaxHeight(0.13f))
                LoginForm(
                    emailInput,
                    passwordInput,
                    onLoginButtonCLick,
                    onRegisterButtonCLick
                )
            }
        }
    }
}

// @Preview
@Composable
fun LoginHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Kleeren",
            color = Color.White,
            fontSize = MaterialTheme.typography.h2.fontSize
        )
        Text(
            text = "Clothes for all",
            color = Color.White,
            fontSize = MaterialTheme.typography.caption.fontSize.times(2)
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun LoginForm(
    emailInput: MutableState<String>,
    passwordInput: MutableState<String>,
    onLoginButtonClick: (UserCredentials) -> Unit,
    onRegisterButtonClick: () -> Unit
) {
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
    val passwordFocus: FocusRequester = FocusRequester.Default
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputField(
            modifier = Modifier.fillMaxWidth(0.8f),
            valueState = emailInput,
            labelText = "Email",
            enabled = true,
            isSingleLine = true,
            imageVector = Icons.Default.Person,
            shape = RoundedCornerShape(20.dp),
            keyboardType = KeyboardType.Text,
            onAction = KeyboardActions {
                passwordFocus.requestFocus()
            }
        )
        InputField(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .focusRequester(passwordFocus),
            valueState = passwordInput,
            labelText = "Password",
            enabled = true,
            isSingleLine = true,
            imageVector = Icons.Default.Lock,
            shape = RoundedCornerShape(20.dp),
            keyboardType = KeyboardType.Text,
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.3f))
        RoundButton(
            onClick = {
                onLoginButtonClick(
                    UserCredentials(emailInput.value.trim(), passwordInput.value.trim())
                )
                keyboardController?.hide()
            },
            backgroundColor = Green,
            text = "Log In",
            contentPadding = PaddingValues(15.dp),
        )
        RoundButton(
            onClick = onRegisterButtonClick,
            text = "Register",
            backgroundColor = Color.LightGray,
            contentPadding = PaddingValues(15.dp),
        )
    }
}
