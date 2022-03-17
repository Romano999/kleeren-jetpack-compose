package nl.romano.kleeren

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import nl.romano.kleeren.navigation.KleerenNavigation
import nl.romano.kleeren.ui.theme.KleerenTheme
import nl.romano.kleeren.util.InitializeFirestore

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KleerenTheme {
                MyApp {
                    KleerenNavigation()
                    val initializeFirestore = InitializeFirestore()
                    initializeFirestore.initializeFirestoreCollectionsIfEmpty()
                }
            }
        }
    }
}

// Container function
@Composable
fun MyApp(content: @Composable () -> Unit) {
    KleerenTheme {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KleerenTheme {
    }
}
