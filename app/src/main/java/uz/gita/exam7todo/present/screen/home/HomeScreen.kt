package uz.gita.exam7todo.present.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.exam7todo.R
import uz.gita.exam7todo.data.model.TaskData
import uz.gita.exam7todo.present.component.Item
import uz.gita.exam7todo.present.screen.home.viewmodel.HomeContract
import uz.gita.exam7todo.present.screen.home.viewmodel.HomeVMImpl
import uz.gita.exam7todo.ui.theme.md_theme_dark_inversePrimary

/**
 *    Created by Kamolov Samandar on 29.05.2023 at 15:08
 */

class HomeScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel: HomeContract.ViewModel = getViewModel<HomeVMImpl>()
        val uiState = viewModel.uiState.collectAsState().value
        HomeScreenContent(uiState, viewModel::onEventDispatcher)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    uiState: HomeContract.UIState,
    onEventDispatcher: (HomeContract.Intent) -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tasks",
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Medium
                    )
                }
            )
        },
        floatingActionButton = {
            Card(
                modifier = Modifier.size(60.dp),
                colors = CardDefaults.cardColors(md_theme_dark_inversePrimary),
                shape = RoundedCornerShape(100.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .clickable {
                            onEventDispatcher(HomeContract.Intent.OpenAddScreen)
                        }
                        .padding(20.dp)
                        .size(20.dp),
                    imageVector = Icons.Filled.Edit,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }
    ) { padding ->

        if (uiState.tasks.isEmpty()){
            Column(Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.placeholder),
                    modifier = Modifier.size(100.dp),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Add your first todo...", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground)
            }
        }
        else {
            LazyColumn(
                modifier = Modifier.padding(padding)
            ) {
                items(uiState.tasks) { data ->

                    Item(
                        data.title, data.topic, data.time, data.date, data.done,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                            .combinedClickable(
                                onClick={ onEventDispatcher(HomeContract.Intent.OpedEditScreen(data)) },
                                onLongClick = { }
                            ),
                        onClick = { checked ->
                            onEventDispatcher(
                                HomeContract.Intent.Update(
                                    TaskData(
                                        data.id,
                                        data.title,
                                        data.topic,
                                        data.date,
                                        data.time,
                                        data.uuid,
                                        checked
                                    )
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}