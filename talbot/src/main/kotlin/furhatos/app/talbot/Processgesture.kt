package furhatos.app.talbot
import furhatos.gestures.Gestures
import furhatos.flow.kotlin.*

fun startProcessingSequence(furhat: Furhat) {
    furhat.ledStrip.solid(java.awt.Color(0,0,0))
    //furhat.gesture(Gestures.Blink(duration = 0.5))  // Snabb blinkning för att visa uppmärksamhet
    furhat.gesture(Gestures.Smile(duration = 2.5))  // Tydligare "tänker"-gest
    furhat.gesture(Gestures.Nod (duration = 0.5))
    //furhat.gesture(Gestures.Thoughtful(duration = 1.0))  // Lätt rynkad panna som uttrycker koncentration
    //furhat.gesture(Gestures.GazeAway(duration = 1.0))  // Flytta blicken en aning för att skapa intryck av reflektion

    //furhat.gesture(Gestures.(duration = 0.8))  // Blinkning igen för variation
}