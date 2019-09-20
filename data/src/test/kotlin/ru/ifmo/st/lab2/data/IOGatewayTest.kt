package ru.ifmo.st.lab2.data

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.gateway.IOGateway
import java.io.File

class IOGatewayTest {
    companion object {
        lateinit var ioGateway: IOGateway

        @BeforeAll
        @JvmStatic
        fun init() {
            ioGateway = DiskIOGateway()
        }
    }

    private val text = """
            some multiline text
            for test
            test
        """.trimIndent()

    @Test
    fun `test read from file`() {
        val fileName = "testRead.txt"
        File(fileName).writeText(text)

        val actual = ioGateway.readFromFile(fileName)

        Assertions.assertEquals(text, actual)

        File(fileName).delete()
    }

    @Test
    fun `test write to file`() {
        val fileName = "testWrite.txt"

        ioGateway.writeToFile(fileName, text)

        val actual = File(fileName).readText()

        Assertions.assertEquals(text, actual)

        File(fileName).delete()
    }
}
