# Occupancy Level Tracking - Simulation

This project simulates the controller and "triggers" its sensors through code.

## Methodology

1. If the inner sensor and outer sensor are triggered in close succession, a person has left.
2. If the outer sensor and inner sensor are triggered in close succession, a person has entered.
3. If a sensor is triggered and the other one is not triggered in close succession, it was a false positive.
4. We enforce a minimum delay between sensor triggers. Why? If two individuals are entering and leaving simultaneously, we don't want the leaver's trigger of the inner sensor to match with the enterer's trigger of the outer sensor.

## Usage

1. Navigate to `/inputs`
2. Open the desired file and copy the contents to `input.txt`
3. Build and run the executable

## Inputs

Each input file has the following structure:
* The first line indicates how many sensor triggers are in the file.
* The second line indicates the expected net occupancy change at the end of the simulation.
* Each following line represents a sensor trigger. The first element is how many milliseconds to wait after triggering before the next event, and the second is which sensor to trigger (**o**uter or **i**nner).

The following input files are included in `/inputs`:
* `simple_test.txt`: Sample made for ensuring basic functionality
* `narrow_door.txt`: People passing through a narrow doorway, constructed using footage from https://www.youtube.com/watch?v=MSmtkpvKmnI
* `wide_door.txt`: People passing through a wide doorway, constructed using footage from https://www.youtube.com/watch?v=Rf_ixb9i1Tc
* `open_space.txt`: People walking through an open space, constructed using footage from https://www.youtube.com/watch?v=ZkAkdH1aLRU

## Algorithm

```
let S1 := the sensor on the outside of the room
let S2 := the sensor on the inside of the room

let S1_triggers := []
let S2_triggers := []
let event_history := []

when (S1 or S2 is triggered):
    if (S1 was triggered):
        let result := S2_triggers.search(first trigger within 2 seconds of current_time but more than 0.5 seconds old)
        if (result found):
            // S2 was triggered right before S1, so this is a person leaving
            S2_triggers.remove(result)
            event_history.add(current_time, leave_event)
        else:
            // S2 was not triggered, so this may be a person entering
            S1_triggers.add(current_time)
    
    if (S2 was triggered):
        let result := S1_triggers.search(first trigger within 2 seconds of current_time but more than 0.5 seconds old)
        if (result found):
            // S1 was triggered right before S2, so this is a person entering
            S1_triggers.remove(result)
            event_history.add(current_time, enter_event)
        else:
            // S1 was not triggered, so this may be a person leaving
            S2_triggers.add(current_time)
            
    // Discard old events, assuming they were false positives (door movement, etc)
    S1_triggers.remove_all(where timestamp is more than 2 seconds ago)
    S2_triggers.remove_all(where timestamp is more than 2 seconds ago)
```

## Implementation notes
* The bulk of the code to be ported to the sensor is in the Controller class
* This simulation uses C++ vectors, but the sensor implementation will probably need to use C arrays instead
* Use the ESP32 `gettimeofday` function for timestamps (per [the official docs](https://docs.espressif.com/projects/esp-idf/en/latest/esp32/api-reference/system/system_time.html))
* The simulation uses the C++ `find_if` function to search the sensor trigger arrays, a simple linear array search function will need to be implemented in the ESP32