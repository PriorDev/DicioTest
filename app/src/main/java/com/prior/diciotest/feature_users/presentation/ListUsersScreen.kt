package com.prior.diciotest.feature_users.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.prior.diciotest.core.components.BottomMenuBar
import com.prior.diciotest.core.components.DisposableMessage
import com.prior.diciotest.core.components.MyCircularProgressIndicator
import com.prior.diciotest.feature_users.presentation.components.UserItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListUsersScreen(
    navController: NavHostController
) {
    val viewModel: ListUsersViewModel = hiltViewModel()
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle()
    val message = viewModel.message.collectAsStateWithLifecycle()
    val userList = viewModel.userList

    Scaffold(
        bottomBar = {
            BottomMenuBar(navController = navController)
        }
    ) { innerPadding ->
        if(isLoading.value){
            MyCircularProgressIndicator()
            return@Scaffold
        }

        message.value?.let { msg ->
            DisposableMessage(
                message = msg.asString(),
                onDismiss = viewModel::onDismiss
            )
        }

        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ){
            items(userList){ user ->
                UserItem(user = user,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }


}