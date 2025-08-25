package com.cmedhealth.flutter.optimalbytetask.ui.theme

import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.material.icons.filled.Add

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cmedhealth.flutter.optimalbytetask.widgets.ActionFAB
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign

import com.cmedhealth.flutter.optimalbytetask.database.BillsDatabase
import com.cmedhealth.flutter.optimalbytetask.model.Subscription
import com.cmedhealth.flutter.optimalbytetask.repository.BillsRepository
import com.cmedhealth.flutter.optimalbytetask.utils.sealed.FilterType
import com.cmedhealth.flutter.optimalbytetask.utils.BillsUiState
import com.cmedhealth.flutter.optimalbytetask.viewModel.BillsViewModel
import com.cmedhealth.flutter.optimalbytetask.widgets.AddEditDialog
import com.cmedhealth.flutter.optimalbytetask.widgets.ContentSection
import com.cmedhealth.flutter.optimalbytetask.widgets.FilterSection
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillsTrackerApp() {
    val context = LocalContext.current
    val database = remember { BillsDatabase.getDatabase(context) }
    val repository = remember { BillsRepository(database.subscriptionDao()) }
    val viewModel: BillsViewModel = viewModel { BillsViewModel(repository) }

    MaterialTheme {
        BillsScreen(viewModel = viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillsScreen(viewModel: BillsViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var editingSubscription by remember { mutableStateOf<Subscription?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Bills Tracker",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            ActionFAB(
                icon = Icons.Default.Add,
                contentDescription = "Add subscription",
                onClick = { showAddDialog = true }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            FilterSection(
                currentFilter = uiState.filterType,
                onFilterChange = { viewModel.setFilter(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ContentSection(
                uiState = uiState,
                onEdit = { editingSubscription = it },
                onDelete = { viewModel.deleteSubscription(it) }
            )
        }

        // Dialogs
        if (showAddDialog) {
            AddEditDialog(
                subscription = null,
                onDismiss = { showAddDialog = false },
                onSave = {
                    viewModel.addSubscription(it)
                    showAddDialog = false
                }
            )
        }

        editingSubscription?.let { subscription ->
            AddEditDialog(
                subscription = subscription,
                onDismiss = { editingSubscription = null },
                onSave = {
                    viewModel.updateSubscription(it)
                    editingSubscription = null
                }
            )
        }
    }
}