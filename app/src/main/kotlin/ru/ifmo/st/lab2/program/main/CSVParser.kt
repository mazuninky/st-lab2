package ru.ifmo.st.lab2.program.main


class CSVParser(private val filePath: String) {

    fun parse(): Map<String, String> {
        val mutableMap = mutableMapOf<String, String>()
        requireNotNull(CSVParser::class.java.classLoader.getResourceAsStream(filePath))
                .reader()
                .useLines { reader ->
                    reader.drop(1)
                            .forEach {
                                val values = it.split(";")
                                check(values.size == 2)

                                mutableMap[values.first()] = values[1].replace("\\n", "\n")
                            }
                }

        return mutableMap
    }
}
