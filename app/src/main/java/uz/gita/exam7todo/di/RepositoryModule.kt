package uz.gita.exam7todo.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.exam7todo.domain.AppRepository
import uz.gita.exam7todo.domain.AppRepositoryImpl
import javax.inject.Singleton

/**
 *    Created by Kamolov Samandar on 26.05.2023 at 16:46
 */

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindRepository(impl: AppRepositoryImpl): AppRepository
}