package com.dev_musashi.onetouch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev_musashi.onetouch.uiLayer.composable.TableButton
import com.dev_musashi.onetouch.uiLayer.home.composable.TableRow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContents()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ContentPreview(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Box(
            modifier = Modifier
                .height(40.dp)
                .padding(10.dp, 10.dp, 0.dp, 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.원터치보드판),
                color = Color.Black,
                fontSize = 10.sp,
            )
        }


        Divider(color = Color.LightGray)


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.촬영히스토리),
                fontSize = 10.sp
            )
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Divider(
                    Modifier
                        .width(1.dp)
                        .fillMaxHeight(),
                    color = Color.Black
                )
                Icon(
                    modifier = Modifier.clickable { },
                    painter = painterResource(id = R.drawable.ic_photo),
                    contentDescription = null
                )
            }
        }

        Divider(color = Color.LightGray)


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
                .background(Color.Black)
        ) {

        }

        Divider(color = Color.LightGray)


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.서식입력),
                fontSize = 10.sp
            )
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    modifier = Modifier.clickable {  },
                    painter = painterResource(id = R.drawable.ic_save),
                    contentDescription = null
                )
                Divider(
                    Modifier
                        .width(1.dp)
                        .fillMaxHeight(), color = Color.Black
                )
                Icon(
                    modifier = Modifier.clickable { },
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null
                )
            }
        }

        Divider(color = Color.LightGray)

        LazyRow(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            contentPadding = PaddingValues(6.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            item {
                TableButton(
                    text = "",
                    modifier = Modifier
                        .width(80.dp)
                        .height(28.dp),
                    isSelectedState = true,
                    onClick = { },
                    onLongClick = { }
                )
            }
        }

        Divider(color = Color.LightGray)

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp),
        ) {

        }
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(170.dp),
//            contentAlignment = Alignment.Center,
//        ) {
//            Table(
//                state = state,
//                viewModel = homeViewModel,
//                tableScrollState = scrollState,
//                focusRequester = focusRequester
//            )
//        }

        Divider(color = Color.LightGray)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier.width(200.dp),
                onClick = { }
            ) {
                Text(text = "촬영", fontSize = 10.sp)
            }
        }

        Divider(color = Color.LightGray)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
        ) {

        }

        Divider(color = Color.LightGray)

    }

}



