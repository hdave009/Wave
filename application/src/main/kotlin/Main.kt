import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import client.ApiClient
import models.AppData
import ui.RegisterScreen

enum class Screen {
      Welcome, Login, Register, AllChatsScreen, ChatScreen
}

val ENVIRONMENT = "local"
val apiClient = ApiClient()


@Composable
@Preview
fun App() {
    var currentScreen by remember { mutableStateOf(Screen.Welcome) }
    val appData by remember { mutableStateOf(AppData(null, null)) }

    Column {
        // Compose UI based on the current screen
        when (currentScreen) {
            Screen.Welcome -> WelcomeScreen(
                onLogin = {currentScreen = Screen.Login},
                onRegister = {currentScreen = Screen.Register}
            )
            Screen.Login -> LoginScreen(
                onLoginSuccessful = { currentScreen = Screen.AllChatsScreen },
                onNoAccount = { currentScreen = Screen.Register },
                appData)

            Screen.Register -> RegisterScreen(
                onRegistrationSuccessful =  { currentScreen = Screen.Login },
                onBack = {currentScreen = Screen.Login})

            Screen.ChatScreen -> ChatScreen(
                appData,
                onSignOut = { currentScreen = Screen.Login }
            )

            Screen.AllChatsScreen -> AllChatsScreen(
                appData,
                onBack = {currentScreen = Screen.Login},
                onSignOut = { currentScreen = Screen.Login },
                onRoomSelected = { currentScreen = Screen.ChatScreen }
            )
        }
    }
}

fun main() = application {
    Window(
        title = "Wave \uD83D\uDC4B",
        onCloseRequest = ::exitApplication) {
        App()
    }
}
