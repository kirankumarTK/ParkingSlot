package com.example.parkingslot.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
internal fun DefaultEditTextField(
    label: String,
    text: String,
    onTextChange: (String) -> Unit,
    keyboard: KeyboardType,
    isError: Boolean = false,
    errorMsg: String = "",
    imeAction: ImeAction = ImeAction.Next,
    trailIcon: ImageVector = Icons.Filled.Image
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = text,
        onValueChange = { onTextChange(it) },
        maxLines = 1,
        textStyle = MaterialTheme.typography.labelMedium,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 25.dp, max = 80.dp)
            .wrapContentHeight(),
        label = {
            Text(
                style = MaterialTheme.typography.labelSmall, text = if (isError) errorMsg else label
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboard, autoCorrectEnabled = false, imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }),
        isError = isError,
        trailingIcon = {
            Icon(trailIcon, contentDescription = "", modifier = Modifier.size(18.dp))
        })
}


/*VisualTransformation is composable method used mask text*/
@Composable
internal fun PasswordEditTextField(
    label: String,
    text: String,
    onTextChange: (String) -> Unit,
    keyboard: KeyboardType,
    isError: Boolean = false,
    errorMsg: String = "",
    imeAction: ImeAction = ImeAction.Next
) {
    val showPassword = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = text,
        onValueChange = { onTextChange(it) },
        maxLines = 1,
        textStyle = MaterialTheme.typography.labelMedium,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 30.dp, max = 80.dp)
            .wrapContentHeight(),
        label = {
            Text(
                style = MaterialTheme.typography.labelSmall, text = if (isError) errorMsg else label
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboard, autoCorrectEnabled = false, imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }),
        isError = isError,
        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon =
                if (showPassword.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            IconButton(onClick = { showPassword.value = !showPassword.value }) {
                Icon(icon, contentDescription = "Password", modifier = Modifier.size(18.dp))
            }
        })
}

