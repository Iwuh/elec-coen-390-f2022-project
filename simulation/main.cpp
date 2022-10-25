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

    int numEvents;
    int expectedResult;
    inputFile >> numEvents;
    inputFile >> expectedResult;

    Controller c;
    std::atomic_bool stop = false;
    std::thread t(&Controller::Start, &c, std::ref(stop));
    
    int delayMillis;
    int sensor;
    for (size_t i = 0; i < numEvents; i++)
    {
        inputFile >> delayMillis;
        inputFile >> sensor;
        if (sensor == 1)
        {
            c.TriggerOuterSensor();
        }
        else if (sensor == 2)
        {
            c.TriggerInnerSensor();
        }
        std::this_thread::sleep_for(std::chrono::milliseconds(delayMillis));
    }
    stop = true;
    t.join();
    
    auto remainingS1Triggers = c.GetS1Triggers();
    std::cout << "Remaining outer sensor triggers: ";
    for (long long &i : remainingS1Triggers)
    {
        std::cout << i << ' ';
    }
    std::cout << std::endl;

    auto remainingS2Triggers = c.GetS2Triggers();
    std::cout << "Remaining inner sensor triggers: ";
    for (long long &i : remainingS2Triggers)
    {
        std::cout << i << ' ';
    }
    std::cout << std::endl;

    auto events = c.GetEventHistory();
    std::cout << "Events: ";
    for (std::pair<long long, int> &p : events)
    {
        std::cout << '(' << std::get<0>(p) << ", ";
        if (std::get<1>(p) == 1)
        {
            std::cout << "Left) ";
        }
        else
        {
            std::cout << "Entered) ";
        }
    }
    std::cout << std::endl;

    return 0;
}