package com.cmedhealth.flutter.optimalbytetask.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmedhealth.flutter.optimalbytetask.model.ConvertedSubscription
import com.cmedhealth.flutter.optimalbytetask.model.Subscription
import com.cmedhealth.flutter.optimalbytetask.repository.BillsRepository
import com.cmedhealth.flutter.optimalbytetask.utils.BillsUiState
import com.cmedhealth.flutter.optimalbytetask.utils.sealed.FilterType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BillsViewModel(private val repository: BillsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(BillsUiState())
    val uiState: StateFlow<BillsUiState> = _uiState.asStateFlow()

    init {
        loadSubscriptions()
    }

    fun setFilter(filterType: FilterType) {
        _uiState.value = _uiState.value.copy(filterType = filterType)
        loadSubscriptions()
    }

    private fun loadSubscriptions() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val subscriptionsFlow = when (_uiState.value.filterType) {
                is FilterType.All -> repository.getAllSubscriptions()
                is FilterType.DueSoon -> repository.getDueSoonSubscriptions()
                is FilterType.Overdue -> repository.getOverdueSubscriptions()
            }

            subscriptionsFlow.collect { subscriptions ->
                _uiState.value = _uiState.value.copy(
                    subscriptions = subscriptions,
                    isLoading = false
                )
            }
        }
    }

    fun refreshSubscriptions() {
        loadSubscriptions()
    }

    fun addSubscription(subscription: Subscription) {
        viewModelScope.launch {
            repository.insertSubscription(subscription)
            refreshSubscriptions()
        }
    }

    fun updateSubscription(subscription: Subscription) {
        viewModelScope.launch {
            repository.updateSubscription(subscription)
            refreshSubscriptions()
        }
    }

    fun deleteSubscription(subscription: Subscription) {
        viewModelScope.launch {
            repository.deleteSubscription(subscription)
            refreshSubscriptions()
        }
    }

    fun convertToBDTAndShow() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingConversion = true, conversionError = null)

            val result = repository.convertToBDT(_uiState.value.subscriptions)

            if (result.isSuccess) {
                val convertedList = result.getOrNull() ?: emptyList<ConvertedSubscription>()
                _uiState.value = _uiState.value.copy(
                    convertedSubscriptions = convertedList,
                    isLoadingConversion = false,
                    showConvertedView = true,
                    conversionError = null
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoadingConversion = false,
                    conversionError = result.exceptionOrNull()?.message ?: "Failed to convert currencies"
                )
            }
        }
    }

    fun hideConvertedView() {
        _uiState.value = _uiState.value.copy(showConvertedView = false)
    }
}
