package com.dicoding.mycomposesneakers.ui.screen.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.mycomposesneakers.di.Injection
import com.dicoding.mycomposesneakers.ui.ViewModelFactory
import com.dicoding.mycomposesneakers.ui.common.UiState
import com.dicoding.mycomposesneakers.ui.components.CartItem
import com.dicoding.mycomposesneakers.ui.components.OrderButton
import com.dicoding.mycomposesneakers.R
import androidx.compose.material3.*

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onOrderButtonClicked: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedOrderSneakers()
            }
            is UiState.Success -> {
                CartContent(
                    uiState.data,
                    onProductCountChanged = { sneakersId, count ->
                        viewModel.updateOrderReward(sneakersId, count)
                    },
                    onOrderButtonClicked = onOrderButtonClicked
                )
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    state: CartState,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    onOrderButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shareMessage = stringResource(
        R.string.share_message,
        state.orderSneakers.count(),
        state.totalPrice
    )
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.cart_page),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            )
        },
        content = {innerPadding ->
            Column(
                modifier = modifier.fillMaxSize()
            ) {
                OrderButton(
                    text = stringResource(R.string.total_order, state.totalPrice),
                    enabled = state.orderSneakers.isNotEmpty(),
                    onClick = {
                        onOrderButtonClicked(shareMessage)
                    },
                    modifier = Modifier.padding(innerPadding).padding(16.dp)
                )
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(state.orderSneakers, key = {it.sneakers.id}) { item ->
                        CartItem(
                            sneakerId = item.sneakers.id,
                            image = item.sneakers.image,
                            title = item.sneakers.title,
                            totalPrice = item.sneakers.price * item.count,
                            count = item.count,
                            onProductCountChanged = onProductCountChanged,
                        )
                        Divider()
                    }
                }
            }
        }
    )
}