package com.example.seefood.screens.catalog.composable

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import com.example.seefood.common.util.URIPathHelper
import com.example.seefood.database.objects.Dish
import java.io.File

@Composable
fun DishCard(dish : Dish){
   Column(
      modifier = Modifier
         .padding(15.dp)
         .width(100.dp),
      horizontalAlignment = Alignment.CenterHorizontally
   ) {
      var imageSize by remember { mutableStateOf(Size.Zero) }
      val helper = URIPathHelper()
      val imageFile = File(helper.getPath(LocalContext.current, Uri.parse(dish.imgLocalPath)).toString())

      AsyncImage(
         modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .onGloballyPositioned { coordinates -> imageSize = coordinates.size.toSize() }
            .height(with(LocalDensity.current) { imageSize.width.toDp() }),
         model = imageFile,
         contentDescription = dish.name,
         contentScale = ContentScale.Crop
      )

      Text(
         modifier = Modifier.padding(top = 10.dp),
         text = dish.name,
         style = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.W500,
            fontSize = 16.sp
         ),
         maxLines = 1,
         overflow = TextOverflow.Ellipsis
      )
   }
}