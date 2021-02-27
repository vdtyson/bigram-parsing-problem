package com.versilistyson

import java.io.File
import java.util.*

class FileManager {
    /**
     * Retrieves text content from a passed file location.
     * Skips any non letters during the scanning process.
     *
     * @param fileLocation The location to retrieve the specified text file from
     * @return A List of String
     * @throws FileNotFoundException when a file is not able to be located
     */
    fun readFile(fileLocation: String): List<String> {
        var content: String? = null
        val scanner = Scanner(File(fileLocation)).useDelimiter("\\W")
        while (scanner.hasNext()) {
            if (content == null) {
                content = ""
            }
            content += scanner.nextLine()
        }
        return content?.split(" ") ?: listOf()
    }

    /**
     * Writes resolved histogram to specified file location
     *
     * @param fileLocation The location to write the histograms to
     */
    fun writeHistogramToFile(fileLocation: String, histogram: Map<String, Int>) {

        println("writing histogram to location: $fileLocation")

        val outputFile = File(fileLocation)
        var outputString = ""
        histogram.forEach { (bigram, occurrences) ->
            outputString += entryToString(bigram, occurrences)
        }
        outputFile.writeText(outputString)
    }

    /**
     * Prints histogram to console.
     */
    fun printHistogram(histogram: Map<String, Int>) {
        histogram.forEach { (bigram, occurrences) ->
            print(entryToString(bigram, occurrences))
        }
    }

    private fun entryToString(bigram: String, occurrences: Int): String {
        return "\"$bigram\" $occurrences\n"
    }

}

