# Weather SDK
Web service for obtaining weather information in a selected city

## Installation
Oracle openjdk-21 must be pre-installed.  
Copy and, if necessary, unzip the application source files to any location on your Linux PC.  
To install the application, you should call the application script in the root of the package.  
The first time the script is called, it should be done in the following sequence:  

- **./application init** - init working directory and download latest source code  
- **./application build** - run JUnit tests to check app health (-skipTests arg to skip tests) and build jar  
- **./application up** - launch application  

The application files of latest version will be installed in the directory /home/USER/Workspace/weather-sdk .  

If the application is already installed and you want to update to the latest version, the script call sequence is as follows:
- **./application clean** - clean working directory
- **./application init**  
- **./application build**  
- **./application up**  

During operation, an HTTP server is launched on the default port 8080.  
There are two ways to access the http server:
- **using a browser** at http://YOUR_HOST:8080/weather
- **using REST Client** at http://YOUR_HOST:8080/api/v1/weather

## Usage

### Using a browser

Go to the address http://YOUR_HOST:8080/weather and see the start page:  

![01_MVC_start_page.png](images%2F01_MVC_start_page.png)  

Enter your OpenWeather API Key and city you want to know the weather.  
Click Send request button and the information should appear in the field below:

![01_MVC_result.png](images%2F01_MVC_result.png)

If errors occur, the following messages appear:

![01_MVC_error401.png](images%2F01_MVC_error401.png)  

![01_MVC_error404.png](images%2F01_MVC_error404.png)  

![01_MVC_errorValidation.png](images%2F01_MVC_errorValidation.png)

### Using REST Client  
To successfully obtain information, you must send a GET request to the endpoint http://YOUR_HOST:8080/api/v1/weather.   
Into the request body, you must pass Payload(appid, city) in JSON format as shown in the screenshot.  
If you did everything correctly, you will receive a response in JSON format with status 200.  

![02_result.png](images%2F02_result.png)

If you make a mistake when specifying the 32-character appid, you will receive a response with the status 401:  

![02_error401.png](images%2F02_error401.png)  

If you specified a non-existent city, you will receive a response with status 404:  

![02_error404.png](images%2F02_error404.png)

If you specify an appid that is not 32 characters long and an empty city field, you will receive a complex error message with status 400:  

![02_error400Validation.png](images%2F02_error400Validation.png)

## Implementation details

The application is written in Java using Spring Boot modules, the Gradle build tool and some other libraries.

### Request service

Requests from the application are made over the https protocol using the Spring DefaultRestClient class.  

### View

The ability to interact with the service via a browser is implemented using the Thymeleaf template engine

### Cache

The cache of requested data is implemented using the Guava library and in-memory caching support from Spring.  
The data of the last 10 requests is cached.  
The TTL of each cached value is 10 min.

### Exception handling

For MVC controller, possible errors are caught and handled directly in the WeatherController class.  
For REST controller, all errors are centrally handled in the RestControllerExceptionHandler class.

### Test
Testing was performed for the REST controller.  
The WireMock library was used to create a virtual request server simulating the operation of OpenWeather.  
Interaction with the own REST controller is simulated using Spring MockMvc.  

