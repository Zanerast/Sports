package com.astrick.sports.data

import android.os.Parcelable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize

internal class ContentRepository(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    
    suspend fun getFeaturedSports(): List<Sport> = withContext(dispatcher) {
        delay(5000)
        Sport.createMockedSports()
    }
    
}

@Parcelize
internal data class Sport(
    val name: String,
    val description: String
): Parcelable {
    
    companion object {
        
        fun createMockedSports(): List<Sport> =
            listOf(
                Sport(
                    "Cycling",
                    "Cycling, also called bicycling or biking, is the use of bicycles for transport, recreation, exercise or sport. People engaged in cycling are referred to as \"cyclists\", \"bicyclists\", or \"bikers\". Apart from two-wheeled bicycles, \"cycling\" also includes the riding of unicycles, tricycles, quadricycles, recumbent and similar human-powered vehicles (HPVs)."
                ),
                Sport(
                    "Association football",
                    "Association football, more commonly known as simply football or soccer,[a] is a team sport played with a spherical ball between two teams of 11 players. It is played by approximately 250 million players in over 200 countries and dependencies, making it the world's most popular sport."
                ),
                Sport(
                    "Hockey",
                    "Hockey is a term used to denote various types of both summer and winter team sports which originated on either an outdoor field, sheet of ice, or dry floor such as in a gymnasium.\n" +
                            "There are many types of hockey. Some games make the use of skates, either wheeled, or bladed while others do not. In order to help make the distinction between these various games, the word \"hockey\" is often preceded by another word i.e. \"field hockey\", \"ice hockey\", \"roller hockey\", \"rink hockey\", or \"floor hockey\". "
                ),
                Sport(
                    "Basketball",
                    "Basketball is a team sport in which two teams, most commonly of five players each, opposing one another on a rectangular court, compete with the primary objective of shooting a basketball (approximately 9.4 inches (24 cm) in diameter) through the defender's hoop (a basket 18 inches (46 cm) in diameter mounted 10 feet (3.048 m) high to a backboard at each end of the court) while preventing the opposing team from shooting through their own hoop."
                ),
                Sport(
                    "Rugby",
                    "Rugby football is a collective name for the family of team sports of rugby union and rugby league, as well as the earlier forms of football from which both games, as well as Australian rules football and gridiron football, evolved. "
                ),
                Sport(
                    "Cricket",
                    "Cricket is a bat-and-ball game played between two teams of eleven players each on a field at the centre of which is a 22-yard (20-metre) pitch with a wicket at each end, each comprising two bails balanced on three stumps. The game proceeds when a player on the fielding team, called the bowler, \"bowls\" (propels) the ball from one end of the pitch towards the wicket at the other end, with an \"over\" being completed once they have legally done so six times."
                ),
                Sport(
                    "Boxing",
                    "Boxing (also known as \"western boxing\" or \"pugilism\") is a combat sport in which two people, usually wearing protective gloves and other protective equipment such as hand wraps and mouthguards, throw punches at each other for a predetermined amount of time in a boxing ring. "
                ),
                Sport(
                    "Rowing",
                    "Rowing is the act of propelling a boat using the motion of oars in the water by displacing water to propel the boat forward."
                ),
                Sport(
                    "Volleyball",
                    "Volleyball is a team sport in which two teams of six players are separated by a net. Each team tries to score points by grounding a ball on the other team's court under organized rules."
                )
            )
    }
    
}
