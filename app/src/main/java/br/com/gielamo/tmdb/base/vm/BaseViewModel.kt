package br.com.gielamo.tmdb.base.vm

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<S, M> : ViewModel(), DefaultLifecycleObserver {
    private val _state = MutableStateFlow(getDefaultState())

    private val _message = MutableSharedFlow<M>()

    val state: StateFlow<S> = _state

    val message: SharedFlow<M> = _message

    override fun onCreate(owner: LifecycleOwner) {
        onInitialize()
    }

    protected abstract fun getDefaultState(): S

    protected open fun onInitialize() {}

    protected fun postState(state: S) {
        _state.value = state
    }

    protected fun postMessage(message: M) {
        Timber.d("Posting message ${message!!::class.simpleName}...")

        viewModelScope.launch {
            _message.emit(message)
        }
    }
}