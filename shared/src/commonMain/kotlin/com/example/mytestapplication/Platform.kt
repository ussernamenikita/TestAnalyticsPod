package com.example.mytestapplication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform