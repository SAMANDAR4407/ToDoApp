package uz.gita.exam7todo.present.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.exam7todo.ui.theme.Exam7ToDoTheme
import java.time.LocalDateTime

/**
 *    Created by Kamolov Samandar on 26.05.2023 at 17:23
 */


@Composable
fun Item(
    title: String,
    topic: String,
    time: String,
    date: String,
    isDone: Boolean,
    modifier: Modifier,
    onClick: ((Boolean) -> Unit)? = null
) {
    var checked by remember {
        mutableStateOf(isDone)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 5.dp),
        shape = RoundedCornerShape(14.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(95.dp)
        ) {

            Checkbox(
                checked = checked,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 6.dp),
                onCheckedChange = {
                    checked = !checked
                    onClick!!.invoke(checked)
                }
            )

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = modifier
                    .wrapContentWidth()
                    .padding(end = 10.dp)
            ) {
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Card(
                        Modifier.wrapContentWidth(),
                        colors = CardDefaults.cardColors(Color.Black),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp),
                            text = topic,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    if (date.substring(8).toInt() != LocalDateTime.now().dayOfMonth){
                        Icon(
                            imageVector = Icons.Outlined.CalendarMonth, modifier = Modifier
                                .padding(end = 1.dp)
                                .size(15.dp),
                            contentDescription = ""
                        )
                        Text(
                            text = date,
                            modifier
                                .padding(vertical = 10.dp),
                            fontSize = 13.sp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.Timer, modifier = Modifier
                                .padding(end = 1.dp)
                                .size(15.dp),
                            contentDescription = ""
                        )
                        Text(
                            text = time,
                            modifier
                                .padding(vertical = 10.dp),
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun Item() {
    Exam7ToDoTheme() {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Item("Demo taskdfhbkhjbdgdbseilfubliubsfsfliib", "ddkahgvc", "12:30", "2023-07-09", true, Modifier)
        }
    }
}