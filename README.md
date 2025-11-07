# TalBot: An LLM-based robot application for Children with Language Vulnerabilities

<p align="center">
<img align="center" alt="pic1" src="https://raw.githubusercontent.com/mwgren/TalBot/refs/heads/main/README-pics/pic1.png">
</p>

## About

This robot application is designed to play the language game alias with 3 children simultaneously. It is designed to play the game with children with language vulnerabilities who may have problems with for example pronunciation — the applications design reflects this.

In brief, the application starts by providing the player its right with a clue. The turn proceeds until the child has guessed the correct word; after this, it turns to the player to next player, to its left. The application uses an LLM for providing adaptive clues, emotionally congruent responses and robots gestures and for deeming if the guessed word is correct or close enough to count as correct — making for up for poor ASR.
 
## Setup
The application is Kotlin- and JS-based and runs on the robotic platform Furhat. The easiest way is to build the project is to open the build.gradle file in IntelliJ as a project; it should build it automatically.

For the GUI-side, you need to have node installed and navigate to the [assets folder](https://github.com/mwgren/TalBot/tree/main/talbot/assets/exampleGui) and run:

```bash
npm install
```

You will also need to create an .env file in the root of the project with and provide your OpenAI key in the following format:

```kotlin
OPENAI_API_KEY=<key>
```

After running the project on the robot, it will halt until you open the GUI interface, which is easiest to reach through connecting to the Furhat robot's webserver.

## Prompt

The system is largely based on prompting the LLM. The prompt can be found [here](https://github.com/mwgren/TalBot/blob/main/talbot/src/main/kotlin/furhatos/app/talbot/promptHandler.kt)


## License

The code is licensed through the [MIT license](https://github.com/mwgren/TalBot/blob/main/LICENSE).
