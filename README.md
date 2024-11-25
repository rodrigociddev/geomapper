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
