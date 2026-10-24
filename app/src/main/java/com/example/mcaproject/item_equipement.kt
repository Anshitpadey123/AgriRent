
package com.example.mcaproject

data class Equipment(
    val id: Int,
    val name: String,
    val category: String,
    val price: Int,
    val available: Boolean,
    val emoji: String,
    val location: String
)
