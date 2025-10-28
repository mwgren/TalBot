package furhatos.app.talbot

import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.Date

class Logger {
    private val logFile: File

    init {
        // Ensure the ./logs directory exists
        val logDirectory = File("./logs")
        if (!logDirectory.exists()) {
            logDirectory.mkdir() // Create the directory if it doesn't exist
        }

        // Generate a unique file name with a timestamp
        val timestamp = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Date())
        logFile = File(logDirectory, "app_log_$timestamp.log")

        // Create the file (empty to start)
        logFile.createNewFile()
    }

    fun log(message: String) {
        // Append the message to the log file
        PrintWriter(FileWriter(logFile, true)).use { writer ->
            writer.println("[${currentTime()}] $message")
        }
    }

    private fun currentTime(): String {
        // Format the current time for log entries
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
    }
}