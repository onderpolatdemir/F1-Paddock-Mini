package com.onder.f1PaddockMini.pages

import com.onder.f1PaddockMini.R

/**
 * Helper function to get driver image URL from driver name
 */
fun getDriverImage(driverName: String): String? {
    val nameLower = driverName.lowercase()
    
    // Try exact match first (e.g., "max_verstappen")
    val exactMatch = nameLower.replace(" ", "_")
    driverImageMap[exactMatch]?.let { return it }
    
    // Try with only surname (e.g., "verstappen" from "Max Verstappen")
    val surname = nameLower.split(" ").lastOrNull() ?: nameLower
    driverImageMap[surname]?.let { return it }
    
    // Try with only first name (e.g., "max" from "Max Verstappen")
    val firstName = nameLower.split(" ").firstOrNull() ?: nameLower
    driverImageMap[firstName]?.let { return it }
    
    // Try common variations
    val variations = listOf(
        nameLower.replace(" ", ""),
        nameLower.replace("-", "_"),
        nameLower.replace("'", ""),
    )
    
    variations.forEach { variation ->
        driverImageMap[variation]?.let { return it }
    }
    
    // Fallback to null (UI will handle fallback to placeholder local resource)
    return null
}