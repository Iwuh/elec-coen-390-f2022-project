#include <iostream>
#include <thread>
#include <fstream>

#include "controller.h"

int main()
{
    std::ifstream inputFile("input.txt");
    if (!inputFile)
    {
        std::cout << "Unable to read input file" << std::endl;
        return 1;
    }

    // Get number of events and the expected result from the input file.
    int numEvents;
    int expectedResult;
    inputFile >> numEvents;
    inputFile >> expectedResult;

    // Run the controller in its own thread, since we want it to be independent from the thread triggering the sensors (ie. the real world)
    Controller c;
    std::atomic_bool stop = false;
    std::thread t(&Controller::Start, &c, std::ref(stop));
    
    int delayMillis;
    char sensor;
    for (size_t i = 0; i < numEvents; i++)
    {
        // For each event, trigger the appropriate sensor and delay for a number of milliseconds.
        inputFile >> delayMillis;
        inputFile >> sensor;
        if (sensor == 'o')
        {
            c.TriggerOuterSensor();
        }
        else if (sensor == 'i')
        {
            c.TriggerInnerSensor();
        }
        std::this_thread::sleep_for(std::chrono::milliseconds(delayMillis));
    }
    // Once all events are submitted, stop the controller thread.
    stop = true;
    t.join();
    
    // Print out any unmatched outer sensor triggers that have not been auto-purged.
    auto remainingS1Triggers = c.GetOuterTriggers();
    std::cout << "Remaining outer sensor triggers: ";
    for (long long &i : remainingS1Triggers)
    {
        std::cout << i << ' ';
    }
    std::cout << std::endl;

    // Print out any unmatched inner sensor triggers that have not been auto-purged.
    auto remainingS2Triggers = c.GetInnerTriggers();
    std::cout << "Remaining inner sensor triggers: ";
    for (long long &i : remainingS2Triggers)
    {
        std::cout << i << ' ';
    }
    std::cout << std::endl;

    // Print out all enter/leave events, and compute the net change.
    auto events = c.GetEventHistory();
    int delta = 0;
    std::cout << "Events: ";
    for (std::pair<long long, Controller::Event> &p : events)
    {
        std::cout << '(' << std::get<0>(p) << ", ";
        if (std::get<1>(p) == Controller::Event::Left)
        {
            std::cout << "Left) ";
            delta--;
        }
        else
        {
            std::cout << "Entered) ";
            delta++;
        }
    }
    std::cout << std::endl;

    std::cout << "Expected net change: " << expectedResult << std::endl;
    std::cout << "Computed net change: " << delta << std::endl; 

    return 0;
}