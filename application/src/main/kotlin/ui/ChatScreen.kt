import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun ChatScreen(userName: String) {
    var currentMessage by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<Message>() }

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        MessagesList(messages, Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = currentMessage,
                onValueChange = { currentMessage = it },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    if (currentMessage.isNotBlank()) {
                        messages.add(Message(userName, currentMessage))
                        currentMessage = ""
                    }
                })
            )
            Button(
                onClick = {
                    if (currentMessage.isNotBlank()) {
                        messages.add(Message(userName, currentMessage))
                        currentMessage = ""
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Send")
            }
        }
    }
}

@Composable
fun MessagesList(messages: List<Message>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        for (message in messages) {
            Text(text = "[${message.userName}]: ${message.message}", modifier = Modifier.padding(4.dp))
        }
    }
}
