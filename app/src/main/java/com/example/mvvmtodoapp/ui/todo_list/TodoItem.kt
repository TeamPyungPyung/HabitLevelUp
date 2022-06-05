package com.example.mvvmtodoapp.ui.todo_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.loader.R
import androidx.loader.content.Loader
import com.airbnb.lottie.compose.*
import com.example.mvvmtodoapp.data.Todo
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun TodoItem(
    todo: Todo,
    onEvent: (TodoListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier.padding(8.dp),
        border = BorderStroke(width = 2.dp, color = Color.DarkGray),
        elevation = 8.dp
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = todo.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {
                        onEvent(TodoListEvent.OnDeleteTodoClick(todo))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
                todo.description?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it)
                }
            }
//        Checkbox(
//            checked = todo.isDone,
//            onCheckedChange = { isChecked ->
//            onEvent(TodoListEvent.OnDoneChange(todo, isChecked))
//            }
//        )
            Text(text = todo.doneCount.toString(),
                fontSize = 23.sp
            )

            val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(com.example.mvvmtodoapp.R.raw.arrowup))

            val lottieAnimatableUp = rememberLottieAnimatable()
            LaunchedEffect(composition) {
                lottieAnimatableUp.animate(
                    composition = composition,

                    )
            }
            val scopeArrowUp = rememberCoroutineScope()
            Box(modifier = Modifier
                .height(60.dp)
                .width(60.dp)
                .clickable(onClick = {
                    onEvent(TodoListEvent.OnCountUp(todo, todo.doneCount))
                    scopeArrowUp.launch {
                        lottieAnimatableUp.animate(composition, initialProgress = 0f, speed = 2f)
                    }
                })
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = lottieAnimatableUp.progress
                )
            }


            val lottieAnimatableDown = rememberLottieAnimatable()
            LaunchedEffect(composition) {
                lottieAnimatableDown.animate(
                    composition = composition,

                    )
            }
            val scopeArrowDown = rememberCoroutineScope()
            Box(modifier = Modifier
                .height(60.dp)
                .width(60.dp)
                .clickable(onClick = {
                    onEvent(TodoListEvent.OnCountDown(todo, todo.doneCount))
                    scopeArrowDown.launch {
                        lottieAnimatableDown.animate(composition, initialProgress = 0f, speed = 2f)
                    }
                })
                .rotate(180.0f)
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = lottieAnimatableDown.progress
                )
            }


        }
    }

}

@Composable
fun Loader() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(com.example.mvvmtodoapp.R.raw.arrowup))
    val progress by animateLottieCompositionAsState(composition = composition)
    LottieAnimation(composition = composition, progress = progress, contentScale = ContentScale.Inside)
}



