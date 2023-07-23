package com.prior.diciotest.feature_registration.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.prior.diciotest.R
import com.prior.diciotest.core.RoutesMenu
import com.prior.diciotest.core.components.BottomMenuBar
import com.prior.diciotest.core.components.DatePicker
import com.prior.diciotest.core.components.DisposableMessage
import com.prior.diciotest.core.components.MyCircularProgressIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterView(
    navHost: NavHostController
) {
    val viewModel: RegistrationViewModel = hiltViewModel()
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle()
    val message = viewModel.message.collectAsStateWithLifecycle()
    val register = viewModel.registration.collectAsStateWithLifecycle()
    val datos = viewModel.datos.collectAsStateWithLifecycle()
    val uri = viewModel.uri.collectAsStateWithLifecycle()

    if(isLoading.value){
        MyCircularProgressIndicator()
        return
    }

    message.value?.let { msg ->
        DisposableMessage(
            message = msg.asString(),
            onDismiss = viewModel::onDismiss
        )
    }

    Scaffold(
        topBar = { TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.registration_form),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )},
        bottomBar = {
            BottomMenuBar(navController = navHost)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ){
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ){
                item{
                    OutlinedTextField(
                        value = register.value.nombre,
                        onValueChange = viewModel::onNameChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = stringResource(id = R.string.name))
                        }
                    )
                }
                item{
                    OutlinedTextField(
                        value = register.value.apellidoPaterno,
                        onValueChange = viewModel::onPaternalNameChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = stringResource(id = R.string.paternal_lastname))
                        }
                    )
                }
                item{
                    OutlinedTextField(
                        value = register.value.apellidoMaterno,
                        onValueChange = viewModel::onMaternalNameChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = stringResource(id = R.string.maternal_lastname))
                        }
                    )
                }

                item{
                    OutlinedTextField(
                        value = register.value.email,
                        onValueChange = viewModel::onEmailChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = stringResource(id = R.string.email))
                        }
                    )
                }

                item{
                    DatePicker(
                        modifier = Modifier.fillMaxWidth(),
                        value = register.value.fechaNac,
                        label = stringResource(id = R.string.select_your_bithday),
                        onValueChanged = viewModel::onBirthdayChange
                    )
                }

                item{
                    OutlinedTextField(
                        value = datos.value.calle,
                        onValueChange = viewModel::onStreetChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = stringResource(id = R.string.street))
                        }
                    )
                }

                item{
                    OutlinedTextField(
                        value = datos.value.numero,
                        onValueChange = viewModel::onNumberChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = stringResource(id = R.string.number))
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                item{
                    OutlinedTextField(
                        value = datos.value.colonia,
                        onValueChange = viewModel::onNeighborhoodChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = stringResource(id = R.string.neighborhood))
                        }
                    )
                }

                item{
                    OutlinedTextField(
                        value = datos.value.delegacion,
                        onValueChange = viewModel::onCityChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = stringResource(id = R.string.city))
                        }
                    )
                }

                item{
                    OutlinedTextField(
                        value = datos.value.estado,
                        onValueChange = viewModel::onStateChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = stringResource(id = R.string.state))
                        }
                    )
                }

                item{
                    OutlinedTextField(
                        value = datos.value.cp,
                        onValueChange = viewModel::onCPChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = stringResource(id = R.string.cp))
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                item{
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Box(
                            modifier = Modifier
                                .size(200.dp)
                                .border(BorderStroke(2.dp, MaterialTheme.colorScheme.primary))
                                .clickable {
                                    navHost.navigate(RoutesMenu.CameraView.route)
                                }
                        ){
                            if(uri.value != null){
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(uri.value)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = stringResource(id = R.string.photography),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(200.dp),
                                )
                            }else{
                                Text(
                                    text = stringResource(id = R.string.take_picture),
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }

                item{
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Button(onClick = viewModel::onRegister ) {
                            Text(text = stringResource(id = R.string.register))
                        }
                    }
                }
            }
        }
    }
}