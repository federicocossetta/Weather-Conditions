# Weather-Conditions -1.0
This is a simple app to show weather.
You can either [Dowload latest build](https://github.com/federicocossetta/Weather-Conditions/raw/main/weather-forecast-release.apk) or build the app

## Building the app
To build the project you have to specify in file **build_config**
* **API_KEY**  the API key obtained from [openweathermap](https://openweathermap.org/)
* **KEYSTORE_PATH** path of the keystore
* **KEYSTORE_ALIAS** alias of the keystore
* **KEYSTORE_PASSWORD** password of the keystore
** Note: I put a default keystore to build app without needing

## Libarires used
* Koin for DI
* Uniflow for data flow
* Retrofit for server comunication
* Moshi for Json deserialziation
* Android navigation for UI navigation
* Glide for image loading


## Short video of the app
[Download video](https://github.com/federicocossetta/Weather-Conditions/raw/main/app_video.mp4)
## TODO
* Implement android room to load data cached