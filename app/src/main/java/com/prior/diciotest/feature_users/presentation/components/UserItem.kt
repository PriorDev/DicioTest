package com.prior.diciotest.feature_users.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.prior.diciotest.R
import com.prior.diciotest.domain.models.UserData

@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    user: UserData
) {
    Column(modifier) {
        Card(
            modifier = Modifier.fillMaxSize(),
            elevation = CardDefaults.elevatedCardElevation(8.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(user.datos?.imagen != null){
                    Image(
                        bitmap = user.datos.imagen,
                        contentDescription = user.nombre,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(8.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                }else{
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = user.nombre,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(8.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                }

                Column {
                    Text(
                        text = "${user.nombre} ${user.apellidoMaterno} ${user.apellidoMaterno}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    user.edad?.let {
                        Text(
                            text = "${user.edad} " + stringResource(id = R.string.years),
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.birthday) + " ${user.fechaNac}",
                    )
                    Text(
                        text = stringResource(id = R.string.email) + "",
                    )
                }
            }
            Text(
                text = stringResource(id = R.string.address),
                fontWeight = FontWeight.Bold
            )
            user.datos?.let { datos ->
                Text(
                    text = "${datos.calle} #${datos.numero}, " +
                            stringResource(id = R.string.neighborhood_abbreviation) +
                            " ${datos.colonia}, ${datos.delegacion}, ${datos.estado}," +
                            stringResource(id = R.string.cp) +
                            "${datos.codigoPostal ?: datos.cp ?: ""}"
                )
            }
        }
    }
}