/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package io.bitpogo.rustkmp.android.app

import androidx.lifecycle.ViewModel
import io.bitpogo.rustkmp.bignumber.BigUIntegerContract.BigUInteger
import io.bitpogo.rustkmp.bignumber.BigUIntegerFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RustOnRailsVM : ViewModel() {
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

    private fun calculate(
        number1: BigUInteger,
        number2: BigUInteger,
    ): BigUInteger {
        return when (_operator.value) {
            '+' -> number1 + number2
            '-' -> number1 - number2
            '*' -> number1 * number2
            '/' -> number1 / number2
            else -> throw IllegalStateException("Unknown operator!")
        }
    }

    fun calculate() {
        val number1 = bigIntFactory.from(_number1.value)
        val number2 = bigIntFactory.from(_number2.value)

        _result.update {
            calculate(number1, number2).toString()
        }
    }
}
