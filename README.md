
# SmartPool

SmartPool is my Android application I will be developing as part of my Final Year Project at TU Dublin.
The aim of the project is to help pool players improve their performance by analysing their shots and
providing personalised feedback.

## Checkpoint 1

The first checkpoint focuses on establishing a working Android application and its initial tech stack.

### Completed Features
- Welcome screen
- User registration
- User login and logout
- Firebase Authentication
- Cloud Firestore user profiles
- Personalised home screen
- Basic dashboard for future features

## Backend
After Checkpoint one I started working on the Python backedn using FastAPI

### Completed Features
-Set up a Python Backend using FastApi
-Created welcome page and health check endpoints
-Connected Android to the Backend
-Added a simple button to check if Backend is working
-Tested the connection succesfully, (only tested on my phone so far)

At this point the Backend is running locally on my computer, and my phone connects through USB. 


## Technologies Used
- Java
- XML
- Android Studio
- Firebase Authentication
- Cloud Firestore
- Python 
- FastAPI

## Running the Application

### Android Application
1. Open the project in Android Studio.
2. Configure a Firebase project.
3. Add google-services.json to the app folder.
4. Enable Email/Password Authentication and configure Firestore.
5. Sync Gradle and run the application.

### Python Backend
1. Open the backend folder in VS Code.
2. In terminal start the backend using: .\.venv\Scripts\python.exe -m uvicorn main:app --reload
3. Use http://127.0.0.1:8000/health to check that the backend is running

If connecting an Android phone, ADB reverse port must be set to port 8000
Run this command on Android Studio terminal: & "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe" reverse tcp:8000 tcp:8000

## Next Steps
- Add an option to take or upload pictures of a pool table
- Start working on the pool table and ball detection
- Work on the pool analysis and shot recommendations
- Add Training history and progress tracking 

## Project Status
Checkpoint 1 - Android application and Firebase integration completed. 

Python Backend - The initial backend was created and connected to the Android application.

Next stage - Begin working on pool table and ball detection. 