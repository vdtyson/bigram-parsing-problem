package com.versilistyson

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ParserCLITest {
    @MockK
    lateinit var mockResolver: BigramResolver

    @MockK
    lateinit var mockFileManager: FileManager

    @InjectMockKs
    lateinit var parserCLI: ParserCLI


    @Nested
    @DisplayName("When calling execute")
    inner class WhenCallingExecute {
        @Nested
        @DisplayName("And not passed required input-file flag")
        inner class AndNotPassedRequiredInputFileFlag {
            @Test
            fun `Should throw IllegalStateException`() {
                // GIVEN
                val args = arrayOf<String>()


                // THEN
                assertThrows(IllegalStateException::class.java) { parserCLI.execute(args) }
            }
        }

        @Nested
        @DisplayName("And passed input file with no output file")
        inner class AndPassedInputFileWithKnowOutputFile {
            @Test
            fun `Should call method to print histogram in console`() {
                // GIVEN
                val expected = mapOf("this" to 1)
                val filePath = "this/is/a/fake/filepath"
                val args = arrayOf("-i", filePath)

                every { mockFileManager.readFile(any()) } returns listOf()
                every { mockResolver.convertToHistogram(any()) } returns expected
                every { mockFileManager.printHistogram(any()) } returns Unit

                // WHEN
                parserCLI.execute(args)

                // THEN
                verify(exactly = 1) { mockFileManager.printHistogram(mapOf("this" to 1)) }
            }
        }

        @Nested
        @DisplayName("And passed input and output file")
        inner class AndPassedInputAndOutputFile {
            @Test
            fun `Should call method to write histogram in file`() {
                // GIVEN
                val expected = mapOf("test" to 1)
                val inputFilePath = "this/is/an/input/filepath"
                val outputFilePath = "this/is/an/output/filepath"
                val args = arrayOf("-i", inputFilePath, "-o", outputFilePath)

                every { mockFileManager.readFile(inputFilePath) } returns listOf()
                every { mockResolver.convertToHistogram(any()) } returns expected
                every { mockFileManager.writeHistogramToFile(any(), any()) } returns Unit

                // WHEN
                parserCLI.execute(args)

                // THEN
                verify(exactly = 1) { mockFileManager.writeHistogramToFile(outputFilePath, expected) }
            }
        }
    }
}
