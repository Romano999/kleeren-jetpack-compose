package nl.romano.kleeren.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.romano.kleeren.model.UserSearch
import nl.romano.kleeren.util.ObjectMother

@Composable
fun UserSearchEntries(
    userSearches: List<UserSearch> = ObjectMother.genericUserSearchList(),
    onRowClick: (UserSearch) -> Unit,
    onIconClick: (UserSearch) -> Unit,
    limit: Int = 3
) {
    val limitedUserSearches: List<UserSearch>

    if (limit < userSearches.size) {
        limitedUserSearches = userSearches
            .sortedBy { userSearch -> userSearch.timestamp }
            .reversed()
            .subList(0, limit)
    } else {
        limitedUserSearches = userSearches
            .sortedBy { userSearch -> userSearch.timestamp }
            .reversed()
    }

    limitedUserSearches.forEach {
        Log.d("ok", "UserSearchEntries: ${limitedUserSearches.size} + ${it.searchTerm} + ${it.timestamp}")
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(
            limitedUserSearches
        ) { userSearch ->
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .clickable {
                        onRowClick(userSearch)
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = userSearch.searchTerm,
                    fontSize = MaterialTheme.typography.caption.fontSize.times(1.5),
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { onIconClick(userSearch) }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Delete",
                    )
                }
            }
        }
    }
}
