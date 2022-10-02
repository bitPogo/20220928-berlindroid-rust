/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package io.bitpogo.rustkmp.js.app.ui

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import tech.antibytes.kfixture.PublicApi
import tech.antibytes.kfixture.fixture
import tech.antibytes.kfixture.kotlinFixture
import tech.antibytes.util.test.coroutine.AsyncTestReturnValue
import tech.antibytes.util.test.coroutine.runBlockingTestInContext
import tech.antibytes.util.test.coroutine.runBlockingTestWithTimeout
import tech.antibytes.util.test.mustBe

@OptIn(ExperimentalCoroutinesApi::class)
class RustOnRailsVMSpec {
    private val fixture = kotlinFixture()
    private val scheduler = TestCoroutineScheduler()
    private lateinit var scope: CoroutineScope

    @BeforeTest
    fun setUp() {
        scope = CoroutineScope(scheduler)
    }

    @Test
    fun Number1_is_empty_on_default() {
        // When
        val viewModel = RustOnRailsVM()

        // Then
        viewModel.number1.value mustBe ""
    }

    @Test
    fun Given_setNumber1_is_called_it_sets_a_number() {
        // Given
        val number: String = fixture.fixture<Int>(PublicApi.Sign.POSITIVE).toString()

        // When
        val viewModel = RustOnRailsVM()
        viewModel.setNumber1(number)

        // Then
        viewModel.number1.value mustBe number
    }

    @Test
    fun Number2_is_empty_on_default() {
        // When
        val viewModel = RustOnRailsVM()

        // Then
        viewModel.number2.value mustBe ""
    }

    @Test
    fun Given_setNumber2_is_called_it_sets_a_number() {
        // Given
        val number: String = fixture.fixture<Int>(PublicApi.Sign.POSITIVE).toString()

        // When
        val viewModel = RustOnRailsVM()
        viewModel.setNumber2(number)

        // Then
        viewModel.number2.value mustBe number
    }

    @Test
    fun Operator_is_Plus_on_default() {
        // When
        val viewModel = RustOnRailsVM()

        // Then
        viewModel.operator.value mustBe '+'
    }

    @Test
    fun Given_setOperator_is_called_it_sets_an_opertator() {
        // Given
        val operator = fixture.fixture<Char>().toString()

        // When
        val viewModel = RustOnRailsVM()
        viewModel.setOperator(operator)

        // Then
        viewModel.operator.value mustBe operator[0]
    }

    @Test
    fun Given_calculate_is_called_with_an_unknown_operator_it_fails(): AsyncTestReturnValue {
        // Given
        val number1: String = fixture.fixture<Int>(PublicApi.Sign.POSITIVE).toString()
        val number2: String = fixture.fixture<Int>(PublicApi.Sign.POSITIVE).toString()
        val operator = fixture.fixture<Char>().toString()

        // When
        val viewModel = RustOnRailsVM()
        viewModel.setNumber1(number1)
        viewModel.setNumber2(number2)
        viewModel.setOperator(operator)

        // Then
        return runBlockingTestWithTimeout {
            // When
            val error = assertFailsWith<IllegalStateException> {
                viewModel.calculate()
            }

            error.message mustBe "Unknown operator!"
        }
    }

    @Test
    fun Given_calculate_is_called_with_an_known_operator_it_runs_the_operation(): AsyncTestReturnValue {
        // Given
        val number1: Int = fixture.fixture(
            range = 0..10,
        )
        val number2: Int = fixture.fixture(
            range = 0..10,
        )
        val operator = "+"

        // When
        val viewModel = RustOnRailsVM()
        viewModel.setNumber1(number1.toString())
        viewModel.setNumber2(number2.toString())
        viewModel.setOperator(operator)

        return runBlockingTestInContext(scheduler) {
            viewModel.calculate()
            scheduler.advanceUntilIdle()

            // Then
            viewModel.result.value mustBe (number1 + number2).toString()
        }
    }
}
