# Occupancy Level Tracking - Simulation

This project simulates the controller and "triggers" its sensors through code.

## Methodology

1. If the inner sensor and outer sensor are triggered in close succession, a person has left.
2. If the outer sensor and inner sensor are triggered in close succession, a person has entered.
3. If a sensor is triggered and the other one is not triggered in close succession, it was a false positive.
4. We enforce a minimum delay between sensor triggers. Why? If two individuals are entering and leaving simultaneously, we don't want the leaver's trigger of the inner sensor to match with the enterer's trigger of the outer sensor.

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