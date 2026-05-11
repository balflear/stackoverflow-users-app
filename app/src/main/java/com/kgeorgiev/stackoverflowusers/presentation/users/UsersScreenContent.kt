package com.kgeorgiev.stackoverflowusers.presentation.users

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kgeorgiev.stackoverflowusers.R
import com.kgeorgiev.stackoverflowusers.domain.error.AppError
import com.kgeorgiev.stackoverflowusers.domain.model.User
import com.kgeorgiev.stackoverflowusers.presentation.base.getErrorText

@Composable
fun UsersScreenContent(
    onAction: (UsersActions) -> Unit,
    screenState: UsersScreenState,
    paddingValues: PaddingValues
) {

    val errorText = getErrorText(screenState.error)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (screenState.isLoading) {
            CircularProgressIndicator()
        } else if (errorText != null) {
            Text(errorText)
        } else {
            Text(
                stringResource(R.string.users_screen_top_users),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(Modifier.height(10.dp))

            LazyColumn() {
                items(items = screenState.usersList, key = { it.accountId }) { item ->
                    UserMainCard(
                        user = item,
                        isProcessingUser = item.accountId in screenState.processingUserIds,
                        onAction = onAction
                    )

                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun UserMainCard(user: User, isProcessingUser: Boolean, onAction: (UsersActions) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = CardDefaults.elevatedShape
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            AsyncImage(
                model = user.profileImageUrl,
                contentDescription = "Profile image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape),
                error = painterResource(android.R.drawable.ic_menu_myplaces),
                fallback = painterResource(android.R.drawable.ic_menu_myplaces),
                placeholder = painterResource(android.R.drawable.ic_menu_myplaces)
            )

            Text(user.displayName, fontWeight = FontWeight.Bold)
            Text("Reputation: " + user.reputation.toString())

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.users_screen_following))
                Checkbox(
                    checked = user.isFollowed,
                    enabled = !isProcessingUser,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            onAction(UsersActions.FollowUser(user.accountId))
                        } else {
                            onAction(UsersActions.UnFollowUser(user.accountId))
                        }
                    })
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun UsersScreenContentPreview() {
    MaterialTheme() {
        val user1 = User(
            accountId = 101L,
            displayName = "John Cena",
            reputation = 10001,
            profileImageUrl = ""
        )

        val user2 = User(
            accountId = 105L,
            displayName = "The Rock",
            reputation = 788888,
            profileImageUrl = ""
        )
        val sreenState = UsersScreenState(usersList = listOf(user1, user2))

        UsersScreenContent(
            onAction = {},
            screenState = sreenState,
            paddingValues = PaddingValues(10.dp)
        )
    }
}