# Raw Football

Simple app to display football games schedules.

App uses local json files to get the data and contains a single screen where past, current and upcoming matches are shown.
The focus in not on the UI but on the architecture and the way the data is handled.
The classes are created such that Dependency Injection can be easily implemented in future.

## Architecture
App uses MVVM architecture with Repository pattern. The data is fetched from local json files and is stored in a local database. The repository is responsible for fetching the data from the database and providing it to the view model.

- The Repository simply exposes data from the json files.
- ViewModel is responsible for combining the data into form that can be displayed in the view. And handling UI states.
- The View is responsible for displaying the data and handling user interactions.

## Libraries
- Jetpack Compose: UI
- Kotlinx Serialization: JSON parsing
- Coil: Image loading

## Things avoided for reasons mentioned
- Modularization: Since there's only single screen this doesn't make sense.
- DI: Time constraints.
- Different model classes for different layers: Time constraints and simplicity.
