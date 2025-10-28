package furhatos.app.talbot

import com.theokanning.openai.completion.chat.ChatMessage
import furhatos.app.talbot.flow.speechParameters
import furhatos.flow.kotlin.Furhat
import furhatos.gestures.Gestures

//ibland säger den [lekfull] och [förväntansfull]
fun parseEmotionAndDialog(response: String): Pair<String, String> {
    // Regex to match content inside square brackets
    val regex = """\[(.*?)\]""".toRegex()
    val match = regex.find(response)

    //om den skickar en känsla som inte finns så parsar den inte den. FIXA
    return if (match != null) {
        val emotion = match.groupValues[1] // Extract the emotion
        val dialog = response.replace(match.value, "").trim() // Remove the emotion tag from the response
        Pair(emotion, dialog) // Return the emotion and dialog as a Pair
    } else {
        Pair("neutral", response.trim()) // Default to neutral if no tag is found
    }
}


fun handleChatResponse(furhat: Furhat, chatHistory: List<ChatMessage>, turnObject: TurnManager, logObject: Logger) {
    var emotion: String = "neutral" // Set a default value for emotion
    var dialog: String? = null

    try {
        // Send chat request and process response
        val response = sendChatRequest(chatHistory, logObject)
        if (response != null) {
            // Parse emotion and dialog from response
            val (parsedEmotion, parsedDialog) = parseEmotionAndDialog(response.trim())
            emotion = parsedEmotion // Provide a default value in case of null
            dialog = parsedDialog
            //println("Emotion: $emotion")
            //println("Dialog: $dialog")
        } else {
            println("No response from chat request")
            return
        }
    } catch (e: Exception) {
        println("Problem with connection to OpenAI: ${e.message}")
        return
    }

    // Kontrollera om kommandot [next_turn] finns i dialogen
    val nextTurnCommand = Regex("""\[next_turn\]""")
    //If dialog is null, shouldNextTurn is assigned false
    val shouldNextTurn = dialog?.let { nextTurnCommand.containsMatchIn(it) } ?: false

    // Rensa kommandot [next_turn] och trimma dialogen
    dialog = if (dialog != null) {
        dialog.replace(nextTurnCommand, "").trim()
    } else {
        ""
    }

    // Dela upp dialogen vid frasen "Nästa spelare, din tur!"
    val parts = dialog.split("Nästa spelare, din tur!")


    // Apply gesture based on emotion tag before saying each part
    when (emotion) {
        "glad" -> furhat.gesture(Gestures.Smile(duration=1.0))
        "uppspelt" -> furhat.gesture(Gestures.BigSmile(duration=1.0))
        "lycklig" -> furhat.gesture(Gestures.BigSmile(duration=1.0))
        "sprudlande" -> furhat.gesture(Gestures.Surprise(duration=1.0))
        "nöjd" -> furhat.gesture(Gestures.Smile(duration=1.0))
        "tacksam" -> furhat.gesture(Gestures.Thoughtful(duration=1.0))
        "förväntansfull" -> furhat.gesture(Gestures.Thoughtful(duration=1.0))
        "lekfull" -> furhat.gesture(Gestures.Wink)
        "humoristisk" -> furhat.gesture(Gestures.BigSmile(duration=1.0))
        "neutral" -> furhat.gesture(Gestures.Thoughtful(duration=1.0))
    }

    // Sätt LED-färgen till svart innan Furhat börjar prata
    furhat.ledStrip.solid(java.awt.Color(0,0,0))

    // Säg första delen av dialogen om den finns
    if (parts.isNotEmpty()) {
        furhat.say(speechParameters + parts[0].trim())
    }

    // Byt tur omedelbart om kommandot [next_turn] finns
    if (shouldNextTurn) {
        turnObject.nextTurn(furhat)
        logObject.log("Turn changed immediately based on ChatGPT command [next_turn].\n")
        //println("Turn changed immediately based on ChatGPT command [next_turn].")
    } else {
        logObject.log("No [next_turn] command found in response; turn not changed.\n")
        //println("No [next_turn] command found in response; turn not changed.")
    }


    if (parts.size > 1) {
        furhat.say(speechParameters + "Nästa spelare, din tur!")
        furhat.say(speechParameters + parts[1].trim())
    }

    // Sätt LED-färgen till blått efter tal
    furhat.ledStrip.solid(java.awt.Color.BLUE)

    // Efter att dialogen är färdig och turen har växlats, låt Furhat lyssna igen
    furhat.listen(timeout = 14000)
}