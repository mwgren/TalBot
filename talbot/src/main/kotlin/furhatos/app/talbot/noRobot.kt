package furhatos.app.talbot

import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import furhatos.app.talbot.flow.*


fun noRobot() {

    // Initial chat history setup
    chatHistory.add(ChatMessage("system", systemPrompt))
    chatHistory.add(ChatMessage("assistant", "hej, är ni redo att spela Alias?"))
    chatHistory.add(ChatMessage("user", "ja"))

    val completionRequest = ChatCompletionRequest.builder()
        .model("gpt-4o-2024-08-06")
        .messages(chatHistory)
        .maxTokens(150)
        .temperature(0.7)
        .build()

    val completion = service.createChatCompletion(completionRequest)
    val responseMessage = completion.choices.first().message.content
    println(responseMessage)

    // Add the assistant's response to the chat history
    chatHistory.add(ChatMessage("assistant", responseMessage))

    // Start the conversation loop
    while (true) {
        // Prompt the user for input
        print("Type something (or type 'exit' to quit): ")
        val input = readLine() // Reads a line of input from the terminal

        // Check if the user typed 'exit'
        if (input.equals("exit", ignoreCase = true)) {
            println("Goodbye!")
            break
        }

        // Add the user's message to the chat history
        chatHistory.add(ChatMessage("user", input))

        // Create a new completion request with the updated chat history
        val completionRequest = ChatCompletionRequest.builder()
            .model("gpt-4o-2024-08-06")
            .messages(chatHistory)
            .maxTokens(150)
            .temperature(0.7)
            .build()

        // Call the API and fetch the response
        val completion = service.createChatCompletion(completionRequest)
        val responseMessage = completion.choices.first().message.content

        // Add the assistant's response to the chat history
        chatHistory.add(ChatMessage("assistant", responseMessage))

        // Print the assistant's response
        println(responseMessage)
    }
}

