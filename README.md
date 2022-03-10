Mobile SDE Master Exercise
=================

This is an exercise project to assign a consignment shipment address to a driver in terms of Suitability Score.

App Overview
------------

A simple UI is implemented. There is a list to show all Drivers from the data source, here JSON file is used.
* * In this exercise, both (Shipment address and driver) lists are loaded.
    These are filtered/switched in between using a text button; including an icon to differentiate their category.
    
When any item is clicked in the List, the SS business logic comes into play to assign the address/driver value accordingly.

Implemented Libraries
------------

* Android KTX
* Lifecycles
* LiveData
* Coroutines
* View Binding
* Hilt
* Flow
* Fragment
* Gson

## Project Setup
---------------

- Use Android Studio 4.0+
- Clone the project using the HTTPS/SSH etc using "Project from version control" or downloaded zip can be used to import within the Android Studio.
- Once the Project is synced with all gradle files and dependencies, the project is ready to generate .apk(s). Or can run directly to any connected device.
- terminal can also be used to generate an apk/run the app
  ```
  gradlew assembleDebug/assembleRelease
  ```
  This will generate the build in ```project_name/module_name/build/outputs/apk/``` folder

  ```
  gradlew installDebug/installRelease
  ```
  This will build an apk and install it on a running emulator or a physical device
  * * Make sure the USB Debugging option is enabled, before installing the app on a physical device.
      Under __ __ Settings > Developer Options __ __
      
- Once the apk is installed, the app icon can be used to run the app.