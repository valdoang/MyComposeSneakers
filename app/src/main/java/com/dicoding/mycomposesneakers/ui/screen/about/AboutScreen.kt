package com.dicoding.mycomposesneakers.ui.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.dicoding.mycomposesneakers.R
import com.dicoding.mycomposesneakers.ui.theme.MyComposeSneakersTheme

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.about_photo),
                contentDescription = "About Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(250.dp)
                    .clip(CircleShape)
            )
            Text(
                text = stringResource(R.string.about_name),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = stringResource(R.string.about_email),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }

}

@Composable
@Preview(showBackground = true)
fun AboutScreenPreview(
    modifier: Modifier = Modifier,
) {
    MyComposeSneakersTheme {
        AboutScreen()
    }
}