import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreen(onLogin: () -> Unit, onRegister: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF7B1FA2), // Deep purple
                        Color(0xFFE1BEE7)  // Light purple
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Wave 👋",
                fontSize = 32.sp, // Increased font size
                color = Color.White,
                fontWeight = FontWeight.Bold, // Bolder font
                style = MaterialTheme.typography.h4 // More appealing typography
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Style the buttons with a fixed width and rounded corners
            Button(
                onClick = onLogin,
                modifier = Modifier
                    .width(200.dp) // Fixed width
                    .height(50.dp), // Fixed height
                shape = RoundedCornerShape(50) // Fully rounded corners
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onRegister,
                modifier = Modifier
                    .width(200.dp) // Fixed width
                    .height(50.dp), // Fixed height
                shape = RoundedCornerShape(50) // Fully rounded corners
            ) {
                Text("Register")
            }
        }
    }
}
