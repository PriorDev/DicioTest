package com.prior.diciotest.feature_camera.presentation

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.prior.diciotest.R
import com.prior.diciotest.core.components.DisposableMessage
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun CameraView(
    navHost: NavHostController
){
    val viewModel: CameraViewViewModel = hiltViewModel()
    val message = viewModel.message.collectAsStateWithLifecycle()
    val uri = viewModel.uri.collectAsStateWithLifecycle()

    val lensFacing = CameraSelector.LENS_FACING_FRONT
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember {
        ImageCapture
            .Builder()
            .setTargetResolution(Size(300, 300))
            .build()
    }

    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()
    val coroutineScope = rememberCoroutineScope()

    message.value?.let { msg ->
        DisposableMessage(
            message = msg.asString(),
            onDismiss = viewModel::onDismiss
        )
    }

    uri.value?.let {
        PhotoPreview(
            uri = it,
            onDiscardPicture = viewModel::onDiscard,
            onAcceptPhoto = {
                viewModel.onSavePhoto()
                navHost.popBackStack()
            }
        )
        return
    }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ){
        AndroidView(modifier = Modifier.fillMaxSize(),
            factory = {
                coroutineScope.launch {
                    val cameraProvider: ProcessCameraProvider = context.getCameraProvider()
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageCapture
                    )
                    preview.setSurfaceProvider(previewView.surfaceProvider)
                }

                previewView
            }
        )
        IconButton(
            modifier = Modifier.padding(24.dp),
            onClick = {
                takePhoto(
                    imageCapture = imageCapture,
                    onImageCaptured = viewModel::onPhotoTook,
                    onError = { ex ->
                        viewModel.onError(ex)
                    },
                    context = context
                )
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_circle),
                contentDescription = stringResource(id = R.string.take_picture),
                tint = Color.White,
                modifier = Modifier
                    .size(50.dp)
                    .padding(1.dp)
                    .border(1.dp, color = Color.White, CircleShape)
            )
        }
        PhotoGuide(
            modifier = Modifier.fillMaxSize()
        )
    }
}


private fun takePhoto(
    imageCapture: ImageCapture,
    context: Context,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
){
    val outputDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val customDirectory = File(outputDirectory, "Dicio")

    if(!customDirectory.exists()){
        customDirectory.mkdir()
    }

    val photoName = "Img_" +  SimpleDateFormat("yy_MM_dd_HH_mm_ss", Locale.US)
        .format(System.currentTimeMillis()) + ".png"

    val photoFile = File(customDirectory, photoName)

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
    val executor = Executors.newSingleThreadExecutor()

    imageCapture.takePicture(outputOptions,
        executor,
        object: ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                onImageCaptured(savedUri)
            }

        override fun onError(exception: ImageCaptureException) {
            onError(exception)
        }
    })
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider {
    return suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }
}
