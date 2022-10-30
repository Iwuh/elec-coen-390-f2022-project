#include "controller.h"

#include <vector>
#include <chrono>
#include <algorithm>

#define MIN_TH 500000 // 0.5s
#define MAX_TH 2000000 // 2s

void Controller::TriggerOuterSensor()
{
    // Set event state and notify condition variable to simulate asynchronous interrupt
    std::unique_lock lock(mutex);
    event = true;
    sensor = 1;
    lock.unlock();
    signal.notify_all();
}

void Controller::TriggerInnerSensor()
{
    // Set event state and notify condition variable to simulate asynchronous interrupt
    std::unique_lock lock(mutex);
    event = true;
    sensor = 2;
    lock.unlock();
    signal.notify_all();
}

void Controller::Start(std::atomic_bool& stopFlag)
{
    while (!stopFlag)
    {
        // Wait until a sensor is triggered or the stop flag is set.
        // Simulates how the controller will respond to sensor triggers via interrupts.
        std::unique_lock lock(mutex);
        if (!signal.wait_for(lock, std::chrono::seconds(5), [this]{ return event; }))
        {
            continue;
        }

        long long currTime = GetTime();
        if (sensor == 1) // outer
        {
            // Search S2_triggers for the first timestamp more than MIN_TH microseconds ago but fewer than MAX_TH microseconds ago.
            auto iter = std::find_if(S2_triggers.begin(), S2_triggers.end(), [currTime](long long &ts){ return (currTime - ts >= MIN_TH && currTime - ts <= MAX_TH); });
            if (iter != S2_triggers.end())
            {
                // If we found a match, remove it and push a leave event
                S2_triggers.erase(iter);
                EventHistory.push_back(std::make_pair(currTime, Event::Left));
            }
            else
            {
                S1_triggers.push_back(currTime);
            }
        }
        else if (sensor == 2) // inner
        {
            // Perform the same search but on S1_triggers
            auto iter = std::find_if(S1_triggers.begin(), S1_triggers.end(), [currTime](long long &ts){ return (currTime - ts >= MIN_TH && currTime - ts <= MAX_TH); });
            if (iter != S1_triggers.end())
            {
                S1_triggers.erase(iter);
                EventHistory.push_back(std::make_pair(currTime, Event::Entered));
            }
            else
            {
                S2_triggers.push_back(currTime);
            }
        }

        // Erase triggers more than 2 seconds old.
        std::vector<size_t> S1_toClear;
        for (size_t i = 0; i < S1_triggers.size(); i++)
        {
            if (currTime - S1_triggers[i] > MAX_TH)
            {
                S1_toClear.push_back(i);
            }
        }
        for (size_t i = 0; i < S1_toClear.size(); i++)
        {
            S1_triggers.erase(S1_triggers.begin() + S1_toClear[i]);
        }
               
        std::vector<size_t> S2_toClear;
        for (size_t i = 0; i < S2_triggers.size(); i++)
        {
            if (currTime - S2_triggers[i] > MAX_TH)
            {
                S2_toClear.push_back(i);
            }
        }
        for (size_t i = 0; i < S2_toClear.size(); i++)
        {
            S2_triggers.erase(S2_triggers.begin() + S2_toClear[i]);
        }

        // Reset the event flag and sensor ID for the next trigger.
        event = false;
        sensor = 0;
    }    
}

// Returns the current system time in microseconds since the UNIX epoch.
long long Controller::GetTime()
{
    std::chrono::duration timestamp = std::chrono::system_clock::now().time_since_epoch();
    return std::chrono::duration_cast<std::chrono::microseconds>(timestamp).count();
}