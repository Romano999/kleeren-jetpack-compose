package nl.romano.kleeren.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nl.romano.kleeren.R
import nl.romano.kleeren.component.InputField
import nl.romano.kleeren.component.RoundButton
import nl.romano.kleeren.ui.theme.Green

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(navController: NavController) {
    val usernameInput = remember {
        mutableStateOf("")
    }

    val passwordInput = remember {
        mutableStateOf("")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.loginscreen_background),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
        Surface(
            color = Color.Transparent,
            modifier = Modifier.fillMaxHeight()
        ) {
            Column {
                LoginHeader()
                Spacer(modifier = Modifier.fillMaxHeight(0.18f))
                LoginForm(usernameInput, passwordInput)
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
            .height(200.dp),
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
    usernameInput: MutableState<String>,
    passwordInput: MutableState<String>
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputField(
            modifier = Modifier.fillMaxWidth(0.8f),
            valueState = usernameInput,
            labelText = "Username",
            enabled = true,
            isSingleLine = true,
            imageVector = Icons.Default.Person,
            shape = RoundedCornerShape(20.dp),
            keyboardType = KeyboardType.Text
        )
        InputField(
            modifier = Modifier.fillMaxWidth(0.8f),
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
            onClick = { /*TODO*/ },
            backgroundColor = Green,
            text = "Log In",
            contentPadding = PaddingValues(15.dp)
        )
        RoundButton(
            onClick = { /*TODO*/ },
            text = "Register",
            backgroundColor = Color.LightGray,
            contentPadding = PaddingValues(15.dp)
        )
    }
}
