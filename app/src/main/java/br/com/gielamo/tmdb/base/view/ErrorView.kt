package br.com.gielamo.tmdb.base.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    errorMessage: String,
    retryButtonTitle: String,
    retryButtonOnClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = errorMessage,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { retryButtonOnClick.invoke() }
        ) {
            Text(
                text = retryButtonTitle,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
@Preview
fun ErrorViewPreview() {
    ErrorView(errorMessage = "Could not load movie details", retryButtonTitle = "Try again") {
    }
}