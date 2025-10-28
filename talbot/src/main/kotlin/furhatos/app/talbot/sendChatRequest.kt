package furhatos.app.talbot

import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import furhatos.app.talbot.flow.chatHistory
import furhatos.app.talbot.flow.lastPrintedIndex
import furhatos.app.talbot.flow.service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


//Lägg true om du vill ha printat vad som skickas till och från openAI
const val debugPrint = true

fun sendChatRequest(messages: List<ChatMessage>, logObject: Logger): String? {
    // Formatter for timestamps
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    // Only print messages that haven't been printed yet
    messages.forEachIndexed { index, message ->
        if (index > lastPrintedIndex) {
            val timestamp = LocalDateTime.now().format(dateFormatter)

            logObject.log("Message ${index + 1}:")
            logObject.log("Role: ${message.role}")
            logObject.log("Content: ${message.content}\n")


            if(debugPrint) {
                println("Timestamp: $timestamp")
                println("Message ${index + 1}:")
                println("Role: ${message.role}")
                println("Content: ${message.content}")
                println("\n\n") // 4 newlines between messages
            }
            // Update lastPrintedIndex after printing this message
            lastPrintedIndex = index
        }
    }

    // Set up the ChatCompletionRequest
    val completionRequest = ChatCompletionRequest.builder()
        //.model("gpt-4o-mini-2024-07-18")
        .model("gpt-4o-2024-08-06")
        .messages(messages)
        .maxTokens(150)
        .temperature(0.7)
        .build()

    // Try sending the request and handle response
    return try {
        val completion = service.createChatCompletion(completionRequest)
        val responseMessage = completion.choices.first().message.content

        // Print the response with timestamp, numbering, and formatting
        val responseTimestamp = LocalDateTime.now().format(dateFormatter)

        // Check if the response has been printed already
        if (lastPrintedIndex < messages.size) {
            lastPrintedIndex = messages.size // Update lastPrintedIndex to the size of messages before printing response

            logObject.log("Message ${lastPrintedIndex + 1}:")  // Increment message number for the response
            logObject.log("Role: assistant")
            logObject.log("Content: $responseMessage \n")

            if(debugPrint) {
                println("Message ${lastPrintedIndex + 1}:")  // Increment message number for the response
                println("Timestamp: $responseTimestamp")
                println("Role: assistant")
                println("Content: $responseMessage")
                println("\n\n") // 4 newlines after the response
            }
            chatHistory.add(ChatMessage("assistant", responseMessage))  // Add assistant's response to history
        }

        responseMessage
    } catch (e: Exception) {
        println("Error calling ChatGPT: ${e.message}")  // Log the error
        e.printStackTrace()  // Print stack trace for troubleshooting
        null
    }
}