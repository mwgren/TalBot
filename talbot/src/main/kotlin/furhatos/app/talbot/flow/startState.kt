package furhatos.app.talbot.flow

import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.service.OpenAiService
import furhatos.app.furgui.DataDelivery
import furhatos.app.talbot.*
import furhatos.event.senses.SenseSkillGUIConnected
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.Voice
import furhatos.skills.HostedGUI
import furhatos.util.Gender
import furhatos.util.Language
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.*
import io.github.cdimascio.dotenv.dotenv

lateinit var furhatObject: Furhat

fun toggleMessage() {
    currentMessage = if (currentMessage == Intro_one) Intro_two else Intro_one
}

val dotenv = dotenv()
val apiKey = dotenv["OPENAI_API_KEY"] ?: throw IllegalStateException("Missing OPENAI_API_KEY")
val service = OpenAiService(apiKey)
val GUI = HostedGUI("ExampleGUI", "assets/exampleGui", 1234)
val lists = listOf(listOne, listTwo, listThree, listFour)
var chosenList = lists[0]
var didNotUnderstandIndex = 0
var didNotHearIndex = 0
val speechParametersOn = false
var speechParameters = ""
val chatHistory = mutableListOf<ChatMessage>()
var systemPrompt = ""
var lastPrintedIndex = -1
val turnObject = TurnManager()
val logObject = Logger()
val eventScope = CoroutineScope(Dispatchers.Default)

var currentMessage = "Nu är jag redo. Är ni redo för att spela Alias?"
val Intro_one = "Nu är jag redo. Är ni redo för att spela Alias?"
val Intro_two = """Hej! Jag ska spela med er idag. Jag lyssnar när jag lyser blått så prata bara då jag lyser blått. Jag förstår när ni pratar, men ibland hör jag fel, så det är lättare för mig att förstå om ni turas om att prata. Om ni inte förstår vad jag sa, kan ni säga "Vad sa du?" eller "Jag vet inte". Jag lär mig fortfarande, så säg gärna till om jag gör fel. Ska vi börja spela?"""


val didNotUnderStandResponses = listOf(
    "Ursäkta, nu förstod jag inte vad du sa. Kan du säga det en gång till?",
    "Ursäkta jag förstod fortfarande inte igen."
)
val didNotHearResponses = listOf(
    "Ursäkta, jag hörde inte vad du sa. Kan du säga det en gång till?",
    "Ursäkta jag hörde inte igen.",
)

val NoGUI: State = state(null) {
    onEvent<SenseSkillGUIConnected> {
        if (speechParametersOn) speechParameters = "\\rspd=92\\ \\vct=103\\"

        furhat.setInputLanguage(Language.SWEDISH)
        furhat.voice = Voice(name = "Elin22k_HQ", language = Language.SWEDISH, gender = Gender.MALE)

        turnObject.updateUsers(furhat)
        send(DataDelivery(0, null))
        goto(Intro)
    }
}


val Intro: State = state(null) {
    onEvent("WaitButton") {
        furhat.say(speechParameters + "Okej, jag pausar spelet")
        goto(WaitState)
    }

    onEvent("checkIntro") {
        toggleMessage()
    }

    onEvent("listOne") {
        chosenList = lists[0]
        goto(StartState)
    }
    onEvent("listTwo") {
        chosenList = lists[1]
        goto(StartState)
    }
    onEvent("listThree") {
        chosenList = lists[2]
        goto(StartState)
    }
    onEvent("listFour") {
        chosenList = lists[3]
        goto(StartState)
    }

    onEvent("iceBreaker") {
        chosenList = lists[3]
        goto(iceBreakerState)
    }

}

val iceBreakerState: State = state(null) {
    onEntry {

        //val tempAnswer = arrayOf("Okej")
        users.setSimpleEngagementPolicy(2.0, 4)
        send(DataDelivery(3, null))
        turnObject.nextTurn(furhat)
    }
    onEvent("breakerOne") {
        furhat.say("Har du pratat med en robot förr?")
        furhat.ledStrip.solid(java.awt.Color.BLUE)
        furhat.listen(timeout = 30000)

    }
    onEvent("breakerTwo") {
        furhat.say("Tycker du mera om röd eller gul?")
        furhat.ledStrip.solid(java.awt.Color.BLUE)
        furhat.listen(timeout = 30000)
    }
    onEvent("breakerThree") {
        furhat.say("Vad är ditt favoritdjur?")
        furhat.ledStrip.solid(java.awt.Color.BLUE)
        furhat.listen(timeout = 30000)
    }
    onEvent("breakerFour") {
        furhat.say("Vad är din favoritmat?")
        furhat.ledStrip.solid(java.awt.Color.BLUE)
        furhat.listen(timeout = 30000)
    }

    onResponse {
        startProcessingSequence(furhat)
        furhat.say("Okej")
        furhat.ledStrip.solid(java.awt.Color(0,0,0))
    }

    onEvent("NextTurnButton") {
        turnObject.nextTurn(furhat)
    }

}




val WaitState: State = state(null) {
    onEntry {
        send(DataDelivery(2, null))
    }
    onEvent("resume") {
        furhat.ledStrip.solid(java.awt.Color(0,0,0))
        furhat.say(speechParameters + "Okej, tillbaks till spelet")
        goto(GameState)
    }

}

val StartState: State = state {
    systemPrompt = systemPrompt(chosenList)

    onEntry {
        furhatObject = furhat

        //uncomment if you want to run the app through text.
        //noRobot()

        // Set the inner space to a circle of 2.0 meters (outer distance becomes 2.5) and maximum users to 3
        users.setSimpleEngagementPolicy(2.0, 4)
        send(DataDelivery(1, null))
        // Skapa ett namn baserat på aktuellt datum och tid för loggningen
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")
        val formattedDateTime = currentDateTime.format(formatter)



        furhat.ledStrip.solid(java.awt.Color(0,0,0))
        furhat.say(speechParameters + currentMessage)

        furhat.ledStrip.solid(java.awt.Color.BLUE)
        furhat.listen(timeout = 14000) // Startar lyssningen direkt efter att ha talat

    }

    onResponse {
        val userInput = it.text

        // Check if the user wants to play by looking for affirmative responses
        val affirmativeResponses = listOf("ja", "jo", "jodå", "yes", "yeah")
        if (affirmativeResponses.any { userInput.contains(it, ignoreCase = true) }) {
            // User wants to play, move to the game state
            goto(GameState)  // Transition to the GameState
        } else {
            // User doesn't want to play
            furhat.ledStrip.solid(java.awt.Color(0,0,0))
            furhat.say(speechParameters + "Okej, kanske en annan gång!")  // You can customize this response
            //dialogLogger.endSession()  // Optionally, you can end the interaction or stay in the current state
        }
    }

    onResponse { // Catches everything else
        val response = didNotUnderStandResponses[didNotUnderstandIndex]

        // Increment the index, but only if it hasn’t reached the end of the list
        if (didNotUnderstandIndex < didNotUnderStandResponses.size - 1) {
            didNotUnderstandIndex += 1
        }
        furhat.say(speechParameters + response)
        furhat.ledStrip.solid(java.awt.Color.BLUE)
        furhat.listen(timeout = 14000)
    }

    onNoResponse { // Catches silence
        chatHistory.add(ChatMessage("user", "[tystnad]"))
        handleChatResponse(furhat, chatHistory, turnObject, logObject)

        furhat.ledStrip.solid(java.awt.Color.BLUE)
        furhat.listen(timeout = 14000)
    }
}

// New state for the game interaction
val GameState: State = state {

    onEvent("WaitButton") {
        furhat.ledStrip.solid(java.awt.Color(0,0,0))
        furhat.say(speechParameters + "Okej, jag pausar spelet")
        goto(WaitState)
    }

    //have to add listener since it otherwise blocks it. Would need to be run async.
    onEvent("NextTurnButton") {
        turnObject.nextTurn(furhat)
        furhat.ledStrip.solid(java.awt.Color.BLUE)
        furhat.listen(timeout = 14000)
    }

    onEntry {

        users.setSimpleEngagementPolicy(2.0, 4)
        turnObject.nextTurn(furhat)
        send(DataDelivery(1, null))
        chatHistory.add(ChatMessage("system", systemPrompt))
        handleChatResponse(furhat, chatHistory, turnObject, logObject)
    }

    onReentry {
        send(DataDelivery(1, null))
    }


    onResponse {
        startProcessingSequence(furhat)
        val userInput = it.text
        chatHistory.add(ChatMessage("user", userInput))
        handleChatResponse(furhat, chatHistory, turnObject, logObject)
    }

    onResponse { // Catches everything else
        val response = didNotUnderStandResponses[didNotUnderstandIndex]

        // Increment the index, but only if it hasn’t reached the end of the list
        if (didNotUnderstandIndex < didNotUnderStandResponses.size - 1) {
            didNotUnderstandIndex += 1
        }
        furhat.say(speechParameters + response)
        furhat.ledStrip.solid(java.awt.Color.BLUE)
        furhat.listen(timeout = 14000)
    }

    onNoResponse { // Catches silence
        chatHistory.add(ChatMessage("user", "[tystnad]"))
        handleChatResponse(furhat, chatHistory, turnObject, logObject)

        furhat.ledStrip.solid(java.awt.Color.BLUE)
        furhat.listen(timeout = 14000)
    }
}