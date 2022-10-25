#ifndef CONTROLLER_H
#define CONTROLLER_H

#include <atomic>
#include <condition_variable>

class Controller
{
public:
    enum Event
    {
        Left, Entered
    };

    // Trigger the sensor located on the inside of the room.
    void TriggerOuterSensor();
    // Trigger the sensor located on the outside of the room.
    void TriggerInnerSensor();

    // Starts the controller. Should be run in a thread.
    void Start(std::atomic_bool& stopFlag);

    std::vector<long long> GetOuterTriggers() {return S1_triggers;}
    std::vector<long long> GetInnerTriggers() {return S2_triggers;}
    std::vector<std::pair<long long, Event>> GetEventHistory() {return EventHistory;}

private:
    long long GetTime();

    std::condition_variable signal;
    std::mutex mutex;
    // Event is set to true when a sensor is triggered
    // Sensor is set to 1 or 2 based on which sensor was triggered
    int sensor = 0;
    bool event = false;

    // Store unmatched S1 triggers
    std::vector<long long> S1_triggers;
    // Store unmatched S2 triggers
    std::vector<long long> S2_triggers;
    // Store occupancy changes. Each element is a pair of timestamp and event type (1 = leave, 2 = enter)
    std::vector<std::pair<long long, Event>> EventHistory;
};

#endif