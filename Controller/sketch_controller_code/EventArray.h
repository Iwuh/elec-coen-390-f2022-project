class EventArray
{
public:
  EventArray()
    : eventCount(0)
  {}

  // Add a new sensor trigger.
  // timestamp_us: timestamp to add in microseconds
  // returns true if there was space to add, otherwise false
  bool add(uint64_t timestamp_us)
  {
    if (eventCount < ARRAY_MAX)
    {
      events[eventCount++] = timestamp_us;
      return true;
    }
    return false;
  }

  // Match a microseconds timestamp based on calibration criteria.
  // timestamp_us: the timestamp to match.
  // returns the matched time stamp if it was found.
  // Otherwise, returns the 64-bit unsigned maximum value
  uint64_t findAndRemove(uint64_t timestamp_us)
  {
    // Perform a linear search over the array.
    // I don't think a binary search will be worth it with such a small array size.
    // We can test this if time permits.
    for (size_t i = 0; i < eventCount; i++)
    {
      // Find the first event longer ago than MIN_TH but sooner than MAX_TH
      uint64_t absdiff = abs(timestamp_us - events[i]);
      if (absdiff >= MIN_TH && absdiff <= MAX_TH)
      {
        // If found, shift all subsequent elements back to remove this one and then return it.
        uint64_t retval = events[i];
        for (size_t j = i; j < eventCount - 1; j++)
        {
          events[j] = events[j+1];
        }
        eventCount--;
        return retval;
      }
    }
    // Maximum value of 64 bit unsigned integer, this will never be legitimately reached.
    return 0xFFFFFFFFFFFFFFFF; 
  }

private:
  static constexpr size_t ARRAY_MAX = 20;
  static constexpr uint64_t MIN_TH = 500000;
  static constexpr uint64_t MAX_TH = 2000000;
  uint64_t events[ARRAY_MAX];
  size_t eventCount;
};