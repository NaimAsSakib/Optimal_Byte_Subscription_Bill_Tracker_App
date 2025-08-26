package com.cmedhealth.flutter.optimalbytetask.widgets
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp


import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Schedule
import java.text.SimpleDateFormat

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.cmedhealth.flutter.optimalbytetask.model.ConvertedSubscription
import com.cmedhealth.flutter.optimalbytetask.model.Subscription
import com.cmedhealth.flutter.optimalbytetask.utils.BillsUiState
import java.util.Date
import java.util.Locale
import com.cmedhealth.flutter.optimalbytetask.utils.sealed.FilterType

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(text)
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        Text(text)
    }
}

@Composable
fun CustomIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Composable
fun ActionFAB(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}

// ===== INPUT FIELDS =====

@Composable
fun InputField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = if (placeholder.isNotEmpty()) { { Text(placeholder) } } else null,
        leadingIcon = leadingIcon?.let {
            { Icon(it, contentDescription = null) }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = singleLine,
        maxLines = maxLines,
        isError = isError,
        supportingText = if (isError && errorMessage != null) {
            { Text(errorMessage, color = MaterialTheme.colorScheme.error) }
        } else null,
        modifier = modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    value: String,
    label: String,
    options: List<String>,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = value,
            onValueChange = {},
            label = { Text(label) },
            leadingIcon = leadingIcon?.let {
                { Icon(it, contentDescription = null) }
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(option)
                            if (option == value) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = "Selected",
                                    modifier = Modifier.size(16.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    },
                    onClick = {
                        onValueChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ReadOnlyField(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text(label) },
        leadingIcon = leadingIcon?.let {
            { Icon(it, contentDescription = null) }
        },
        trailingIcon = trailingIcon,
        readOnly = true,
        modifier = modifier
            .fillMaxWidth()
            .let { mod ->
                onClick?.let { mod.clickable { it() } } ?: mod
            }
    )
}

// ===== FILTER CHIPS =====

@Composable
fun FilterChipGroup(
    selectedIndex: Int,
    options: List<String>,
    onSelectionChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEachIndexed { index, option ->
            FilterChip(
                onClick = { onSelectionChange(index) },
                label = { Text(option) },
                selected = selectedIndex == index,
                modifier = Modifier.weight(1f),
                leadingIcon = if (selectedIndex == index) {
                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                } else null
            )
        }
    }
}


@Composable
fun FilterSection(
    currentFilter: FilterType,
    onFilterChange: (FilterType) -> Unit
) {
    val filters = listOf("All", "Due Soon", "Overdue")
    val filterTypes = listOf(FilterType.All, FilterType.DueSoon, FilterType.Overdue)
    val currentIndex = filterTypes.indexOf(currentFilter)

    FilterChipGroup(
        selectedIndex = currentIndex,
        options = filters,
        onSelectionChange = { index ->
            onFilterChange(filterTypes[index])
        }
    )
}



@Composable
fun ContentSection(
    uiState: BillsUiState,
    onEdit: (Subscription) -> Unit,
    onDelete: (Subscription) -> Unit,
    onConvertToBDT: () -> Unit
) {
    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        uiState.subscriptions.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        Icons.Default.Receipt,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        "No subscriptions found",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "Tap the + button to add your first subscription",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        uiState.showConvertedView -> {
            ConvertedSubscriptionsView(
                convertedSubscriptions = uiState.convertedSubscriptions,
            )
        }
        else -> {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (uiState.isLoadingConversion) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp))
                            Text("Converting...")
                        }
                    } else {
                        PrimaryButton(
                            text = "Click to see the total",
                            icon = Icons.Default.CurrencyExchange,
                            onClick = onConvertToBDT
                        )
                    }
                }

                uiState.conversionError?.let { error ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = "Error: $error",
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.subscriptions) { subscription ->
                        SubscriptionCard(
                            subscription = subscription,
                            onEdit = { onEdit(subscription) },
                            onDelete = { onDelete(subscription) }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SubscriptionCard(
    subscription: Subscription,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val isOverdue = subscription.nextDueDate < System.currentTimeMillis()
    val isDueSoon = subscription.nextDueDate <= System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000L)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isOverdue -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
                isDueSoon -> MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                else -> MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = subscription.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${subscription.amount} ${subscription.currency} • ${subscription.frequency}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Due: ${formatDate(subscription.nextDueDate)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isOverdue) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row {
                    CustomIconButton(
                        icon = Icons.Default.Edit,
                        contentDescription = "Edit",
                        onClick = onEdit,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    CustomIconButton(
                        icon = Icons.Default.Delete,
                        contentDescription = "Delete",
                        onClick = onDelete,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

private fun formatDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return formatter.format(Date(timestamp))
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditDialog(
    subscription: Subscription?,
    onDismiss: () -> Unit,
    onSave: (Subscription) -> Unit
) {
    val isEditing = subscription != null

    var name by remember { mutableStateOf(subscription?.name ?: "") }
    var amount by remember { mutableStateOf(subscription?.amount?.toString() ?: "") }
    var currency by remember { mutableStateOf(subscription?.currency ?: "USD") }
    var frequency by remember { mutableStateOf(subscription?.frequency ?: "Monthly") }
    var selectedDate by remember { mutableStateOf(subscription?.nextDueDate ?: System.currentTimeMillis()) }

    var nameError by remember { mutableStateOf(false) }
    var amountError by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isEditing) "Edit Subscription" else "Add Subscription") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                InputField(
                    value = name,
                    label = "Name",
                    placeholder = "e.g., Netflix, Spotify",
                    leadingIcon = Icons.Default.Business,
                    onValueChange = { name = it; nameError = false },
                    isError = nameError,
                    errorMessage = "Name is required"
                )

                InputField(
                    value = amount,
                    label = "Amount",
                    placeholder = "0.00",
                    leadingIcon = Icons.Default.MonetizationOn,
                    keyboardType = KeyboardType.Decimal,
                    onValueChange = { amount = it; amountError = false },
                    isError = amountError,
                    errorMessage = "Enter a valid amount greater than 0"
                )

                DropdownField(
                    value = currency,
                    label = "Currency",
                   options = listOf(
                        "BDT", "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN",
                "BAM", "BBD", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL", "BSD", "BTN",
                "BWP", "BYN", "BZD", "CAD", "CDF", "CHF", "CLP", "CNY", "COP", "CRC", "CUP",
                "CVE", "CZK", "DJF", "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FJD",
                "FKP", "FOK", "GBP", "GEL", "GGP", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD",
                "HKD", "HNL", "HRK", "HTG", "HUF", "IDR", "ILS", "IMP", "INR", "IQD", "IRR",
                "ISK", "JEP", "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KID", "KMF", "KRW",
                "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LYD", "MAD", "MDL",
                "MGA", "MKD", "MMK", "MNT", "MOP", "MRU", "MUR", "MVR", "MWK", "MXN", "MYR",
                "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK",
                "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR", "SBD",
                "SCR", "SDG", "SEK", "SGD", "SHP", "SLE", "SLL", "SOS", "SRD", "SSP", "STN",
                "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TVD", "TWD",
                "TZS", "UAH", "UGX", "USD", "UYU", "UZS", "VES", "VND", "VUV", "WST", "XAF",
                "XCD", "XCG", "XDR", "XOF", "XPF", "YER", "ZAR", "ZMW", "ZWL"
                ),
                    leadingIcon = Icons.Default.AttachMoney,
                    onValueChange = { currency = it }
                )

                DropdownField(
                    value = frequency,
                    label = "Frequency",
                    options = listOf("Weekly", "Monthly", "Yearly"),
                    leadingIcon = Icons.Default.Schedule,
                    onValueChange = { frequency = it }
                )

                ReadOnlyField(
                    value = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                        .format(Date(selectedDate)),
                    label = "Next Due Date",
                    leadingIcon = Icons.Default.CalendarToday,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Pick date")
                        }
                    }
                )
            }
        },
        confirmButton = {
            PrimaryButton(
                text = if (isEditing) "Update" else "Save",
                icon = if (isEditing) Icons.Default.Save else Icons.Default.Add,
                onClick = {
                    nameError = name.isBlank()
                    amountError = amount.isBlank() || amount.toDoubleOrNull() == null || amount.toDoubleOrNull()!! <= 0

                    if (!nameError && !amountError) {
                        val newSubscription = if (isEditing) {
                            subscription!!.copy(
                                name = name.trim(),
                                amount = amount.toDouble(),
                                currency = currency,
                                frequency = frequency,
                                nextDueDate = selectedDate
                            )
                        } else {
                            Subscription(
                                name = name.trim(),
                                amount = amount.toDouble(),
                                currency = currency,
                                frequency = frequency,
                                nextDueDate = selectedDate
                            )
                        }
                        onSave(newSubscription)
                    }
                },
                enabled = name.isNotBlank() && amount.isNotBlank()
            )
        },
        dismissButton = {
            SecondaryButton(
                text = "Cancel",
                onClick = onDismiss
            )
        }
    )

    // Date Picker Dialog
    if (showDatePicker) {
        DatePickerModal(
            initialDate = selectedDate,
            onDateSelected = { newDate ->
                selectedDate = newDate
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    initialDate: Long,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { selectedDate ->
                        onDateSelected(selectedDate)
                    }
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = true
        )
    }
}

@Composable
fun ConvertedSubscriptionsView(
    convertedSubscriptions: List<ConvertedSubscription>,
    modifier: Modifier = Modifier
) {
    val totalBDT = convertedSubscriptions.sumOf { it.bdtAmount }

    Column(modifier = modifier) {
        // Header with back button and total
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Converted to BDT",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Total: ${String.format("%.2f", totalBDT)} BDT",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // List of converted subscriptions
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(convertedSubscriptions) { convertedSub ->
                ConvertedSubscriptionCard(convertedSubscription = convertedSub)
            }
        }
    }
}

@Composable
fun ConvertedSubscriptionCard(
    convertedSubscription: ConvertedSubscription
) {
    val subscription = convertedSubscription.subscription
    val isOverdue = subscription.nextDueDate < System.currentTimeMillis()
    val isDueSoon = subscription.nextDueDate <= System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000L)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isOverdue -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
                isDueSoon -> MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                else -> MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = subscription.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Original: ${subscription.amount} ${subscription.currency}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Converted amount
                    Text(
                        text = "BDT: ${String.format("%.2f", convertedSubscription.bdtAmount)}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Exchange rate info
                    if (subscription.currency != "BDT") {
                        Text(
                            text = "Rate: 1 ${subscription.currency} = ${String.format("%.4f", 1.0 / convertedSubscription.exchangeRate)} BDT",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }

                    Text(
                        text = "${subscription.frequency} • Due: ${formatDate(subscription.nextDueDate)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isOverdue) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}