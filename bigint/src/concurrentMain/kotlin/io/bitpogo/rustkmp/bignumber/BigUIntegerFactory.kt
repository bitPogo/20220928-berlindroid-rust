/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package io.bitpogo.rustkmp.bignumber

import io.bitpogo.rustkmp.bignumber.BigUIntegerContract.BigUIntArithmetic
import io.bitpogo.rustkmp.bignumber.BigUIntegerContract.BigUInteger

expect class BigUIntegerFactory internal constructor(
    rechenwerk: BigUIntArithmetic,
) : BigUIntegerContract.BigUIntegerFactory {
    constructor()
    override fun from(number: String): BigUInteger
    override fun from(number: UInt): BigUInteger

    @OptIn(ExperimentalUnsignedTypes::class)
    override fun from(bytes: UByteArray): BigUInteger
    override fun getProbablePrime(size: Int): BigUInteger
}
