package com.versilistyson

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required

class ParserCLI(private val bigramResolver: BigramResolver, private val fileManager: FileManager) {

    private val parser = ArgParser("bgram")

    private val inputFile =
        parser.option(
            type = ArgType.String,
            fullName = "input-file",
            shortName = "i",
            description = "Input file location"
        ).required()

    private val outputFile =
        parser.option(
            type = ArgType.String,
            fullName = "output-file",
            shortName = "o",
            description = "Output file location. If not set, results will be printed to console"
        )

    fun execute(args: Array<String>) {
        parser.parse(args)

        println("resolving file from input location ${inputFile.value}")

        val content = fileManager.readFile(inputFile.value)
        val histogram = bigramResolver.convertToHistogram(content)

        outputFile.value?.let { outputFilePath ->
            fileManager.writeHistogramToFile(outputFilePath, histogram)
        } ?: fileManager.printHistogram(histogram)
    }
}
