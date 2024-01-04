import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import models.AppData

@Composable
fun AllChatsScreen(appData: AppData, onSignOut: () -> Unit, onRoomSelected: (User) -> Unit, onBack: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var isDialogOpen by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Chats") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isDialogOpen = true },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Chat")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)) {
                // Search Bar
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Search Users") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                // List of chat users
                LazyColumn(modifier = Modifier.fillMaxSize()) {
//                    items(listOf<User>(User("Harsh Dave", 21), User("John Doe", 32)).filter { user ->
//                        searchQuery.isEmpty() || user.name.contains(searchQuery, ignoreCase = true)
//                    }) { user ->
//                        ChatUserItem(user = user, onClick = { onRoomSelected(user) })
//                    }
                }
            }

            // New Chat Dialog
            if (isDialogOpen) {
                Dialog(onDismissRequest = { isDialogOpen = false }) {
                    NewChatModal(onStartChat = { user ->
                        onRoomSelected(user)
                        isDialogOpen = false
                    })
                }
            }
        }
    }
}

@Composable
fun ChatUserItem(user: User, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)
        .clickable(onClick = onClick),
        elevation = 4.dp
    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = user.name, style = MaterialTheme.typography.h6)
//            // Add more details if necessary
//        }
    }
}


@Composable
fun NewChatModal(onStartChat: (User) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var users by remember { mutableStateOf(listOf<User>()) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(searchQuery) {
        isLoading = true
        users = fetchUsersFromAPI(searchQuery) // This function should make the API request
        isLoading = false
    }

    Column {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search Users") },
            modifier = Modifier.fillMaxWidth()
        )

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(users) { user ->
                    UserItem(user = user, onClick = { onStartChat(user) })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserItem(user: User, onClick: () -> Unit) {
    // Simple user item layout
//    ListItem(
//        text = { Text(user.name) },
//        modifier = Modifier.clickable(onClick = onClick)
//    )
}

fun fetchUsersFromAPI(query: String): List<User> {
    // Implement the network request here
    // Return a list of users based on the search query
    return listOf() // Dummy return, replace with actual API call result
}

