package org.example.cross.card

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform