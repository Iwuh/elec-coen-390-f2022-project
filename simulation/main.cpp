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
    
    return 0;
}