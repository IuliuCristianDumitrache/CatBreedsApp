package com.example.cat.extensions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StringExtensionTests {
    @Test
    fun `given an email grek@gmailcom when logging in then an error is shown`() {
        val validEmail = "grek@gmailcom".isValidEmail()
        val expected = "false"

        assertEquals(expected, validEmail)
    }
}