# D2D_JonathanCheli

This is my solution for the android mobile code challenge. A booking tool for rides in the city of Berlin, which is created with the purpose of helping people to have the most comfortable experience when it comes down booking transportation during the gaps that every public transport network have. 

# Project

Fed by a websocket created for the Door2Door backend team, we have the scenario where we can simulate a reservation of a vehicle, and i implemented solutions for all the requirements for this challenge:

1- shows the user the live location of their assigned vehicle on a map.
2- hows the ride's current status (waiting, in the vehicle, dropped off) in a UI component.
3- shows the ride's pickup and drop-off addresses in a UI component.

PLUS EXTRAS:

4- Visualise on the map the intermediate stops that the vehicle will make between the pickup and dropoff locations. Note that these stops may change over the duration of the journey.
5- Visualise the navigation bearing of the vehicle to show which direction the vehicle is currently driving in.



# Technology: Which libraries or approaches are used? Do they make sense for the task? Justify why you've decided to use those technologies to solve the code challenge:

- In order to keep best practises, clean code, and keeping the logic intact, i implemented a combination of techonologies like:
- MVP for the presentation of the layers, for keeping the structure of the architecture well organized, and due to the excellent mapping that is achieved between the presenter and   the views.

- Moshi library for a concise mapping of the Json structure

-Dagger for dependency injections, helping you to reduce the amount of code and implementations.

-RxKotlin and RxJava, when we pass resources like "the Event data class as Observables", we make the observers work in that speficit moment, excuting a more efficient use of resources.

-As methology, Modulation played a big role in the whole development of this software, due to the interaction between Webcoket extracted data, the interactor and the 2 feature,
the organization of these components in separate modules kept the main thread fresh and healthy. Framework MVP again, is the key of the communication of the views and the presenters.


# Correctness: Is the task solved? Does the app handle properly all the events sent from the WebSockets enpoint? If there is anything missing, is the reason why it is missing documented?


