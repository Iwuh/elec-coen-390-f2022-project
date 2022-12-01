# BiblioBuddy

Term project for ELEC 390 (Electrical Engineering Product Design Project) / COEN 390 (Computer Engineering Product Design Project)

Concordia University, Fall 2022

## What is it?
BiblioBuddy is a solution that allows library users to view real-time occupancy levels of the different Concordia Library reading rooms as well as the average noise level in those rooms.
Sensors installed on doors track individuals entering or leaving rooms, and sensors installed in the rooms measure ambient noise levels.
The app then displays these values.
This app also leverages the Concordia [Open Data API](https://www.concordia.ca/web/open-data.html) to provide information on opening hours, available computer workstations, and more.
The app also allows librarians to broadcast announcements to users.

## Contributors
* [Matthew Faigan](https://github.com/Iwuh)
* [Matthew Bergeron](https://github.com/mberg-ConU)
* [Ambel Ruli](https://github.com/amb31)
* [Abdullah Tenveer](https://github.com/mabdullaht1)
* [Harkirat Kaur](https://github.com/red346)
* [Abdul Hadi Elaarag](https://github.com/elaarag)

## Directory Structure
* `/app`: The Android application
* `/Controller`: The Arduino sketches for the WEMOS D1 R1 microcontroller
* `/simulation`: The occupancy algorithm simulation developed during Sprint 1
