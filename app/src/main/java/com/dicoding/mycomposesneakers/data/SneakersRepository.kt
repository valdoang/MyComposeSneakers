package com.dicoding.mycomposesneakers.data

import com.dicoding.mycomposesneakers.model.FakeSneakersDataSource
import com.dicoding.mycomposesneakers.model.OrderSneakers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class SneakersRepository {

    private val orderSneakers = mutableListOf<OrderSneakers>()

    init {
        if (orderSneakers.isEmpty()){
            FakeSneakersDataSource.dummySneakers.forEach{
                orderSneakers.add(OrderSneakers(it,0))
            }
        }
    }

    fun getAllSneakers(): Flow<List<OrderSneakers>> {
        return flowOf(orderSneakers)
    }

    fun getOrderSneakerById(sneakersId: Long): OrderSneakers {
        return orderSneakers.first{
            it.sneakers.id == sneakersId
        }
    }

    fun updateOrderSneaker(sneakersId: Long, newCountValue: Int):Flow<Boolean> {
        val index = orderSneakers.indexOfFirst { it.sneakers.id == sneakersId }
        val result = if(index >= 0) {
            val orderSneaker = orderSneakers[index]
            orderSneakers[index] =
                orderSneaker.copy(sneakers = orderSneaker.sneakers, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderSneakers():Flow<List<OrderSneakers>> {
        return getAllSneakers()
            .map { orderSneakers ->
                orderSneakers.filter { orderSneaker ->
                    orderSneaker.count != 0
                }
            }
    }

    fun searchSneakers(query: String): List<OrderSneakers>{
        return orderSneakers.filter {
            it.sneakers.title.contains(query, ignoreCase = true)
        }
    }

    companion object {
        @Volatile
        private var instance: SneakersRepository? = null

        fun getInstance(): SneakersRepository =
            instance ?: synchronized(this) {
                SneakersRepository().apply {
                    instance = this
                }
            }
    }
}