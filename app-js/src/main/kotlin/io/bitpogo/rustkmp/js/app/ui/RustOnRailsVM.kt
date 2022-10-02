/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package io.bitpogo.rustkmp.js.app.ui

import io.bitpogo.rustkmp.bignumber.BigUIntegerContract.BigUInteger
import io.bitpogo.rustkmp.bignumber.BigUIntegerFactory
import kotlin.js.Promise
import kotlinx.coroutines.await
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RustOnRailsVM {
    private val bigIntFactory = BigUIntegerFactory()

    private val _number1: MutableStateFlow<String> = MutableStateFlow("")
    val number1: StateFlow<String> = _number1

    private val _number2: MutableStateFlow<String> = MutableStateFlow("")
    val number2: StateFlow<String> = _number2

    private val _operator: MutableStateFlow<Char> = MutableStateFlow('+')
    val operator: StateFlow<Char> = _operator

    private val _result: MutableStateFlow<String> = MutableStateFlow("Nothing")
    val result: StateFlow<String> = _result

    fun setNumber1(number: String) = _number1.update { number }
    fun setNumber2(number: String) = _number2.update { number }
    fun setOperator(operator: String) = _operator.update { operator.getOrElse(0) { '+' } }

    private suspend fun calculate(
        number1: BigUInteger,
        number2: BigUInteger,
    ): Promise<BigUInteger> {
        return when (_operator.value) {
            '+' -> Promise.resolve(number1 + number2)
            '-' -> Promise.resolve(number1 - number2)
            '*' -> Promise.resolve(number1 * number2)
            '/' -> Promise.resolve(number1 / number2)
            else -> Promise.reject(IllegalStateException("Unknown operator!"))
        }
    }

    private suspend fun Promise<BigUInteger>.unwrapAndSet() {
        val result = this
            .then { result -> result }
            .catch { error -> throw error }
            .await<BigUInteger>()
            .asString()

        _result.update { result }
    }

    suspend fun calculate() {
        val number1 = bigIntFactory.from(_number1.value)
        val number2 = bigIntFactory.from(_number2.value)

        calculate(number1, number2).unwrapAndSet()
    }
}
