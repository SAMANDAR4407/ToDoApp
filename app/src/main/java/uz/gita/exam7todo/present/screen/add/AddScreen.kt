package uz.gita.exam7todo.present.screen.add

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
import uz.gita.exam7todo.present.screen.add.viewmodel.AddContract
import uz.gita.exam7todo.present.screen.add.viewmodel.AddVMImpl
import uz.gita.exam7todo.workmanager.MyWorker
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

/**
 *    Created by Kamolov Samandar on 29.05.2023 at 15:17
 */

class AddScreen : AndroidScreen() {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val viewModel: AddContract.ViewModel = getViewModel<AddVMImpl>()
        AddScreenContent(viewModel::eventDispatcher)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreenContent(
    eventDispatcher: (AddContract.Intent) -> Unit
) {
    val context = LocalContext.current

    val uuid = UUID.randomUUID()

    var title by remember {
        mutableStateOf("")
    }
    var topic by remember {
        mutableStateOf("")
    }
    var date by remember {
        mutableStateOf(LocalDate.now())
    }
    var time by remember {
        mutableStateOf(LocalTime.now())
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
        }
    )

//    DateTimeDialog(
//        state = clockState,
//        selection = DateTimeSelection.Time { newTime ->
//            time = newTime
//        }
//    )

    var submitBtnEnabling by remember {
        mutableStateOf(false)
    }

    val navigator = LocalNavigator.currentOrThrow

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add task",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Medium)
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
                }
            )
        },
    ) { contentPadding ->

        Column(
            modifier = Modifier.padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            MyTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp), "Title", value = title, onValueChange = {
                title = it
            })

            Spacer(modifier = Modifier.height(20.dp))

            MyTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp), "Topic", value = topic, onValueChange = {
                submitBtnEnabling = it.isNotBlank()
                if (it.length <= 10){
                    topic = it
                }
            })

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
                    eventDispatcher(AddContract.Intent.Save(TaskData(0, title, topic, formattedDate, formattedTime, uuid)))

                    val selectedTime = Calendar.getInstance()
                    if (formattedTime.substring(7) == "PM"){
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
                    Log.d("TTT", "AddScreenContent:  $delay")
                },
                enabled = submitBtnEnabling,
            ) {
                Text(text = "Add")
            }
        }
    }

}