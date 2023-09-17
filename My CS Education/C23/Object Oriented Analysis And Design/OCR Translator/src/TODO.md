Name: Ryan Kornitsky

TDD TODO/Task list

1. Invalid String inputs -> whether top, middle, or bottom are null, check for exception (lines 43-45)
2. Invalid String inputs -> input strings have the different length, check for exception (lines 46-48)
3. Make sure strings with the same length don’t throw expectation based on length (lines 46-48)
4. Handle cases where input String are empty (lines 78-82)
5. Start with reading in single characters 0-9 alone with nothing else and no other spaces (lines 103-112)
6. Invalid OCR encoding -> OCR digit but with space between each character (lines 103,103, 118)
7. Be able to read any input with the concatenation of characters 0-9 with only one single space between all characters and no extra space at the beginning or end (refactored into lines 99-112)
8. Read input with just space (refactored into lines 78-82)
9. Read any input with variable spaces between characters but still no extra space at beginning or end (lines 99-112)
10. Read input with variable spaces at the beginning and end without variable spaces between each digit (lines 78-87)
11. Read input with variable spaces at the beginning and end with variable spaces between each digit (lines 78-87)