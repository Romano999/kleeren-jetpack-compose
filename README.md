# kleeren-jetpack-compose
This repository contains my IKPMD project made with Jetpack Compose.

## Setup
In order to run this project you need to do the following:
  1. Clone this repository.
  2. Add Firebase to the project (https://firebase.google.com/docs/android/setup).
  3. Go to Firebase Authentication and activate it, choose 'Email/Password' as (native) provider.
  4. Go to Firestore Database and make 4 <strong>EMPTY</strong> collections with the following names (arrivals, products, sales, users).
  5. Uncomment the commented code in app/src/main/java/nl/romano/kleeren/MainActivity.kt
  6. Your app should now work!
