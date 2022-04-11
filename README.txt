## Assumptions
I made some assumptions:
    1. First come first serve.
    2. Guests prefer to sit next to each other.
    3. Guests prefer to sit closer to the screen.
    4. Once the program has received a request that cannot be satisfied,
       it will reject the request completely.
    5. The program won't consider the rest of requests once it has rejected a request.
    6. All reservation identifiers are unique.

## Problem:
    Design a seat assignment program to maximize satisfaction and safety

## Approach:
    Implemented a greedy algorithm to solve this problem.
    For each request, the algorithm searches the row with empty seats number greater than request.
    If there are not enough empty seats, then it will reject the request.

## Further Improvements and Possible Strategies:
    1. A better greedy algorithm:
       Accept requests until the theatre is full. Handle requests with larger guestNum first.
       This improvement would enable more guest from the same group to stay in one row.



## How To Run Program:
    1. In the root directory, run
        javac -d classes src/main/java/walmart/*.java
        cd classes
        java walmart.App <InputFileName>.txt

## How To Run Tests:
    This project uses junit for testing.
    1. JUnit tests:
        In the root directory, run
        javac -d testClasses -cp "junit-platform-console-standalone-1.8.2.jar;classes" src/test/walmart/*.java
        java -jar junit-platform-console-standalone-1.8.2.jar --class-path "testClasses;classes" --scan-class-path
