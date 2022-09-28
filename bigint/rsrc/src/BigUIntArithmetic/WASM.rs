/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

#![cfg(target_family="wasm")]
#![allow(non_snake_case)]
use wasm_bindgen::prelude::*;
use crate::BigUIntArithmetic::*;
use std::str;

#[cfg(feature = "wee_alloc")]
#[global_allocator]
static ALLOC: wee_alloc::WeeAlloc = wee_alloc::WeeAlloc::INIT;

#[wasm_bindgen]
pub fn add(
    summand1: String,
    summand2: String
) -> String {
    let result = _add(
        summand1.as_bytes().to_vec(),
        summand2.as_bytes().to_vec(),
    );

    str::from_utf8(&result).unwrap().to_string()
}

#[wasm_bindgen]
pub fn subtract(
    minuend: String,
    subtrahend: String
) -> String {
    let result = _subtract(
        minuend.as_bytes().to_vec(),
        subtrahend.as_bytes().to_vec(),
    );

    str::from_utf8(&result).unwrap().to_string()
}

#[wasm_bindgen]
pub fn multiply(
    factor1: String,
    factor2: String
) -> String {
    let result = _multiply(
        factor1.as_bytes().to_vec(),
        factor2.as_bytes().to_vec(),
    );

    str::from_utf8(&result).unwrap().to_string()
}

#[wasm_bindgen]
pub fn divide(
    dividend: String,
    divisor: String
) -> String {
    let result = _divide(
        dividend.as_bytes().to_vec(),
        divisor.as_bytes().to_vec(),
    );

    str::from_utf8(&result).unwrap().to_string()
}

#[wasm_bindgen]
pub fn remainder(
    number: String,
    modulus: String
) -> String {
    let result = _remainder(
        number.as_bytes().to_vec(),
        modulus.as_bytes().to_vec(),
    );

    str::from_utf8(&result).unwrap().to_string()
}

#[wasm_bindgen]
pub fn gcd(
    number: String,
    modulus: String
) -> String {
    let result = _gcd(
        number.as_bytes().to_vec(),
        modulus.as_bytes().to_vec(),
    );

    str::from_utf8(&result).unwrap().to_string()
}

#[wasm_bindgen]
pub fn shiftLeft(
    number: String,
    shifts: i64
) -> String {
    let result = _shiftLeft(
        number.as_bytes().to_vec(),
        shifts,
    );

    str::from_utf8(&result).unwrap().to_string()
}

#[wasm_bindgen]
pub fn shiftRight(
    number: String,
    shifts: i64
) -> String {
    let result = _shiftRight(
        number.as_bytes().to_vec(),
        shifts,
    );

    str::from_utf8(&result).unwrap().to_string()
}

#[wasm_bindgen]
pub fn modPow(
    base: String,
    exponent: String,
    modulus: String,
) -> String {
    let result = _modPow(
        base.as_bytes().to_vec(),
        exponent.as_bytes().to_vec(),
        modulus.as_bytes().to_vec(),
    );

    str::from_utf8(&result).unwrap().to_string()
}

#[wasm_bindgen]
pub fn modInverse(
    number: String,
    modulus: String,
) -> String {
    let result = _modInverse(
        number.as_bytes().to_vec(),
        modulus.as_bytes().to_vec(),
    );

    str::from_utf8(&result).unwrap().to_string()
}

#[wasm_bindgen]
pub fn intoString(
    number: String,
    radix: i32,
) -> String {
    _intoString(
        number.as_bytes().to_vec(),
        radix.try_into().unwrap(),
    )
}

#[wasm_bindgen]
pub fn compare(
    number: String,
    other: String,
) -> i32 {
    _compare(
        number.as_bytes().to_vec(),
        other.as_bytes().to_vec(),
    )
}
