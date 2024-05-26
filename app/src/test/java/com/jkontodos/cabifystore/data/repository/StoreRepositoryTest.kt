package com.jkontodos.cabifystore.data.repository

import com.jkontodos.cabifystore.data.common.Either
import com.jkontodos.cabifystore.data.exception.Failure
import com.jkontodos.cabifystore.data.server.response.ProductListResponse
import com.jkontodos.cabifystore.data.source.LocalDataSource
import com.jkontodos.cabifystore.data.source.RemoteDataSource
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBe
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StoreRepositoryTest {
    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var localDataSource: LocalDataSource

    private lateinit var storeRepository: StoreRepository

    @Before
    fun setUp() {
        storeRepository = StoreRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `get Products OK`() {
        runBlocking {
            val testResponse = ProductListResponse(listOf())
            whenever(remoteDataSource.getProductList()).thenReturn(
                Either.Right(testResponse)
            )

            val result = storeRepository.getProducts()
            result.isRight shouldBe true
        }
    }

    @Test
    fun `get Products KO`() {
        runBlocking {
            val testResponse = Failure.ServerError
            whenever(remoteDataSource.getProductList()).thenReturn(
                Either.Left(testResponse)
            )

            val result = storeRepository.getProducts()
            result.isLeft shouldBe true
        }
    }
}