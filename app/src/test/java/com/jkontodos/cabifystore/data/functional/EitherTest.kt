package com.jkontodos.cabifystore.data.functional

import com.jkontodos.cabifystore.data.common.Either
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf

import org.junit.Test

class EitherTest {
    @Test
    fun `Either Right should return correct type`() {
        val result = Either.Right("ironman")

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldBe false
        result.isRight shouldBe true
        result.either({},
            { right ->
                right shouldBeInstanceOf String::class.java
                right shouldBeEqualTo "ironman"
            })
    }

    @Test
    fun `Either Left should return correct type`() {
        val result = Either.Left("ironman")

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldBe true
        result.isRight shouldBe false
        result.either(
            { left ->
                left shouldBeInstanceOf String::class.java
                left shouldBeEqualTo "ironman"
            }, {})
    }
}