package com.example.parkingslot.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.parkingslot.R
import com.example.parkingslot.utils.capitalizeFirstLetter

@Composable
internal fun AppToolBar(
    title: String = "",
    showTitle: Boolean = false,
    showBackNavigation: Boolean = false,
    navController: NavHostController,
    currentRoute: String?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colorScheme.primaryContainer),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        if (showBackNavigation) {
            Icon(
                contentDescription = stringResource(R.string.app_name),
                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(18.dp)
                    .clickable {
                        navController.navigate("login") {
                            popUpTo(currentRoute.toString()) {
                                inclusive = true
                            }
                        }
                    },
            )
        }
        if (showTitle) {
            Text(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .fillMaxWidth(),
                text = title.take(50).capitalizeFirstLetter(),
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Start,
            )
        }

    }
}

@Preview
@Composable
internal fun PreviewToolbar() {
    AppToolBar(
        "Registration",
        true,
        true,
        navController = rememberNavController(),
        currentRoute = ""
    )
}