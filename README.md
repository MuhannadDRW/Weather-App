# Weather Application

This is a JavaFX-based Weather Application that allows users to check current weather information for a specified city. The application connects to the OpenWeather API to fetch weather data and provides an option for viewing previous searches.

## Features

- **City Weather Search**: Users can enter a city name to view details such as temperature, humidity, wind speed, and weather conditions.
- **Unit System Toggle**: Users can choose between Metric (Celsius, m/s) and American (Fahrenheit, mph) units.
- **Search History**: A history of recent searches is accessible within the application, showing details like date, city name, temperature, humidity, and weather state.
- **Error Handling**: The application displays custom error messages if the city name is invalid or if there's an issue connecting to the weather service.

## Project Structure

- `HelloApplication.java`: The main application class that sets up the JavaFX UI.
- `Weather.java`: Model class representing weather data and managing the search history.
- `GetAPI.java`: Handles communication with the OpenWeather API, sending requests and parsing responses.

## Dependencies

- **JavaFX**: For creating the graphical interface.
- **HttpClient**: For making HTTP requests to the OpenWeather API.
- **OpenWeather API Key**: Required to fetch weather data.

## Prerequisites

- **Java Development Kit (JDK) 11 or higher**
- **JavaFX SDK**: Ensure JavaFX libraries are configured with your IDE.


## Running the Application

1 - Open the project in your preferred IDE.

2 - Run HelloApplication.java as a Java Application.

3 - The application window will open, allowing you to:
- Enter a city name and get weather data.
- Toggle between the American and metric systems.
- View the search history.


## Example Usage
1. Enter City: Type a valid city name (e.g., "London").
2. Toggle Units: Check or uncheck "American System" to switch between units.
3. Click "Search": The application displays current weather data for the city.
4. History: Click "History Search" to view a log of past searches.


## Class Descriptions

### HelloApplication.java
The main application class responsible for initializing the user interface. It provides functionalities to:

- Search for weather data.
- Display the current weather information.
- Show the history of previous searches.


### Weather.java
A model class representing weather data, including:

- City name
- Temperature
- Humidity
- Wind speed
- Weather description
- System unit used (metric or American)

### GetAPI.java
Handles API requests to the OpenWeather API and parses the response. Key functions include:

- ```request(String cityName, CheckBox cb_usSys)```: Sends a request to the API and parses the JSON response to retrieve
weather data for the specified city.


## Error Handling
The application provides alerts in case of:

- Invalid city name: Prompts the user to enter a valid city name.
- Server error: Alerts the user if the city is not found or the request fails.

