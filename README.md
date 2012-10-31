AI-Simulation
=============

This program places 10-20 AI characters on a 10x10 grid and allows them to move freely within it. If any two characters should meet, they will undergo an interaction based on their own personal attributes.


It started off as a larger text-based adventure game that I was using as a learning project, but it split off from there as a project of its own. I plan on reimplimenting this back into the text based adventure game.

The main method is held in the AI class. It takes a few kinds of input:
map         -   prints out the map.
num         -   prints out the number of characters in existence.
room num    -   prints out how many people are in any room at the moment.
attendence  -   prints out the names of all the characters in existence.
add         -   adds another random character to the mix
remove X    -   removes a character specified by name.
(hit enter) -   moves the simulation forward a step. All characters move, interact, and use their "turn" method (right now only deals with money and hunger).

The rest of the work happens behind the scenes.
Every "turn," all characters will either move to an adjacent space on the grid or stay where they are. They will also receive a bonus 30% of their wealth, and subtract from that the cost of living. Eventually, I plan on having starvation as a factor in interactions and allow for death, but until I get the economy right and reproduction in place, I don't think it's fair or logical to kill any characters based on hunger.
If any Mover moves into a room that already has a Mover in it, the two will undergo an interaction. This means that the program will take the base attributes of one character, compare it to the other's then add that to their base personality to form how the character feels about the other. This comparison is stored in a LinkedList in the Mover class for future reference between the two, and allows for changed actions based on how well they get along in their interactions. Right now, referencing the previous interaction is a somewhat important part of the method, but I plan on adding a lot more along with more attributes. I'll have each of the interactions go back and affect the moods of each character, and their basic inclinations as to how to behave when interacting with this one particular person. I just wanted to get the framework in (which I did) to see if I could. I didn't want to bite off more than I could chew. Now I'll be able to work my way up to more complex interactions. Eventually, I hope that this will allow for really deep and intimate relationships, whether they are love or hatred or somewhere in between.
Currently there are only 6 possible actions and 22 possible reactions, but I have mapped many more that involve attributes that I have not yet integrated (stubborness, sense of humor, eccentricity, etc.). As I put in more attributes, more nuanced versions of the general actions I use here will become available (conning someone out of money, or getting into an actual fight), as well as a few more complex ones like partnering up and travelling together for a while. And, of course, every action will have several possible reactions, making a huge blend of possible interactions.

Next on my list of things to do is to start integrating female characters, and the interactions they'd have with each other and with male characters. I've begun mapping out the interactions and have a list of names for them. This involves introducing lust and romantic love as attributes, and new interactions will include everything from flirtation to marriage to reproduction to rejection.
Then I'll start adding more attributes across the board, allowing for more defined interactions, and start inputting death, the need for people with high morality to save the dying, and low morality to extort them.
I'll also start putting in items. I have a working items system in my text-based adventure, I just have to impliment it here, assign the items worth, and write an interaction for trade. Morality and Intellect can decide on how well any given item sells (high intellect will get be better at bargaining, low morality won't mind ripping people off).


Known bugs:
The balance is tipped towards positive interactions. Hopefully adding more personality traits will help straighten that out, but for now, I'm looking for a way to balance the scale a little more.

Occasionally two characters interact twice in one move. This used to be much more of a problem, but now it's very infrequent. The bulk of the problem was in the for loop in Mover.Interact(Mover), and I think the rest of the problem lies in the same place.

