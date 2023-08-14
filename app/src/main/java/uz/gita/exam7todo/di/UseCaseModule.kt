package uz.gita.exam7todo.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.exam7todo.domain.usecase.UseCase
import uz.gita.exam7todo.domain.usecase.UseCaseImpl

/**
 *    Created by Kamolov Samandar on 29.05.2023 at 16:27
 */
@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindUseCase(impl: UseCaseImpl): UseCase
}