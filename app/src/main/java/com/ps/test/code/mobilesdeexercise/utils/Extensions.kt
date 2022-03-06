package com.ps.test.code.mobilesdeexercise.utils

/**
 * String Extension function to check whether a string length is even or odd
 * @return 0 if even else 1
 */
fun String.isLengthEvenOrOdd(): Int {
    return if (length % 2 == 0) 0 else 1
}