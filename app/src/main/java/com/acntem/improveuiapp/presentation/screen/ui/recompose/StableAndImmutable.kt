package com.acntem.improveuiapp.presentation.screen.ui.recompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.acntem.improveuiapp.presentation.common.SimpleSwitchOptimizationLayout

@Composable
fun StableImmutableScreen(
    onPopBackStack: () -> Unit = {}
) {
    var useStable by remember { mutableStateOf(false) }

    var unstableChecked by remember { mutableStateOf(false) }

    var stableChecked by remember { mutableStateOf(false) }
    var changeStableChecked by remember { mutableStateOf(false) }

    var mutableStableChecked by remember { mutableStateOf(false) }

    SimpleSwitchOptimizationLayout(
        title = "Stable and Immutable",
        isOptimizeMode = useStable,
        onPopBackStack = onPopBackStack,
        sharedContent = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Use Stable",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Switch(
                    checked = useStable,
                    onCheckedChange = { useStable = it }
                )
            }
        },
        optimizeContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Stable not change",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Checkbox(
                            checked = stableChecked,
                            onCheckedChange = { stableChecked = it }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        StableContactList(state = StableContactListState(listOf("Stable")))
                    }

                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .padding(vertical = 16.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val changeStableState =
                            remember { ChangeStableContactListState(listOf("Change Stable")) }
                        Text(
                            "Stable change",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Checkbox(
                            checked = changeStableChecked,
                            onCheckedChange = {
                                changeStableChecked = it
                                changeStableState.names = if (changeStableChecked)
                                    listOf("Change Stable ✅")
                                else
                                    listOf("Change Stable")
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        ChangeStableContactList(state = changeStableState)
                    }
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .padding(vertical = 16.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Immutable",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Checkbox(
                            checked = mutableStableChecked,
                            onCheckedChange = {
                                mutableStableChecked = it
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        ImmutableContactList(state = ImmutableContactListState(listOf("Mutable")))
                    }
                }
            }
        },
        nonOptimizeContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Checkbox(
                    checked = unstableChecked,
                    onCheckedChange = {
                        unstableChecked = it
                    }
                )
                ContactList(state = ContactListState(listOf("Unstable")))
            }
        }
    )
}

data class ContactListState(val names: List<String>)

@Suppress("NonSkippableComposable")
@Composable
fun ContactList(state: ContactListState) {
    Text(
        text = state.names.joinToString(),
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(start = 8.dp)
    )
}

@Stable
data class StableContactListState(val names: List<String>)

@Composable
fun StableContactList(state: StableContactListState) {
    Text(
        text = state.names.toString(),
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(start = 8.dp)
    )
}

@Stable
class ChangeStableContactListState(names: List<String>) {
    var names by mutableStateOf(names)
}

@Composable
fun ChangeStableContactList(state: ChangeStableContactListState) {
    Text(
        text = state.names.toString(),
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(start = 8.dp)
    )
}

@Immutable
data class ImmutableContactListState(val names: List<String>)

@Composable
fun ImmutableContactList(state: ImmutableContactListState) {
    Text(
        text = state.names.toString(),
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(start = 8.dp)
    )
}
