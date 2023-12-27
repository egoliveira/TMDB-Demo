package br.com.gielamo.tmdb.base.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import br.com.gielamo.tmdb.R
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@Composable
fun MovieProgressIndicator(modifier: Modifier = Modifier) {
    val drawable = ContextCompat.getDrawable(LocalContext.current, R.drawable.loading_animation)

    Box(modifier = modifier) {
        Image(
            painter = rememberDrawablePainter(drawable),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
    }
}