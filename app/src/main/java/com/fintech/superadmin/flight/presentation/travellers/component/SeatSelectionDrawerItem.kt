package com.fintech.superadmin.flight.presentation.travellers.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fintech.superadmin.flight.domain.model.TravellerModel
import com.fintech.superadmin.flight.presentation.travellers.TravellersViewModel
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.flight.util.common.textSdp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SeatSelectionItem(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    traveller: TravellerModel,
    viewModel: TravellersViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.sdp)
            .border(width = .75.dp, color = MaterialTheme.colorScheme.onSurface)
    ) {
        Column(
            modifier = Modifier.padding(5.sdp)
        ) {
            Text(
                text = "${traveller.title.value?.title ?: ""} ${traveller.fname.value ?: ""} ${traveller.lname.value ?: ""} ",
                maxLines = 2,
                fontSize = 10.textSdp,
                overflow = TextOverflow.Ellipsis
            )
            traveller.seatDetailsItem.value?.let {
                Text(
                    text = buildAnnotatedString {
                        traveller.seatDetailsItem.value?.let { seatDetailsItem ->
                            append("Seat: ${seatDetailsItem.sSRTypeName} ")
                            append("Price: ${seatDetailsItem.totalAmount}")
                        }
                    },
                    maxLines = 2,
                    fontSize = 10.textSdp,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(3.sdp)
                    )
                    .clip(shape = RoundedCornerShape(3.sdp))
                    .clickable {
                        scope.launch(Dispatchers.IO) {
                            viewModel.selectSeatsCall(
                                context = context,
                                travellerModel = traveller
                            )
                        }
                    }
            ) {
                Text(
                    text = "Select Seat",
                    modifier = Modifier.padding(horizontal = 5.sdp, vertical = 3.sdp),
                    color = Color.White,
                    fontSize = 8.textSdp
                )
            }


        }
    }
}