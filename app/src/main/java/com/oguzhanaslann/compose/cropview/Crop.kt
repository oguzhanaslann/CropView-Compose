package com.oguzhanaslann.compose.cropview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.oguzhanaslann.compose.cropview.cropShape.CropShape
import com.oguzhanaslann.compose.cropview.cropShape.grid.GridCrop
import com.oguzhanaslann.compose.cropview.cropShape.grid.rememberGridCropState

@Composable
fun Crop(
    modifier: Modifier = Modifier,
    cropShape: CropShape = GridCrop(rememberGridCropState()),
    drawGrid: Boolean = true,
    content: @Composable () -> Unit,
) {
    val cropState = cropShape.state

    BoxWithConstraints(modifier = modifier) {
        val maxWidthPx = maxWidth.toPx()
        val maxHeightPx = maxHeight.toPx()

        LaunchedEffect(maxWidthPx, maxHeightPx) {
            when {
                cropState.size == Size.Zero -> {
                    cropState.setSize(maxWidthPx, maxHeightPx)
                }

                cropState.size.width > maxWidthPx || cropState.size.height > maxHeightPx -> {
                    cropState.setSize(maxWidthPx, maxHeightPx)
                }
            }
        }

        content()
        AnimatedVisibility(
            visible = drawGrid,
            modifier = Modifier
                .size(
                    width = maxWidth,
                    height = maxHeight
                )
                .align(Alignment.Center),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            cropShape.content(maxWidthPx, maxHeightPx)
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun previewCrop() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Crop {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                color = Color.Gray,
                content = {}
            )
        }
    }
}
