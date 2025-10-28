package furhatos.app.furgui

import furhatos.app.talbot.flow.GameState
import furhatos.app.talbot.flow.StartState
import furhatos.event.Event

/*
val PORT = 1234 // GUI Port
val SPEECH_DONE = "SpeechDone"
*/

//Don't move this from this file
class DataDelivery(
    val screen :Int,
    val text :String?
) : Event()
