package com.starcodex.gorillatest.util

import com.starcodex.gorillatest.R

enum class IceFragmentsModel constructor(
    val title: String,
    val subtitle: String,
    val backgroundColorResource : String,
    val type : String) {
    FLAVORS("Step 1", "Pick an icecream", "#D1D9FC", "flavor"),
    TOPPINGS("Step 2", "Pick a topping", "#C8FDFE", "topping")
}