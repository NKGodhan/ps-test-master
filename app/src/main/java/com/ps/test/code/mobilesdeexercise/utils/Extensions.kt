package com.ps.test.code.mobilesdeexercise.utils

import kotlin.math.sqrt

/**
 * String Extension function to check whether a string length is even or odd
 * @return true if even else false
 */
fun String.isLengthEvenOrOdd(): Boolean {
    return length % 2 == 0
}

/**
 * Int Extension function to factors of a number
 * @return list of factors for the number
 */
fun Int.getFactors(): ArrayList<Int> {
    val sqrt = sqrt(this.toDouble()).toInt()    // Ignoring fraction of value after decimal point, could make slight differences, maybe?
    val factorList = arrayListOf<Int>()
    for (i in 2..sqrt) {
        if (this % i == 0) {
            if (this / i == i) {
                factorList.add(i)
            } else {
                factorList.add(i)
                factorList.add(this / i)
            }
        }
    }

    return factorList
}