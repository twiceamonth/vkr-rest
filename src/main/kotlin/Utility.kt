package ru.mav26

import java.util.UUID

object Utility {

    fun checkUuid(uuid: String): Boolean {
        try {
            val id = UUID.fromString(uuid)
            return true
        } catch (e: IllegalArgumentException) {
            return false
        }
    }

}