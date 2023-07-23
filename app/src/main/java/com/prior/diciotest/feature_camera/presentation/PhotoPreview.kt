package com.prior.diciotest.feature_camera.presentation

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.prior.diciotest.R

@Composable
fun PhotoPreview(
    uri: Uri,
    onDiscardPicture: () -> Unit,
    onAcceptPhoto: () -> Unit
){
    Box(
        Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(uri)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(id = R.string.photography),
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.BottomStart),
            onClick = onAcceptPhoto
        ) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = stringResource(id = R.string.accept),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(50.dp)
                    .padding(1.dp)
                    .border(1.dp, color = Color.White, CircleShape)
            )
        }

        IconButton(
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.BottomEnd),
            onClick = onDiscardPicture
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(id = R.string.discard),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(50.dp)
                    .padding(1.dp)
                    .border(1.dp, color = Color.White, CircleShape)
            )
        }
    }
}