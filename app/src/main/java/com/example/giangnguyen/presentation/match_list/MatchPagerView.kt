package com.example.giangnguyen.presentation.match_list

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.giangnguyen.domain.model.MatchDetailsModel
import com.example.giangnguyen.domain.model.MatchModel
import kotlinx.coroutines.launch

data class MatchPagerPage(
    @StringRes val titleResId: Int,
    @DrawableRes val drawableResId: Int,
    val matchList: List<MatchDetailsModel>

)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MatchPagerView(
    pagerState: PagerState,
    pages: List<MatchPagerPage>,
    navigateMatchDetails: (MatchModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        val coroutineScope = rememberCoroutineScope()
        // Tab Row
        TabRow(
            selectedTabIndex = pagerState.currentPage
        ) {
            pages.forEachIndexed { index, page ->
                val title = stringResource(id = page.titleResId)
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(text = title) },
                    unselectedContentColor = MaterialTheme.colorScheme.secondary
                )
            }
        }

        // Pages
        HorizontalPager(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            pageCount = pages.size,
            state = pagerState,
            userScrollEnabled = true,
            verticalAlignment = Alignment.Top
        ) { index ->
            MatchListView(pages[index].matchList, navigateMatchDetails)
        }
    }
}