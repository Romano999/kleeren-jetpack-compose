package nl.romano.kleeren.screens.createaccount

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import nl.romano.kleeren.R
import nl.romano.kleeren.component.InputField
import nl.romano.kleeren.component.RoundButton
import nl.romano.kleeren.model.UserCredentials
import nl.romano.kleeren.ui.theme.Green

@ExperimentalComposeUiApi
@Preview
@Composable
fun CreateAccountScreen(
    navController: NavController = rememberNavController(),
    viewModel: CreateAccountScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val emailInput = rememberSaveable {
        mutableStateOf("")
    }

    val passwordInput = rememberSaveable {
        mutableStateOf("")
    }

    val onSubmit: (UserCredentials) -> Unit = { userCredentials ->
        Toast.makeText(context, "Account created!", Toast.LENGTH_SHORT).show()
        viewModel.createUserWithEmailAndPassword(userCredentials) {
            navController.popBackStack()
        }
    }

    /*
    val validForm = remember {
        emailInput.value.trim().isNotEmpty() && passwordInput.value.trim().isNotEmpty()
    }
    */

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.createaccountscreen_background),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
        Surface(
            color = Color.DarkGray.copy(0.5f),
            modifier = Modifier.fillMaxHeight()
        ) {
            Column {
                CreateAccountHeader()
                Spacer(modifier = Modifier.fillMaxHeight(0.13f))
                CreateAccountForm(
                    emailInput,
                    passwordInput,
                    navController,
                    onSubmit
                )
            }
        }
    }
}

@Composable
fun CreateAccountHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Creating an account",
            color = Color.White,
            fontSize = MaterialTheme.typography.caption.fontSize.times(3)
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun CreateAccountForm(
    emailInput: MutableState<String>,
    passwordInput: MutableState<String>,
    navController: NavController,
    onSubmit: (UserCredentials) -> Unit = { }
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val passwordFocus = FocusRequester.Default
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
                onSubmit(
                    UserCredentials(
                        emailInput.value.trim(),
                        passwordInput.value.trim()
                    )
                )
                keyboardController?.hide()
            },
            backgroundColor = Green,
            text = "Create",
            contentPadding = PaddingValues(15.dp),
        )
        RoundButton(
            onClick = { navController.popBackStack() },
            text = "Back",
            backgroundColor = Color.LightGray,
            contentPadding = PaddingValues(15.dp),
        )
    }
}
