package com.versilistyson

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.PrintStream

class FileManagerTest {

    companion object {
        private const val testResourceFilePath = "src/test/resources/test-files"
    }

    val fileManager = FileManager()

    @Nested
    @DisplayName("When calling readFile")
    inner class WhenCallingReadFile {
        @Nested
        @DisplayName("And passed a file location that does not exist")
        inner class AndPassedAFileLocationThatDoesNotExist {
            @Test
            fun `Should throw FileNotFoundException`() {
                assertThrows<FileNotFoundException> { fileManager.readFile("this/doesnt/exist") }
            }
        }
        @Nested
        @DisplayName("And passed sample-case as file location")
        inner class AndPassedSampleCaseAsFileLocation {
            @Test
            fun `Should return expected list of words`() {
                // GIVEN
                val fileLocation = "$testResourceFilePath/sample-case.txt"
                val expected = listOf("The", "quick", "brown", "fox", "and", "the", "quick", "blue", "hare.")

                // WHEN
                val actual = fileManager.readFile(fileLocation)

                // THEN
                assertEquals(expected, actual)
            }
        }
        @Nested
        @DisplayName("And passed a file location containing an empty document")
        inner class AndPassedAFileLocationContainingAnEmptyDocument {
            @Test
            fun `Should return an empty list`() {
                // GIVEN
                val fileLocation = "$testResourceFilePath/nothing.txt"

                // WHEN
                val actual = fileManager.readFile(fileLocation)

                // THEN
                assert(actual.isEmpty())
            }
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    @DisplayName("When calling writeHistogramToFile")
    inner class WhenCallingWriteHistogramToFile {
        private val writeFilePath = "$testResourceFilePath/write.txt"
        private fun clearFile() {
            val file = File(writeFilePath)
            if(file.exists()) {
                file.delete()
            }
            file.createNewFile()
        }
        @BeforeAll
        private fun setupFile() {
            clearFile()
        }
        @AfterEach
        private fun resetFile() {
            clearFile()
        }
        @Nested
        @DisplayName("And histogram is empty")
        inner class AndHistogramIsEmpty {
            @Test
            fun `Should not write any data to file`() {
                // GIVEN
                val histogram = mapOf<String, Int>()

                // WHEN
                fileManager.writeHistogramToFile(writeFilePath, histogram)


                // THEN
                assert(fileManager.readFile(writeFilePath).isEmpty())
            }
        }
        @Nested
        @DisplayName("And histogram is not empty")
        inner class AndHistogramIsNotEmpty {
            @Test
            fun `Should successfully write expected data`() {
                // GIVEN
                val histogram = mapOf(
                    "this is" to 1,
                    "some data" to 1
                )

                val expected = "\"this is\" 1\n\"some data\" 1"

                // WHEN
                fileManager.writeHistogramToFile(writeFilePath, histogram)
                val actual = File(writeFilePath).readText().trim()

                // THEN
                assertEquals(expected, actual)
            }
        }
    }

    @Nested
    @DisplayName("When calling print histogram")
    inner class WhenCallingPrintHistogram {
        private val standardOut = System.out
        private val outputStreamCaptor = ByteArrayOutputStream()
        @BeforeEach
        private fun setUp() {
            System.setOut(PrintStream(outputStreamCaptor))
        }
        @AfterEach
        private fun tearDown() {
            System.setOut(standardOut)
        }
        @Nested
        @DisplayName("And histogram is empty")
        inner class AndHistogramIsEmpty {
            @Test
            fun `Should not print anything to the console`() {
                // WHEN
                fileManager.printHistogram(mapOf())

                // THEN
                assertTrue(outputStreamCaptor.toString().isBlank())
            }
        }
        @Nested
        @DisplayName("And histogram is not empty")
        inner class AndHistogramIsNotEmpty {
            @Test
            fun `Should return expected data`() {
                // GIVEN
                val histogram =
                    mapOf(
                        "this is" to 1,
                        "test data" to 1
                    )

                val expected = "\"this is\" 1\n\"test data\" 1"

                // WHEN
                fileManager.printHistogram(histogram)

                // THEN
                assertEquals(expected, outputStreamCaptor.toString().trim())
            }
        }
    }
}
