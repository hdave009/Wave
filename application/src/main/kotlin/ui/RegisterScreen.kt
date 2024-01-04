package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import apiClient
import kotlinx.coroutines.coroutineScope
import java.time.LocalDate

@Composable
fun RegisterScreen(onRegistrationSuccessful: () -> Unit, onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }

    val performRegistration = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Username") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = dateOfBirth,
            onValueChange = { dateOfBirth = it },
            label = { Text("Date of Birth (YYYY-MM-DD)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isBlank() || userName.isBlank() || password.isBlank() || confirmPassword.isBlank() ||
                    firstName.isBlank() || lastName.isBlank() || dateOfBirth.isBlank()) {
                    println("Fields must not be blank")
                } else if (password != confirmPassword) {
                    println("Passwords do not match")
                }

                try {
                    val dateObject = LocalDate.parse(dateOfBirth)
                } catch (e: Exception) {
                    println("Invalid Date format")
                }

                performRegistration.value = true
                      },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        // LaunchedEffect to handle registration
        LaunchedEffect(performRegistration.value) {
            if (performRegistration.value) {
                val response = apiClient.registerRequest(email, userName, password, firstName, lastName, dateOfBirth)
                if (response.statusCode == 200) {
                    onRegistrationSuccessful()
                } else {
                    println(response.message)
                }
                // Reset the state to avoid repeated calls
                performRegistration.value = false
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Back to Login",
            modifier = Modifier.clickable(onClick = onBack),
            color = MaterialTheme.colors.secondary
        )
    }
}
