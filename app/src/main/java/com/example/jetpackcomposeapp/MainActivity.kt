package com.example.jetpackcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeapp.ui.theme.JetpackComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MyScreenContent()
            }
        }
    }
}

/**
 * [Greeting] is a Text [Composable] function passed in [NameList]
 * @param name is the [name] of the [Text].
 */
@Composable
fun Greeting(name: String) {
    var isSelected by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(if (isSelected) Color.Red else Color.Transparent)

    Text(
        text = "$name!",
        // Modifier parameters tell a element how to lay out, display, or behave within its parent layout.
        modifier = Modifier
            .padding(24.dp)
            .background(color = backgroundColor)
            .clickable(onClick = { isSelected = !isSelected }),
        style  = MaterialTheme.typography.body1
    )
}

/**
 * [MyApp] is a [Composable] function that invokes the [MyScreenContent].
 * @param content is a generic container that takes as a parameter a Composable function which returns [Unit].
 * Composable functions don't return UI components, they emit them. That's why they must return [Unit].
 */
@Composable
fun MyApp(content: @Composable () -> Unit) {
    JetpackComposeAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = Color.Yellow) {
            content()
        }
    }
}

/**
 * [MyScreenContent] is a [Composable] function that displays the main content of the app.
 */
@Composable
fun MyScreenContent(names: List<String> = List(1000) { "Hello Android #$it" }) {
    // Recomposing is used for observing the state of live changes while the app is running
    val counterState = remember { mutableStateOf(0) }
    // This ColumnScope is like a LinearLayout in XML
    Column(modifier = Modifier.fillMaxHeight()) {
        NameList(names, Modifier.weight(1f))
        Counter(
            count = counterState.value,
            updateCount = { newCount ->
                counterState.value = newCount
            }
        )
    }
}

/**
 * [Greeting] is a Text [Composable] function
 * that increments the number of clicks used in [MyScreenContent].
 */
@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    Button(
        onClick = { updateCount(count + 1) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (count > 5) Color.Green else Color.White
        )
    ) {
        Text(text = "I've been clicked $count times")
    }
}

/**
 * [LazyColumn] Composable function that stores a List of String
 * @param names List of names for individual items in [LazyColumn].
 */
@Composable
fun NameList(names: List<String>, modifier: Modifier = Modifier) {
    // LazyColumn is just like RecyclerView in XML
    LazyColumn(modifier = modifier) {
        items(items = names) {
            Greeting(name = it)
            Divider(color = Color.Black)
        }
    }
}

/**
 * [DefaultPreview] is a [Preview] [Composable] function.
 */
@Preview(name = "MyScreenPreview")
@Composable
fun DefaultPreview() {
    JetpackComposeAppTheme {
        MyApp {
            MyScreenContent()
        }
    }
}