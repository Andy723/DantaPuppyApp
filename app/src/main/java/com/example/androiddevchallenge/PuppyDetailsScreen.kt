/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Puppy
import com.example.androiddevchallenge.data.PuppysRepository
import com.example.androiddevchallenge.ui.theme.MyTheme

@Composable
fun puppyDetailsScreen(puppy: Puppy) {
    val pageState = remember {
        PagerState().apply {
            minPage = 0
            maxPage = (puppy.images.size - 1).coerceAtLeast(0)
        }
    }


    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "Published in: ${post.publication?.name}",
//                        style = MaterialTheme.typography.subtitle2,
//                        color = LocalContentColor.current
//                    )
//                },
//                navigationIcon = {
//                    IconButton(onClick = onBack) {
//                        Icon(
//                            imageVector = Icons.Filled.ArrowBack,
//                            contentDescription = stringResource(R.string.back)
//                        )
//                    }
//                }
//            )
//        },
        content = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            PuppyContent(puppy, modifier)
        }
    )
//    Column {
//        Spacer(modifier = Modifier.height(10.dp))
//
//        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
//            puppy.images.forEachIndexed { index, _ ->
//                CarouselDot(
//                    selected = index == pageState.currentPage,
//                    Icons.Filled.Lens
//                )
//            }
//        }
//
//
//        if (adoptSuccess) {
//            var scaleUp by remember { mutableStateOf(false) }
//            val scale by animateFloatAsState(
//                if (scaleUp) 1.1f else 0.9f,
//                finishedListener = { scaleUp = !scaleUp },
//                animationSpec = spring(stiffness = Spring.StiffnessLow)
//            )
////            Image(
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .scale(scale = scale),
////                bitmap = ImageBitmap.imageResource(id = R.drawable.congurtulations),
////                contentDescription = "congratulations"
////            )
//        } else {
//            Button(
//                onClick = { showDialogState = true },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                Text(text = "I love ${puppy.name}")
//            }
//        }
//    }
}

@Composable
private fun ProfileItem(key: String, value: String) {
    Text(
        key, modifier = Modifier.width(70.dp),
        fontWeight = FontWeight.Bold
    )
    Text(value, fontWeight = FontWeight.Light)
}

@Composable
fun CarouselDot(selected: Boolean, icon: ImageVector) {
    Icon(
        imageVector = icon,
        modifier = Modifier
            .padding(4.dp)
            .size(12.dp),
        contentDescription = null,
        tint = if (selected) Color.Gray else Color.LightGray
    )
}


@Preview
@Composable
fun previewDetails() {
    val puppy = PuppysRepository.Edison
    MyTheme {
        puppyDetailsScreen(puppy = puppy)
    }
}
