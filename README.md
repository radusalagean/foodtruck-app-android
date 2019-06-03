# Foodtruck Client App (Android)
This is a demonstrative client app, built around the **foodtruck api**, available and documented [here](https://github.com/radusalagean/foodtruck-api).

## Screenshots
TODO

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

If you want to get access to the **Play Store** listing, please send me an email at [busytrack@gmail.com](mailto://busytrack@gmail.com) with the email addess associated to your Google account that you are using on your Android device in order to add you to the **internal testing** channel.

### Debug vs. Release?
The Debug version has additional _Logcat_ logs and the [Stetho](http://facebook.github.io/stetho/) library integrated for debugging purposes.

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
TODO

## Libraries
TODO

## License
Apache License 2.0, see the [LICENSE](LICENSE) file for details.
