package uz.gita.exam7todo.present.screen.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.exam7todo.domain.usecase.UseCase
import uz.gita.exam7todo.present.screen.home.navigator.HomeNavigator
import javax.inject.Inject

/**
 *    Created by Kamolov Samandar on 29.05.2023 at 15:47
 */

@HiltViewModel
class HomeVMImpl @Inject constructor(
    private val useCase: UseCase,
    private val navigator: HomeNavigator
) : ViewModel(), HomeContract.ViewModel {

    init {
        useCase.getTasks()
            .onEach { tasks -> uiState.update { it.copy(tasks = tasks) } }
            .launchIn(viewModelScope)
    }

    override val uiState: MutableStateFlow<HomeContract.UIState> = MutableStateFlow(HomeContract.UIState())

    override fun onEventDispatcher(intent: HomeContract.Intent) {
        when (intent) {
            is HomeContract.Intent.OpedEditScreen -> {
                Log.d("TTT", "onEventDispatcher:")
                uiState.update { it.copy(currentData = intent.task)}
                viewModelScope.launch {
                    navigator.navigateToUpdate(task = intent.task)
                }
            }
            HomeContract.Intent.OpenAddScreen -> {
                viewModelScope.launch {
                    navigator.navigateToAdd()
                }
            }
            is HomeContract.Intent.Update -> {
                viewModelScope.launch {
                    useCase.update(intent.task)
                }
            }
            is HomeContract.Intent.Delete -> {
                viewModelScope.launch {
                    useCase.delete(intent.task)
                }
            }
        }
    }
}