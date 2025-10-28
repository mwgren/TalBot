package furhatos.app.talbot

import furhatos.flow.kotlin.Furhat
import furhatos.flow.kotlin.users

class TurnManager {
    private var currentTurnIndex = 0
    private var sortedUserList: List<User> = listOf()  // Stores sorted users for the entire game
    private var persistentUserList: List<User> = listOf()  // Fixed list from the first call (this list doesn't change)

    // Data class to hold user information
    data class User(val id: String, val persistentUserId: String, val location: Location)

    // Data class for initial persistent user information (used for reference)
    data class PersistentUser(val persistentUserId: String, val originalId: String)

    // Data class for location information
    data class Location(val x: Double, val y: Double, val z: Double)

    // Function to initialize or update the list of users with their IDs and locations
    fun updateUsers(furhat: Furhat) {
        // If persistentUserList is empty (first call), populate it with persistent user data
        if (persistentUserList.isEmpty()) {
            // Store the initial list of persistentUserIds and their original ids (this list will remain constant)
            persistentUserList = furhat.users.all.toList().map { user ->
                User(
                    id = user.id,
                    persistentUserId = user.persistentData.persistentUserId ?: "unknown",
                    location = Location(
                        x = user.head.location.x,
                        y = user.head.location.y,
                        z = user.head.location.z
                    )
                )
            }
                .sortedBy { it.location.z }  // Sort by z value (ascending)
                .take(3)  // Take the first 3 users with the lowest z values
                .sortedBy { it.location.x }  // Then sort them by x-coordinate (ascending)
                .toList()  // Store the final order of users for the game

            println("Initialized Persistent User List: $persistentUserList")
        }
        currentTurnIndex = 0
    }

    // Function to decide the next user to attend based on the initial turn sequence (persistentUserId reference)
    fun nextTurn(furhat: Furhat): String? {
        val tempUsers = furhat.users.all.toList().map { user ->
            User(
                id = user.id,
                persistentUserId = user.persistentData.persistentUserId ?: "unknown",
                location = Location(
                    x = user.head.location.x,
                    y = user.head.location.y,
                    z = user.head.location.z
                )
            )
        }
        //doesn't change even if I remove a user, which is strange
        //println("TEMP PRINTED" + tempUsers)
        if (persistentUserList.isEmpty() || tempUsers.isEmpty()) return null

        // Keep searching until it can return a match
        while (true) {
            val persistentUserIdToAttend = persistentUserList[currentTurnIndex % persistentUserList.size].persistentUserId

            println("Checking persistentUserId: $persistentUserIdToAttend (Index: $currentTurnIndex)")


            // Search for a user with the current persistentUserId in the tempUsers list
            val selectedUser = tempUsers.find { it.persistentUserId == persistentUserIdToAttend }

            currentTurnIndex++ // Increment turn index for the next loop

            // If a match is found, attend to the user and return their ID
            selectedUser?.let {
                furhat.attend(it.id)
                println("Attending user with ID: ${it.id} (Persistent ID: ${it.persistentUserId})")
                return it.id
            }
        }
    }
}
