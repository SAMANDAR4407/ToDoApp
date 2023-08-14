package uz.gita.exam7todo.present.screen.add.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.gita.exam7todo.domain.usecase.UseCase
import uz.gita.exam7todo.present.screen.add.navigator.AddNavigator
import javax.inject.Inject

/**
 *    Created by Kamolov Samandar on 29.05.2023 at 15:15
 */

@HiltViewModel
class AddVMImpl @Inject constructor(
    private val useCase: UseCase,
    private val navigator: AddNavigator
): AddContract.ViewModel, ViewModel() {

    override val uiState = MutableStateFlow(AddContract.UiState())

    override fun eventDispatcher(intent: AddContract.Intent) {
        when(intent){
            is AddContract.Intent.Pop -> {
                viewModelScope.launch {
                    navigator.pop()
                }
            }
            is AddContract.Intent.Save -> {
                viewModelScope.launch {
                    useCase.addTask(intent.task)
                    navigator.pop()
                }
            }
        }
    }
}