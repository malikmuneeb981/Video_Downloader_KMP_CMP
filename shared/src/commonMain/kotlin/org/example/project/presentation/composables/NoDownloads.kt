package org.example.project.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.ic_nodownloads
import org.jetbrains.compose.resources.vectorResource

@Composable
fun NoDownloads(text: String="No Downloads") {

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)
        .height(100.dp), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(imageVector = vectorResource(Res.drawable.ic_nodownloads),
            contentDescription = null, modifier = Modifier.weight(1f))
        AppText(text = text, modifier = Modifier.padding(top = 10.dp),
            fontWeight = FontWeight.SemiBold)
    }
}

@Preview
@Composable
private fun NoDownloadsPrev() {
    NoDownloads()
}