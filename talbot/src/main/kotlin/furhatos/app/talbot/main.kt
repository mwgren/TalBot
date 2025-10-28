package furhatos.app.talbot

import furhatos.app.talbot.flow.NoGUI
import furhatos.app.talbot.flow.StartState
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

// Main Skill class
class TalBotSkill : Skill() {
    override fun start() {
        // Start the flow with the StartState
        Flow().run(NoGUI)
    }
}

// Main function to launch the skill
fun main(args: Array<String>) {
    Skill.main(args)
}
