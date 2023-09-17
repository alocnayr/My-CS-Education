Name: Ryan Kornitsky

TDD TODO/Task list For Assignment Escape Game Builder Implementation

**Build Tests**

TODO List for the entire project including prior iterations.

TODO LIST:

1. Verify that EscapeGameManager.makeGameManager() doesn't return null
2. Verify that makeCoordinate() with two int isn't null   
3. Verify that getRow() isn't null 
4. Verify that getColumn() isn't null 
5. Verify that coordinate with (x,y) > xMax and yMax can be created (isn't null) 
6. Verify correct value of getRow() 
7. Verify correct value of getColumn()      
8. getPlayerNames() doesn't throw an exception (non null instance)
9. Verify correct player names
10. getCoordinateType() doesn't throw an exception (non null instance)
11. Verify correct coordinate type (getCoordinateType())
12. Test to see if two coordinates are equal 
13. verify that an instance of coordinate != another instance of a different object
14. Verify that the starting (current) player is the first player in the egc
15. Test validity of move with "from" location is off the board
16. Test validity of move with moving to same location
17. Verify failed move did not trigger a change in current player
18. Test validity of moving piece to a location of another piece 
19. Verify that the prior test did not actually move the location of the piece
20. Try to move a piece that doesn't belong to the current player
21. Try to move to a location that isn't on the board  
22. Test infinite board but still not being about to move to (0,0) on square
23. Move up one block without anything blocking the way (orthogonal movement) 
24. Verify that the previous test caused a change in current player 
25. Verify that valid move causes a change in piece location 
26. Orthogonal Movement North 
27. Orthogonal Movement South 
28. Orthogonal Movement East
29. Orthogonal Movement West
30. Verify that orthogonal can make a turn  
31. Verify that orthogonal can't move diagonally
32. Diagonal movement Northeast
33. Diagonal movement Northwest
34. Diagonal movement Southeast
35. Diagonal movement Southwest 
36. Verify that diagonal can't move north 
37. Diagonal can't move south
38. Diagonal can't move east
39. Diagonal can't move west  
40. Verify that diagonal can make turns
41. Linear movement North
42. Linear movement South
43. Linear movement East
44. Linear movement West
45. Linear movement Northeast
46. Linear movement Northwest
47. Linear movement Southeast
48. Linear movement Southwest
49. Verify that linear can't change direction
50. Test omni movement moving both diagonally and orthogonally in the same move
51. Test distance by verifying a piece can't move without flying (not enough distance to go around) 
52. Verify that the piece can move if they have fly attribute from previous test 
53. Verify that getMoveResult() returns none    
54. getPieceAt() valid piece returns not null
    
55. getPieceAt() returns null for spot that doesn’t contain a piece
    
56. Test getting piece with correct piece name
    
57. Testing getting piece with correct player name
    
58. Piece default value (value of 1)
    
59. Correct piece input value other than the default
    
60. Hex linear north
    
61. Hex linear south
    
62. Hex linear east
    
63. Hex linear west
    
64. Hex linear northwest
    
65. Hex linear southeast
    
66. Hex linear can’t move southwest
    
67. Hex linear cant move northeast
    
68. Hex linear finite board can’t move to row 0
    
69. Hex linear finite board can’t move to row < 0
    
70. Hex linear finite board can’t move to column 0
    
71. Hex linear finite board can’t move to column < 0
    
72. Hex linear inf row board can move to row 0
    
73. Hex linear inf row board can move to row < 0
    
74. Hex linear inf row board can move to column 0
    
75. Hex linear inf row board can’t move to column < 0
    
76. Hex linear inf col board can move to row 0
    
77. Hex linear inf col board cant move to row < 0
    
78. Hex linear inf col board can move to column 0
    
79. Hex linear inf col board can move to column < 0
    
80. Hex linear inf board can move to row 0
    
81. Hex linear inf board can move to row < 0
    
82. Hex linear inf board can move to column 0
    
83. Hex linear inf board can move to column < 0
    
84. Hex omni north
    
85. Hex omni south
    
86. Hex omni east
    
87. Hex omni west
    
88. Hex omni northwest
    
89. Hex omni southeast
    
90. Hex omni direction change
    
91. Unblock move through a single block
    
92. Unblock can move past multiple blocks
    
93. Unblock can’t land on a block
    
94. Unblock can’t move through another player
    
95. Unblock can’t move through exit that isn’t final destination
    
96. Unblock can move to an exit that is final destinatioon
    
97. Fly can move over block
    
98. Fly cant land on block
    
99. Fly can move over player
    
100. Fly cant land on other player
    
101. Fly can move over exit
    
102. Jump can move normally
    
103. Jump can’t jump over block
    
104. Jump over piece and land on clear
    
105. Can’t jump over multiple pieces in a row
    
106. Jump can’t change direction mid jump
    
107. Can’t jump over a piece and land on a block with jump and unblock
    
108. Jump over exit and land on clear
    
109. Jump over exit and land on exit (exit is final location)
    
110. Correct turn limit number
    
111. Set correct turn number
    
112. Can’t move past turn limit
    
113. Remove all pieces before turn limit results in a win
   
114. Can’t move after all piece have been removed
    
115. Turn limit draw if scores are the same
    
116. Player 2 score greater than player 1 and turn limit up
    
117. Player 1 score greater than player 2 and turn limit up
    
118. Other player 2 can’t move

119. Other player 1 can’t move
    
120. Point conflict allow piece to move to other piece location
    
121. Point conflict removes piece at starting location
    
122. Point conflict removes piece with lower value
    
123. Point conflict set correct winning piece value
    
124. Point conflict removes both pieces from to location when they have same value
    
125. Point conflict removes losing piece from to location
    
126. Point conflict attacker wins
    
127. Point conflict defender wins
    
128. Point conflict draw wins
    
129. Cant have point conflict with your own piece
    
130. Point conflict jump can’t land on a person not final location
    
131. Point conflict jump can land on a person final location
    
132. Valid move to exit location
    
133. Piece removal when move to exit location at the from location
    
134. No piece at to location when moving to exit
    
135. Moving to exit adds to corresponding player score
    
136. Player wins when score rule is met
    
137. Cant move when score rule is met
138. More information is sent when the condition is met
    
139. Adding an observer not null
    
140. Removing observer not null
    
141. Adding null observer
    
142. Removing null observer
    
143. Notify an observer with a message
    
144. Observer info sent when moving to same location
    
145. Observer Info sent when moving to a location off the board (finite board)
    
146. Observer info sent when moving past turn limit
    
147. Observer info sent when remove all pieces before turn limit
    
148. Observer info sent when turn limit reached and draw
    
149. Observer info sent when other player 2 can’t move
    
150. Observer info sent when other player 1 can’t move
    
151. Observer info sent when point conflict attacker wins
    
152. Observer info sent when point conflict defender wins
    
153. Observer info sent when point conflict draw
    
154. Observer info sent when score rule satisfied
    
155. Observer info sent when try to move when score rule satisfied
    
156. Observer info sent when player 2 score > player 1 score and turn limit up
    
157. Observer info sent when player 1 score > player 2 score and turn limit up
    
158. Observer info sent when unblock can’t land on block
    
159. Observer info sent when can’t land on player
    
160. Observer info sent when move piece from invalid from location
    
161. Observer info sent when not your turn
    
162. Observer info sent when invalid path can’t be found (moving nonlinearly when you have linear movement pattern)
    
163. Observer info sent when not enough distance
164. Observer info sent when point conflict with own piece
