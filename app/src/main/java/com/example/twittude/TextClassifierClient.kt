package com.example.twittude

import android.content.Context
import android.content.res.AssetManager
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel


/**
 * Load in TFLite Model and provide predictions
 */
class TextClassifierClient(context: Context) {

    private val labels = mutableListOf<String>()
    private val dict = mutableMapOf<String, Int>()


    @Throws(IOException::class)
    private fun loadLabelsFile(assetManager: AssetManager) {
        val ins = assetManager.open(LABEL_PATH)
        val reader = BufferedReader(InputStreamReader(ins))
        //Each line in the file is a label
        while (reader.ready()) {
            labels.add(reader.readLine())
        }
    }

    @Throws(IOException::class)
    private fun loadDictionaryFile(assetManager: AssetManager) {
        val ins = assetManager.open(DICT_PATH)
        val reader = BufferedReader(InputStreamReader(ins))
        //Dictionary has 2 columns
        // WORD  |  COUNT of word
        while (reader.ready()) {
            // Word and count separated by a space
            val line = reader.readLine().split(" ")
            //if word doesn't have a count or vice versa, don't add it to dictionary
            if (line.size < 2)
                continue;
            dict[line[0]] = line[1].toInt()
        }
    }

    companion object {
        const val MODEL_PATH = "text_classification.tflite"
        const val LABEL_PATH = "text_classification_labels.txt"
        const val DICT_PATH = "text_classification_vocab.txt"

        @Throws(IOException::class)
        fun loadModel(assetManager: AssetManager): MappedByteBuffer {
            val fileDescriptor = assetManager.openFd(MODEL_PATH)
            val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
            val fileChannel = inputStream.channel
            val startOffset = fileDescriptor.startOffset
            val declaredLength = fileDescriptor.declaredLength
            return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
        }
    }

}