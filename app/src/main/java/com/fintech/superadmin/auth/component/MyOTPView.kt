import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.fintech.superadmin.clean.util.textSdp

@Composable
fun PinView(
    pinText: String,
    onPinTextChange: (String) -> Unit,
    digitColor: Color = MaterialTheme.colors.onBackground,
    digitSize: TextUnit = 14.textSdp,
    containerSize: Dp = digitSize.value.dp * 2,
    digitCount: Int = 6,
) {
    BasicTextField(value = pinText,
        onValueChange = onPinTextChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(digitCount) { index ->
                    DigitView(index, pinText, digitColor, digitSize, containerSize)
                    if (digitCount == 6)
                        Spacer(modifier = Modifier.size(10.dp))
                    else
                        Spacer(modifier = Modifier.size(10.dp))
                }
            }
        })
}


@Composable
private fun DigitView(
    index: Int,
    pinText: String,
    digitColor: Color,
    digitSize: TextUnit,
    containerSize: Dp,
) {
    val modifier = Modifier
        .size(containerSize)
        .border(
            width = .9.dp,
            color = digitColor,
            shape = RectangleShape
        )

    Column(
        modifier =  modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (index >= pinText.length) "" else pinText[index].toString(),
            color = digitColor,
            style = MaterialTheme.typography.body1,
            fontSize = digitSize,
            textAlign = TextAlign.Center
        )
    }

}