package iti.gov.trendo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform