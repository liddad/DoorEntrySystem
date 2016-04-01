# DoorEntrySystem

##Acquiring the code

Firstly, choose a location for the code, end go to that location in the command prompt. The code can be acquired from the GitHub repository by running the git command:
```
git clone https://github.com/liddad/DoorEntrySystem.git
```

##Installing the Database

Install MySQL Workbench for Windows and run the MySQL shell.
From the MySQL shell, run the command:
```
source <location>/Database/dump.sql
```
Where *<location>* is the full pathname of the git repository on the windows system.

##Running the server
Firstly, the Eclipse IDE for java should be installed on a windows system. In this system and import the project held in the **“DoorSideSystem”** folder.
In order to run the server, open the doorServer package, right click on **Main.java** and select *“Run as -> Java application”.*
If there are any problems connecting to the server, check your firewall’s settings.
Make a note of the IP address of the server for later (this can be found by going into Windows Command Prompt and typing *“ipconfig”*)

##Running the Door Controller
The Door Controller code runs in a Raspberry Pi 2 with the Raspbian OS installed and an internet connection. The Explore-NFC by NXP board is required to use the code. This should also work with the new Raspberry Pi 3. After connecting and installing the board as described in the Explore-NFC startup guide (available at element14.com/explorenfc), retrieve the code from git.
Connect a red LED, with an appropriate resistor, between physical pin 36 (BCM 16) and ground on the pi, and a green LED, with an appropriate resistor between physical pin 38 (BCM 20) and ground. A simple pin layout diagram is found at https://pinout.xyz
Move the **“source”** folder to the location where you would like to install the door controller software and create a new folder called “build” there also. 
Open the **“source->main.c”** file, and locate the char pointer **ipAdd**. Change this to the IP address of the computer the server is running on.
In the build folder, build and compile the program by using the commands:
```
cmake ../source/
make
```
This should compile the door controller code without any errors if the Explore-NFC code installed correctly.
The code can now be run with the command:
```
sudo ./explorenfc-cardemulation –t <RoomNo>
```
Where *<RoomNo>* is the room the door controller provides entry to (in all caps)
The door controller should now be now ready and running. If it fails to connect, check the server’s firewall settings.
##Using the Android App
Import the **StrathDoorSystem** project into Android Studio.
In the **LoginActivity** of the project, change the **IP** variable to the IP address of the computer the server is now being run on.
Install the Android App to your Android phone. This step requires that NFC be a feature on the phone used.
Once the app is open, and the server is running, log in to the app using the your log in credentials held in the database, and you should be brought to the screen which allows you to use NFC to open the door.
Once you are logged in, you are free to hold your phone to any door controller and gain access
