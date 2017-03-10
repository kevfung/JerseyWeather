# JerseyWeather
Weather application using Jersey. Purpose is to create a mock RESTful weather service using Jersey.
It uses the Open Weather API to get weather related information. You must provide your own API
key in the gradle.properties file.

# Instructions
1) Ensure you have an Open Weather API key (http://openweathermap.org)
2) Put this key into gradle.properties file on the root directory. It needs to be formatted as API_KEY=XXX-YOUR-KEY-XXX
3) Run the Gretty task "appRun" to start a test Jetty server for the JerseyWeather service
4) Current resources:
   
Get current weather from Open Weather API, get the JSON, convert it to a POJO and call the POJO's toString()
http://localhost:8080/JerseyWeather/openweather/current 
   
Test method to see if the service is running. It prints out the resource being used and any query parameters that you pass it   
http://localhost:8080/JerseyWeather/weather/current?myParam1=value1&myParam2=value2