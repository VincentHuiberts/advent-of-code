# AOC
This repo uses the user-bound AOC session to fetch the puzzle input and caches it on the 
file system(`cache/{year}/{day}/input.txt`). The sessioninfo for fetching can be configured by supplying 
a `settings.properties`in the root of the project containing a `session` property.

The session property can be found in the cookies sent to aoc (when logged in) and is also named `session`. 