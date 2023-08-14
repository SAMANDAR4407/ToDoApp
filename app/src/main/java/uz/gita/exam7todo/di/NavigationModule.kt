package uz.gita.exam7todo.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.exam7todo.navigation.AppNavigator
import uz.gita.exam7todo.navigation.NavigationDispatcher
import uz.gita.exam7todo.navigation.NavigationHandler

/**
 *    Created by Kamolov Samandar on 03.06.2023 at 8:46
 */
@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {
    @Binds
    fun bindAppNavigator(dispatcher: NavigationDispatcher): AppNavigator

    @Binds
    fun bindNavigationHandler(dispatcher: NavigationDispatcher): NavigationHandler
}