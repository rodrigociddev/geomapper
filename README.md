# Geomapper App

This project is a Windows desktop application built with **Electron.js** and **Spring Boot**, designed for seamless image metadata management. Users can upload images, edit metadata such as title, geographic coordinates, and annotations, and export the updated files to **KML** or **KMZ** formats compatible with Google Earth. The app combines a user-friendly interface with powerful functionality, streamlining geotagging and metadata editing for professionals and hobbyists alike.

---

## How It Works

### Frontend (Electron.js)
The frontend is built with **Electron.js**, a framework that enables building cross-platform desktop applications with web technologies like HTML, CSS, and JavaScript. It also provides a bridge between the app's user interface and the system-level functionality (e.g., file operations).

- **How Axios Is Used**: Axios is a promise-based HTTP client used to make API requests between the frontend and backend. It sends metadata editing requests and fetches processed data from the backend for export.
- **Debugging Tools**: You can use Chrome DevTools to debug the frontend. Navigate to `View` → `Toggle Developer Tools` to inspect the app’s state, network requests, and more.

### Backend (Spring Boot)
The backend is implemented using **Spring Boot**, a Java framework for building robust and scalable REST APIs. It processes requests from the Electron frontend, handles file parsing and metadata updates, and manages KML/KMZ file generation.

- **Endpoints**: The backend exposes REST API endpoints for uploading images, saving metadata, and generating/exporting KML/KMZ files.
- **Data Flow**: 
  1. The Electron frontend sends requests via Axios to the backend endpoints.
  2. The backend processes the requests, updates the metadata, and returns the result to the frontend.

---

## Running the Application

### Frontend
1. Navigate to the Electron directory: `cd electron`
2. Install dependencies if not already installed: `npm install`
3. Start the application: `npm start` 
### Backend
1. Navigate to the backend directory: `cd backend`
2. Run the backend service: `./gradlew bootRun`

## Useful Resources
- [Electron.js Documentation](https://www.electronjs.org/docs/latest): Comprehensive guide for building desktop applications using Electron.js.
- [Spring Boot Documentation](https://spring.io/projects/spring-boot): Official documentation for creating robust Java-based backend services with Spring Boot.
- [Axios Documentation](https://axios-http.com/docs/intro): Detailed information on using Axios, a promise-based HTTP client for making API requests.
- [Bootstrap Documentation](https://getbootstrap.com/docs/4.1/getting-started/introduction/): Learn how to use Bootstrap to style and layout your application.
- [Guide to Building Electron + Spring Boot Apps](https://medium.com/@sgstephans/creating-a-java-electron-react-typescript-desktop-app-414e7edceed2): A tutorial on integrating Electron.js with a Spring Boot backend.


<details>
  <summary>Frontend Breakdown</summary>

  ## Hierarchy
  ### Global Variables
  - **`mediaItems`**: Array to keep track of media items.
  - **`currentSelectedMediaId`**: ID of the currently selected media item.
  - **`changesMade`**: Boolean to track if changes have been made.

  ### Functions
  - **`showDefaultMessage()`**: Displays the default message when no media is selected.
  - **`showMediaDetails()`**: Hides the default message and shows media details.
  - **`handleAddMedia(filePath)`**: Handles adding new media items.
  - **`renderMediaList()`**: Renders the list of media items in the sidebar.
  - **`selectMedia(mediaId)`**: Selects a media item and displays its details.
  - **`updateMediaDetails(mediaId, field, value)`**: Updates media details locally.
  - **`updateMediaOnBackend(mediaId)`**: Sends updated media details to the backend.
  - **`debounce(func, wait)`**: Debounces input events to limit the rate of function calls.
  - **`saveProject()`**: Saves the current project.
  - **`exportKML()`**: Exports the project as KML.
  - **`exportKMZ()`**: Exports the project as KMZ.
  - **`updateAllMediaOnBackend()`**: Updates all media details on the backend.
  - **`loadProjectData(data)`**: Loads project data from a given dataset.

  ### Event Listeners
  - **`window.electronAPI.addMedia`**: Listens for the `add-media` event to add new media.
  - **`window.electronAPI.resetApp`**: Listens for the `reset-app` event to reset the application.
  - **`document.getElementById('add-media-button').addEventListener('click')`**: Handles the Add Media button click.
  - **`window.electronAPI.deleteSelected`**: Listens for the `delete-selected` event to delete selected media items.
  - **`window.electronAPI.selectAll`**: Listens for the `select-all` event to highlight all media blocks.
  - **`window.electronAPI.unselectAll`**: Listens for the `unselect-all` event to remove highlighting from all media blocks.
  - **`document.getElementById('title').addEventListener('input')`**: Tracks changes to the title input field with debounce.
  - **`document.getElementById('latitude').addEventListener('input')`**: Tracks changes to the latitude input field with debounce.
  - **`document.getElementById('longitude').addEventListener('input')`**: Tracks changes to the longitude input field with debounce.
  - **`document.getElementById('annotations').addEventListener('input')`**: Tracks changes to the annotations input field with debounce.
  - **`window.electronAPI.openProject`**: Listens for the `open-project` event to load a new project.
  - **`window.addEventListener('error')`**: Prevents specific autofill errors from being logged.

  ---

  ## Logic

  ### Adding Media
  1. **Trigger**: When a media file is added.
  2. **Process**:
     - `handleAddMedia(filePath)` is called.
     - Media details are fetched and displayed.
     - The media item is added to `mediaItems` and the sidebar is re-rendered.

  ### Selecting Media
  1. **Trigger**: Clicking on a media block in the sidebar.
  2. **Process**:
     - `selectMedia(mediaId)` is called.
     - The selected media's details are displayed.
     - The block is highlighted.

  ### Updating Media
  1. **Trigger**: Changes to media details (title, latitude, longitude, annotations).
  2. **Process**:
     - Debounced input event listeners track the changes.
     - `updateMediaDetails(mediaId, field, value)` updates the local media item.
     - `updateMediaOnBackend(mediaId)` sends the updated details to the backend.

  ### Saving and Exporting
  1. **Process**:
     - `saveProject()`, `exportKML()`, and `exportKMZ()` call `updateAllMediaOnBackend()` to save all changes.
     - Perform their respective actions (saving or exporting).

  ### Loading Project Data
  1. **Process**:
     - `loadProjectData(data)` clears existing media items and loads new data.
     - The sidebar is re-rendered, and the first media item is selected if available.

  ### Event Handling
  1. **Process**:
     - Events from the main process (e.g., adding media, resetting the app, deleting selected media) are handled by corresponding listeners.
     - Error events related to autofill are prevented from being logged.

</details>
