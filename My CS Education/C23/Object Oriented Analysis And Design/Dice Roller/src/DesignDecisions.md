# Design Decisions
\<Ryan Kornitsky>

>First I started by making all of the variables I used in my DiceRollerImpl class private. This way no method could unexpectedly change the values of any of these variables and cause tight coupling. This also made testing easier since there was no unexpected reliance on a public variance in multiple spots of my code and it overall makes my code more organized and easier to understand. Additionally, it makes my code modifiable due to ecapsulation. I tried to be as concise as possible with my documentation and didn't have any unnecessary code. I keep track of all dice values in an array when roll() is called becasue this seemed like the simpliest way to store and access these values later on. Whenever roll() is called more than once, old values in the array will simply get replaced and or overwritten. I also tried to use as specific naming conventions even if it meant that some were rather long, such as "totalNumberOfDiceSides". This greatly improves readability because it is self explanatory what the purpose of this variable is instead of calling it "x". The only other "special" design choice that I did was using Java's Random library to generate and assign a random number to each dice when rolling. Other than that, I kept everything pretty simple, as changing a lot more things would have made it unecessarily hard to read.