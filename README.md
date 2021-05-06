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



# Technology:

- In order to keep best practises, clean code, and keeping the logic intact, i implemented a combination of techonologies like:
- MVP for the presentation of the layers, for keeping the structure of the architecture well organized, and due to the excellent mapping that is achieved between the presenter and   the views.

- Moshi library for a concise mapping of the Json structure

-Dagger for dependency injections, helping you to reduce the amount of code and implementations.

-RxKotlin and RxJava, when we pass resources like "the Event data class as Observables", we make the observers work in that speficit moment, excuting a more efficient use of resources.

-As methology, Modulation played a big role in the whole development of this software, due to the interaction between Webcoket extracted data, the interactor and the 2 feature,
the organization of these components in separate modules kept the main thread fresh and healthy. Framework MVP again, is the key of the communication of the views and the presenters.


# Correctness:

The task was solved in its entirely, due to the clear organization of the task itself, and the natural flow of these type of apps related to transportation

As soon as the app fires, the Websocket fetches the data and:

1 -Initialize the vehicle location.
  -Shows the pickup, drop-off, and some intermediate stops markers.

  -Shows the status on the screen in UI components.

  -Shows the pickup and drop-off addresses on the screen in UI components.
  
  2- the vehicle bearing is animated at every new update
  
  3- with the new stops added to our roadmap, new updates in our UI components will be shown, and new makers place on the map, which contain titles and more extra info as bonus.

  4- when the vehicle reaches its final destination,the software identifies this action, and clears all the Markers, and some Polylines i added as an extra feature. 
  
  # What is missing?

  - although i implemented Polylines to show a reference when it comes down drawing a route of our Vehicle, they are not following the steps and legs of the map, due to de Denied    Access to the Api of Origin, i researched a lot about it, and i am going to continue with this, because i found it out many other features which will help in develping more        functionability for this software.
  
  - Unit etsting

  # How to use?
  1- Clone project from GitHub, using Android studio.
  2- Go to google_maps_api.xml, check if the API key is still working.
  3- if it doesnt, please go to https://console.cloud.google.com/apis/dashboard and follow the instructions in the google_maps_api.xm file, about how to generate a new API key.
  4- once generate it, replace the old one in google_maps_api.xm.


