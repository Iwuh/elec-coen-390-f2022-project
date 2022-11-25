#include <stdint.h>

class EventArray {
public:
  EventArray()
    : eventCount(0) {}

  // Add a new sensor trigger.
  // timestamp_us: The timestamp to add in microseconds.
  // Returns true if there was space in the event array to add, otherwise false.
  bool add(unsigned long timestamp_us) {
    if (eventCount < ARRAY_MAX) {
      events[eventCount++] = timestamp_us;
      return true;
    }
    return false;
  }

  // Match a microseconds timestamp based on calibration criteria.
  // timestamp_us: The timestamp to match in microseconds.
  // Returns the matched time stamp if it was found. Removes the matched time stamp from the event array.
  // Otherwise, returns -1.
  unsigned long findAndRemove(unsigned long timestamp_us) {    
    // Perform a linear search over the array.
    // I don't think a binary search will be worth it with such a small array size.
    // We can test this if time permits.
    for (size_t i = 0; i < eventCount; i++) {
      // Find the first event longer ago than MIN_TH but sooner than MAX_TH
      unsigned long tdiff = timestamp_us - events[i];
      if (tdiff >= MIN_TH && tdiff <= MAX_TH) {
        // If found, shift all subsequent elements back to remove this one and then return it.
        unsigned long retval = events[i];
        for (size_t j = i; j < eventCount - 1; j++) {
          events[j] = events[j + 1];
        }
        eventCount--;
        return retval;
      }
    }
    // Indicates match not found.
    return -1;
  }

  // Cleans all events from the array that are longer ago than MAX_TH.
  // timestamp_us: The timestamp to compare to in microseconds.
  // Returns the number of entries that were removed.
  size_t clean(unsigned long timestamp_us) {
    size_t indicesToRemove[ARRAY_MAX];
    size_t countToRemove = 0;

    // First pass: determine which indices need to be removed.
    for (size_t i = 0; i < eventCount; i++) {
      unsigned long tdiff = timestamp_us - events[i];
      if (tdiff > MAX_TH) {
        indicesToRemove[countToRemove++] = i;
      }
    }

    // Second pass: remove all events at those indices.
    // Since removing the element at index i shifts all further elements back by one, we start at the end of the array and move backwards to make things easier.
    for (size_t i = countToRemove; i > 0; i--) {
      for (size_t j = indicesToRemove[i - 1]; j < eventCount - 1; j++) {
        events[j] = events[j + 1];
      }
      eventCount--;
    }

    return countToRemove;
  }

  // Returns the current number of entries in the array.
  size_t count() {
    return eventCount;
  }

private:
  // The maximum number of events that can be stored in the array.
  static constexpr size_t ARRAY_MAX = 50;
  // The minimum time threshold between sensor triggers for a pair to be considered an enter or leave event.
  static constexpr unsigned long MIN_TH = 500000;
  // The maximum time threshold between sensor triggers for a pair to be considered an enter or leave event.
  // i.e., the difference between the pair must be greater than MIN_TH but less than MAX_TH.
  static constexpr unsigned long MAX_TH = 2000000;

  unsigned long events[ARRAY_MAX];
  size_t eventCount;
};