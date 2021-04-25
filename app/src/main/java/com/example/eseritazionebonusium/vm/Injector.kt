package com.example.eseritazionebonusium.vm

object Injector {

    fun provideMyUserViewModel(): UserViewModelFactory{
        return UserViewModelFactory()
    }
}