package com.lukianbat.architecture.data

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class FileStorage(private val context: Context) {

    private val storageDir = context.filesDir

    fun tempFile(named: String, extension: String): File {
        if (!storageDir.exists()) {
            storageDir.mkdir()
        }

        return File.createTempFile(named, ".$extension", storageDir)
    }

    fun asset(named: String): File {
        val assetStream = context.assets.open(named)

        val nameParts = named.split('.')
        val tempFile = tempFile(nameParts[0], nameParts[1])

        writeFile(assetStream, tempFile)
        return tempFile
    }

    fun cleanUp()  {
        if (!storageDir.exists())
            return

        storageDir.deleteRecursively()
    }

    fun writeFile(input: InputStream, file: File) {
        input.use { _ ->
            val output = FileOutputStream(file)
            output.use { _ ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int

                do {
                    read = input.read(buffer)
                    if (read >= 0) {
                        output.write(buffer, 0, read)
                    }
                } while (read != -1)

                output.flush()
            }
        }
    }
}