package uz.gita.exam7todo.present.screen.edit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.gita.exam7todo.domain.usecase.UseCase
import uz.gita.exam7todo.present.screen.edit.navigator.UpdateNavigator
import javax.inject.Inject

/**
 *    Created by Kamolov Samandar on 03.06.2023 at 6:17
 */

@HiltViewModel
class UpdateVMImpl @Inject constructor(
    private val useCase: UseCase,
    private val navigator: UpdateNavigator
): ViewModel(), UpdateContract.ViewModel {
    override val uiState = MutableStateFlow(UpdateContract.UiState())

    override fun eventDispatcher(intent: UpdateContract.Intent) {
        when(intent){
            is UpdateContract.Intent.Pop -> {
                viewModelScope.launch {
                    navigator.pop()
                }
            }
            is UpdateContract.Intent.Save -> {
                viewModelScope.launch {
                    useCase.update(intent.task)
                    navigator.pop()
                }
            }
            is UpdateContract.Intent.Delete -> {
                useCase.delete(intent.task)
            }
        }
    }

}