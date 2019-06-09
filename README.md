[![Build Status](https://travis-ci.org/radusalagean/foodtruck-app-android.svg?branch=master)](https://travis-ci.org/radusalagean/foodtruck-app-android)

# Foodtruck Client App (Android)
![app_icon](FoodtruckClient/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png)

This is a demonstrative client app, built around the **foodtruck api**, available and documented [here](https://github.com/radusalagean/foodtruck-api).

## Screenshots
![screenshot](https://lh3.googleusercontent.com/g0hnDiKiK5eAzKfutGH2VKMeV6btyEFPng5pj-ebzkKB2pyk4ixRegBosoWfkJtGJUA=w720-h310-rw)
![screenshot](https://lh3.googleusercontent.com/efZyOAgAQ7wr19zL7NPXFmP8Qx8dah27mQA_383qJ_ylcj-UTjpYWGM-upeZk-Us9g=w720-h310-rw)
![screenshot](https://lh3.googleusercontent.com/4LaI9eBsxCKunAd9yxyPU2Yv1_Sd2Q8qnFWcwUyFjUap-KIRsUPlFYBIA6exOCom_LbO=w720-h310-rw)
![screenshot](https://lh3.googleusercontent.com/mq9g9NyAmN3yrWOf6lBl6rUITjnTTXhqSuVLVPKHpK5C9VEKNzxvy7yosxTlHKbdITw=w720-h310-rw)

## Features
- Access a centralized network of food trucks
- Submit food trucks that will be visible to all users of the app
- Personalize submissions with specific food types available, current truck location on the map and an optional image
- Personalize your profile with a custom profile image
- Post reviews for food trucks owned by other users
- Viewing available food trucks and profiles does not require authentication

## Using the app
The **Google Maps API** release key is restricted to the apk signed by Google Play (_App Signing Certificate_).
Running a release build variant signed with my local intermediary certificate (aka. _App Upload Certificate_) will result in the maps not being displayed, so there is no point in providing a release-variant apk, other than the final one signed by Google and only accessible through the **Google Play store listing**.

The **debug apk**, however, is signed with the usual _Android Debug Certificate_ and the Google Maps API key for the debug version points to that certificate's _SHA-1_.

As a result, if we want to run the **release** variant properly, only the one from the **Play Store** listing will work.
If we want to run the **debug** variant, the apk provided **[here](https://github.com/radusalagean/foodtruck-app-android/releases)** should work just fine.

If you want to get access to the **Play Store** listing, please send me an email to [busytrack@gmail.com](mailto://busytrack@gmail.com) with the email addess linked to your Google account that you are using on your Android device in order to add you to the **internal testing** channel. Due to the demonstrative nature of this app, it is **not meant for public production use by any means**, thus public access is restricted from the Play Store.

### Debug vs. Release?
The Debug version has additional _Logcat_ logs and the [Stetho](http://facebook.github.io/stetho/) library enabled for debugging purposes.

## Building the project
1. Clone the repo
2. Create a **Google Maps API Key** by following the official [guide](https://developers.google.com/maps/documentation/android-sdk/get-api-key)
3. Go to your cloned repo, path `foodtruck-app-android/FoodtruckClient/app/src/debug/res/values` (create missing directories) and create a new file called `api_keys.xml`.
  The content of the file should look like this:
  ```xml
  <resources>
    <string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">YOUR API KEY</string>
  </resources>
  ```
4. Paste the API key generated at step 2 in the file file created earlier
5. Open the project in Android Studio
6. Build and run

## Architecture

### Overview
The project is built around the `MVP` pattern. The following diagram displays the basic Model-View-Presenter structure.

![fig. 1](https://i.imgur.com/KrnNXd8.png)

### Roles
Some roles I chose for each interface are listed below:
- **`View`**
  - Represents Android Fragments
  - Handles Android-specific tasks, such as Fragment lifecycle, requesting and receiving permission results, etc.
  - Inflates and manages Android views and their behavior
  - Updates Android views to display data received from the `Presenter`
- **`Presenter`**
  - Subscribes to observables provided by the `Model`
  - Processes received data in order to be displayed by the `View`
  - Acts as a 'middle-man' between the `View` and the `Model`
  - Decides whether to provide cached or fresh data to the view, based on cached data availability
  - Interacts with `Manager` classes
- **`Model`**
  - Provides the path between the app and the Backend through `RxJava` observables that the `Presenter` can subscribe to
  - Uses `RxJava` operators to combine data
  - Provides access to cached View Models
  - Interacts with `Repository` classes

### Dependency Scoping
Dependencies are managed with the **Dagger2** library. There are 3 components and 3 corresponding scopes defined:
- `ApplicationComponent` <-> `@ApplicationScope`
- `ActivityComponent` <-> `@ActivityScope`
- `ServiceComponent` <-> `@ServiceScope`

The defined scopes are linked to each component's lifetime. For example, a dependency provided with `@ApplicationScope` annotation will only be instantiated once per application lifetime and the instance will be cached and reused.

A dependency provided with the `@ActivityScope` or `@ServiceScope` annotation will only be instantiated once per that Activity / Service's lifetime, cached and then reused throughout.

An `@ApplicationScope` dependency will outlive any `@ActivityScope` or `@ServiceScope` dependency.

### View Models

In order to provide a seamless user experience on events like screen rotation, classes representing the data displayed on the screen are used. Those are called `ViewModel` classes and each screen type has its own `ViewModelRepository` class where `ViewModel` classes are kept for restoration purposes. Those view model repositories outlive the Activity lifetime, since they are provided with an `@ApplicationScope` annotation.

In order to associate a `ViewModel` class to its corresponding, original `Fragment`, each Fragment has a random `UUID` generated and associated with it when the fragment is first created, through the `newInstance()` method. That `UUID` is stored in the fragment arguments as a `Bundle`, which can be restored later, when the parent `Activity` is destroyed and the re-created, thus creating a one on one association with the view model class.

Every time a fragment is popped off the backstack, the view model repositories are checked and the view model classes that are obsolete are dereferenced, awaiting garbage collection.

## Libraries
- [Google Maps Android SDK](https://developers.google.com/maps/documentation/android-sdk/intro) - Display and select food truck location
- [Retrofit](https://square.github.io/retrofit/) - Networking
- [RxJava](https://github.com/ReactiveX/RxJava) - Concurrency
- [Guava](https://github.com/google/guava) - Google Core Libray
- [Gson](https://github.com/google/gson) - Json serialization / deserialization
- [Butterknife](https://jakewharton.github.io/butterknife/) - Android View Binding
- [Dagger2](https://github.com/google/dagger) - Dependency Injection
- [Glide](https://github.com/bumptech/glide) - Image Loading
- [Flexbox Layout](https://github.com/google/flexbox-layout) - CSS Flexible Box Layout Module adaptation for Android
- [Stetho](http://facebook.github.io/stetho/) - Debugging
- [Timber](https://github.com/JakeWharton/timber) - Logging (Android Logcat wrapper)

## License
Apache License 2.0, see the [LICENSE](LICENSE) file for details.
