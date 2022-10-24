#ifndef CONTROLLER_H
#define CONTROLLER_H

#include <atomic>
#include <condition_variable>

class Controller
{
public:
    // Trigger the sensor located on the inside of the room.
    void TriggerOuterSensor();
    // Trigger the sensor located on the outside of the room.
    void TriggerInnerSensor();

    // Starts the controller. Should be run in a thread.
    void Start(std::atomic_bool& stopFlag);

private:
    long long GetTime();

    std::condition_variable signal;
    std::mutex mutex;
    int sensor;
    bool event;

    // Store unmatched S1 triggers
    std::vector<long long> S1_triggers;
    // Store unmatched S2 triggers
    std::vector<long long> S2_triggers;
    // Store occupancy changes. Each element is a pair of timestamp and event type (1 = leave, 2 = enter)
    std::vector<std::pair<long long, int>> EventHistory;
};

#endif