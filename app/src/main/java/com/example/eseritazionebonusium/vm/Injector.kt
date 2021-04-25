package com.example.eseritazionebonusium.vm

object Injector {

    fun provideMyUserViewModelFactory(): UserViewModelFactory{
        return UserViewModelFactory()
    }
}