package uz.gita.exam7todo.present.screen.edit

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.*
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import uz.gita.exam7todo.data.model.TaskData
import uz.gita.exam7todo.present.component.MyTextField
import uz.gita.exam7todo.present.screen.edit.viewmodel.UpdateContract
import uz.gita.exam7todo.present.screen.edit.viewmodel.UpdateVMImpl
import uz.gita.exam7todo.workmanager.MyWorker
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

/**
 *    Created by Kamolov Samandar on 29.05.2023 at 15:17
 */

class UpdateScreen(val task: TaskData) : AndroidScreen() {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val viewModel = getViewModel<UpdateVMImpl>()
        UpdateScreenContent(viewModel::eventDispatcher, task)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenContent(
    eventDispatcher: (UpdateContract.Intent) -> Unit,
    task: TaskData
) {
    val context = LocalContext.current

    val uuid = UUID.randomUUID()

    var title by remember {
        mutableStateOf(task.title)
    }
    var topic by remember {
        mutableStateOf(task.topic)
    }
    var date by remember {
        mutableStateOf(LocalDate.of(task.date.substring(0, 4).toInt(), task.date.substring(5, 7).toInt(), task.date.substring(8).toInt()))
    }
    var time by remember {
        if (task.time.substring(7) == "PM") {
            mutableStateOf(LocalTime.of(task.time.substring(0, 2).toInt() + 12, task.time.substring(3, 5).toInt()))
        } else {
            mutableStateOf(LocalTime.of(task.time.substring(0, 2).toInt(), task.time.substring(3, 5).toInt()))
        }
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("yyyy-MM-dd")
                .format(date)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm a")
                .format(time)
        }
    }

    val calendarState = rememberUseCaseState()
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(monthSelection = true, yearSelection = true),
        selection = CalendarSelection.Date { newDate ->
            date = newDate
        })

    val clockState = rememberUseCaseState()
    ClockDialog(
        state = clockState,
        config = ClockConfig(boundary = LocalTime.of(0, 0)..LocalTime.of(23, 59)),
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            time = LocalTime.of(hours, minutes)
        })

    val navigator = LocalNavigator.currentOrThrow

    val isDialogShown = remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Reschedule task",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.pop() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            isDialogShown.value = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = ""
                        )
                    }
                }
            )
        },
    ) { contentPadding ->

        if (isDialogShown.value) {
            AlertDialog(
                onDismissRequest = { isDialogShown.value = false },
                text = {
                    Text(text = "Are you sure?", fontWeight = FontWeight.Medium, fontSize = 22.sp)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            isDialogShown.value = false
                            WorkManager.getInstance(context).cancelWorkById(task.uuid)
                            eventDispatcher(UpdateContract.Intent.Delete(task))
                            navigator.pop()
                        }
                    ) {
                        Text(text = "Yes", color = MaterialTheme.colorScheme.background)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { isDialogShown.value = false }
                    ) {
                        Text(text = "No", color = MaterialTheme.colorScheme.background)
                    }
                }
            )
        }

        Column(
            modifier = Modifier.padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            MyTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                "Title",
                value = title,
                onValueChange = { title = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            MyTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                "Topic",
                value = topic,
                onValueChange = {
                    if (it.length <= 10){
                        topic = it
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            MyTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                "Date",
                value = formattedDate,
                onValueChange = {},
                isClickable = false,
                trailingIcon = {
                    IconButton(onClick = {
                        calendarState.show()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = ""
                        )
                    }
                },
            )

            Spacer(modifier = Modifier.height(20.dp))

            MyTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                "Time",
                value = formattedTime,
                onValueChange = {},
                isClickable = false,
                trailingIcon = {
                    IconButton(onClick = {
                        clockState.show()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = ""
                        )
                    }
                },
            )

            Spacer(modifier = Modifier.height(10.dp))

            ElevatedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 26.dp),
                onClick = {
                    eventDispatcher(UpdateContract.Intent.Save(TaskData(task.id, title, topic, formattedDate, formattedTime, uuid)))

                    val selectedTime = Calendar.getInstance()
                    if (formattedTime.substring(7) == "PM") {
                        time.plusHours(12)
                    }
                    selectedTime.time = Date.from(time.atDate(date).atZone(ZoneId.systemDefault()).toInstant())

                    val delay = selectedTime.timeInMillis - System.currentTimeMillis()
                    if (delay >= -10000) {
                        val request = OneTimeWorkRequestBuilder<MyWorker>()
                            .setId(uuid)
                            .setInputData(workDataOf("title" to title, "topic" to topic, "notificationID" to selectedTime.timeInMillis))
                            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                            .build()

                        WorkManager.getInstance(context).enqueueUniqueWork("name", ExistingWorkPolicy.REPLACE, request)
                    }
                }
            ) {
                Text(text = "Reschedule")
            }
        }

    }
}