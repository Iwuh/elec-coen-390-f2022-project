#include <iostream>
#include <thread>
#include <fstream>
#include <filesystem>
#include <random>

#include "controller.h"

void runFile(std::ifstream& stream, bool addNoise);
int randRange(int a, int b);

int main()
{
    std::string ans = "";
    while (!(ans == "y" || ans == "n"))
    {
        std::cout << "Insert random noise events into input? [y/n] ";
        std::cin >> ans;
    }

    for (const std::filesystem::directory_entry& entry : std::filesystem::directory_iterator("inputs"))
    {
        std::ifstream inputFile(entry.path());
        if (!inputFile)
        {
            std::cout << "Unable to read input file " << entry.path() << std::endl;
            return 1;
        }
        std::cout << "Running simulation on file " << entry.path() << std::endl;
        runFile(inputFile, ans == "y");
        std::cout << "----------------------------\n";
    }

    return 0;
}

void runFile(std::ifstream& inputFile, bool addNoise)
{
    // Get number of events and the expected result from the input file.
    size_t numEvents;
    int expectedResult;
    inputFile >> numEvents;
    inputFile >> expectedResult;

    // Get the timestamp and sensor of each event in the file and store them in a vector.
    std::vector<std::pair<int, char>> inputEvents;
    inputEvents.reserve(numEvents);
    int delayMillis;
    char sensor;
    for (size_t i = 0; i < numEvents; i++)
    {
        inputFile >> delayMillis;
        inputFile >> sensor;
        inputEvents.push_back(std::make_pair(delayMillis, sensor));
    }

    // Generate some random noise events and insert them into the list of events
    if (addNoise)
    {
        // Arbitrarily chosen, generate one fifth of the input events in noise.
        size_t noiseCount = numEvents / 5;
        for (size_t i = 0; i < noiseCount; i++)
        {
            // Get a random index to insert the event at. 
            // NB: When using vector::insert(pos, item), the new item will take index pos and the former occupant will become index pos+1.
            int idx = randRange(0, inputEvents.size() - 1);

            // Get the previous event.
            auto prev = inputEvents[idx - 1];

            // We want to insert the new event without changing the total delay between the two events at idx and idx-1.
            // Get a random number of milliseconds within that delay.
            int delay = randRange(1, std::get<0>(prev));

            // Choose one of the two sensors to trigger.
            char sensor = randRange(1,2) == 1 ? 'o' : 'i';

            // Replace the delay of the previous event with the randomly generated value earlier.
            inputEvents[idx - 1] = std::make_pair(delay, std::get<1>(prev));
            // Insert a new event with the remainder of the delay and the randomly generated sensor.
            inputEvents.insert(inputEvents.begin() + idx, std::make_pair(std::get<0>(prev) - delay, sensor)); 
        }
        numEvents += noiseCount;
    }
    
    // Start the controller in its own thread, since we want it to be independent from the thread triggering the sensors (ie. the real world)
    Controller c;
    std::atomic_bool stop = false;
    std::thread t(&Controller::Start, &c, std::ref(stop));
    
    // Submit each sensor trigger to the controller and then sleep for the given number of milliseconds.
    for (size_t i = 0; i < numEvents; i++)
    {
        auto currPair = inputEvents[i];
        if (std::get<1>(currPair) == 'o')
        {
            c.TriggerOuterSensor();
        }
        else
        {
            c.TriggerInnerSensor();
        }

        auto sleep = std::get<0>(currPair);
        std::this_thread::sleep_for(std::chrono::milliseconds(sleep));
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
}

// Generates a random integer in [a,b]
int randRange(int a, int b)
{
    static std::random_device seed;
    static std::default_random_engine engine(seed());

    std::uniform_int_distribution distr(a,b);
    return distr(engine);
}