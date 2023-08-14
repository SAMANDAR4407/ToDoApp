package uz.gita.exam7todo.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.exam7todo.present.screen.add.navigator.AddNavigator
import uz.gita.exam7todo.present.screen.add.navigator.AddNavigatorImpl
import uz.gita.exam7todo.present.screen.edit.navigator.UpdateNavigator
import uz.gita.exam7todo.present.screen.edit.navigator.UpdateNavigatorImpl
import uz.gita.exam7todo.present.screen.home.navigator.HomeNavigator
import uz.gita.exam7todo.present.screen.home.navigator.HomeNavigatorImpl

/**
 *    Created by Kamolov Samandar on 03.06.2023 at 8:26
 */

@Module
@InstallIn(SingletonComponent::class)
interface NavigatorModule {

    @Binds
    fun bindHomeNavigator(impl: HomeNavigatorImpl): HomeNavigator

    @Binds
    fun bindUpdateNavigator(impl: UpdateNavigatorImpl): UpdateNavigator

    @Binds
    fun bindAddNavigator(impl: AddNavigatorImpl): AddNavigator
}