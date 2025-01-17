package com.six.sense.presentation.screen.materialComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.six.sense.presentation.screen.materialComponents.materialBottomNav.MaterialBottomNavigation
import com.six.sense.presentation.screen.materialComponents.materialList.MaterialList
import com.six.sense.presentation.screen.materialComponents.primaryTab.PrimaryTabItems
import com.six.sense.presentation.screen.materialComponents.textFields.MaterialTextFields
import com.six.sense.ui.theme.SixSenseAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentsScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier=modifier, contentWindowInsets = WindowInsets(0,0,0,0)
    ) { innerPadding ->
        var selectedTabIndex by remember { mutableIntStateOf(0) }
        val pagerState = rememberPagerState { MaterialComponents.componentsList.size }
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            PrimaryScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
            ) {
                PrimaryTabItems(
                    selectedTabIndex = selectedTabIndex,
                    onClick = { selectedTabIndex = it })
            }
            HorizontalPager(state = pagerState,userScrollEnabled = false) { pagerItems ->
                when (selectedTabIndex) {
                    0 -> MaterialTextFields()
                    1 -> MaterialList()
                    2 -> MaterialBottomNavigation()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefPrev() {
    SixSenseAndroidTheme {
        ComponentsScreen()
    }
}