package com.covenant.bookit.ui.theme.Components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.TextButton
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerAlertDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    onConfirm: (LocalDate) -> Unit,
) {
    val state = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())

    if (visible) {
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            dismissButton = {
                TextButton(
                    text = "Cancel",
                    onClick = {onDismissRequest()},
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            },
            confirmButton = {
                FilledButton(
                    text = "Confirm",
                    onClick = {
                        val date = LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(state.selectedDateMillis!!),
                            TimeZone.getDefault().toZoneId()
                        ).toLocalDate()

                        onConfirm(date)
                        onDismissRequest()
                    },
                    modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
                )
            },
        ) {
            DatePicker(state = state)
        }
    }
}