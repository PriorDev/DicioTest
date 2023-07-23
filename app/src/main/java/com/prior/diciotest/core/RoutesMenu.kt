package com.prior.diciotest.core

sealed class RoutesMenu(val route: String){
    object ListUserView: RoutesMenu("ListUsersView")
    object RegisterView: RoutesMenu("RegisterView")
    object CameraView: RoutesMenu("CameraView")
}
