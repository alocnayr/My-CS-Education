;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname kornitsky-r-hw4) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; name: Ryan Kornitsky , username: rtkornitsky


; Question 1


;; a Student is a (make-Student String String)

;(define-struct Student (Student-name Student-email))

(define-struct Student (name email))
(define BOB (make-Student "bob" "bob@gmail.edu"))
(define ROB (make-Student "rob" "rob@gmail.edu"))
(define JOB (make-Student "job" "job@gmail.edu"))
(define ROBJOBS (make-Student "rob jobs" "robjobs@apple.edu"))


;; a ListOfStudent is a list of Student

;; a ListOfStudent is one of
;;    empty
;;    (cons Student ListOfStudent)

;(define LISTOFSTUDENT (cons (make-student student-name student-email) ... empty))

(define LISTOFSTUDENT (cons BOB (cons ROB (cons JOB (cons ROBJOBS empty)))))
(define LISTOFSTUDENT1 (cons BOB (cons ROB empty)))
(define LISTOFSTUDENT2 (cons JOB (cons ROB empty)))
(define LISTOFSTUDENT3 (cons ROBJOBS empty))

;; a CourseNode is a (make-coursenode Number String String ListOfStudent BST BST)
(define-struct coursenode (course-id title instructor students left right))

; A BST (Binary Search Tree) is one of;
; - false
; - (make-coursenode Number String String ListOfStudent BST BST)

; Interpretation
; false means no BST or empty BST
; course-id is the node course-id
; title is the title of the course
; instructor is the instructor of the course
; students is a list of students in the course
; left and right are the left and right subtrees

; Invariant: for a given coursenode:
; course-id is > all course-id in its left child
; couse-id is < all course-id in its right child
; the same key never appears twice in the tree

;Question 2

(define COURSETREE (make-coursenode 90.1101 "cs1101" "mike" LISTOFSTUDENT false false))
(define COURSETREE1 (make-coursenode 90.4404 "cs4404" "mike" LISTOFSTUDENT
                                     (make-coursenode 90.3301 "cs3301" "gabe" LISTOFSTUDENT2
                                                      (make-coursenode 90.2201 "cs2201" "sarah" LISTOFSTUDENT3 false false)
                                                      (make-coursenode 90.3302 "cs3302" "james" LISTOFSTUDENT2 false false))
                                     (make-coursenode 90.5505 "cs5505" "glen" LISTOFSTUDENT2 false
                                                      (make-coursenode 90.6606 "cs6606" "glen2" LISTOFSTUDENT3 false false))))
                                  
(define COURSETREE2 (make-coursenode 91.2202 "ma2202" "ben" LISTOFSTUDENT2
                                     (make-coursenode 91.1101 "ma1101" "burt" LISTOFSTUDENT false
                                                      false)
                                     (make-coursenode 91.2203 "ma2203" "bill" LISTOFSTUDENT3 false false)))

                                                                                            


; Question 4

;#;(define (fcn-for-tree a-tree)
;    (cond [(boolean? a-tree) (... )];base case
;          [(coursenose?    a-tree) ...
;                                  (fcn-for-tree (coursenode-left  a-tree))
;                                  (fcn-for-tree (coursenode-right a-tree)))]))

;signature: BST String -> Boolean
;purpose: consume a BST and the name of a professor and produce true if any of the courses in the search tree are taught by the given professor

(define (any-taught-by? a-tree person)
  (cond [(boolean? a-tree) #false]
        [(coursenode? a-tree)
         (or (string=? person (coursenode-instructor a-tree))
             (or (any-taught-by? (coursenode-left a-tree) person)
                 (any-taught-by? (coursenode-right a-tree) person)))]))


(check-expect (any-taught-by? COURSETREE1 "james") #true)
(check-expect (any-taught-by? COURSETREE1 "glen2") #true)
(check-expect (any-taught-by? COURSETREE2 "james") #false)
(check-expect (any-taught-by? COURSETREE "bob") #false)
(check-expect (any-taught-by? COURSETREE1 "glen2") #true)
(check-expect (any-taught-by? COURSETREE2 "glen") #false)


;Question 5


;signature: ListOfStudent String -> ListOfStudent
;purpose: consume aListOfStudent and produce a new ListOfStudent without the given student

(define (remove-student alos email)
  (cond
    [(empty? alos) empty]
    [(string=? (Student-email (first alos)) email)
     (remove-student (rest alos) email)]
    [else (cons (first alos) (remove-student (rest alos) email))]))

(check-expect (remove-student LISTOFSTUDENT "bob@gmail.edu") (cons ROB (cons JOB (cons ROBJOBS empty))))
(check-expect (remove-student LISTOFSTUDENT3 "robjobs@apple.edu") empty)
(check-expect (remove-student empty "apfdjfghdh@gmail.com") empty)
(check-expect (remove-student LISTOFSTUDENT "wefrg@gmail.com") LISTOFSTUDENT)



;signature: BST String String -> BST
;purpose: consumes a BST and produces a BST with a new ListOfStudent without the student with the given email

;#;(define (fcn-for-tree a-tree)
;    (cond [(boolean? a-tree) (... )];base case
;          [(coursenose?    a-tree)...
;                                  ... (coursenode-course-id a-tree)
;                                                   (coursenode-title a-tree)
;                                                   (coursenode-instructor a-tree)
;                                                   (coursenode-students a-tree)
;                                                   (fcn-for-tree (coursenode-left a-tree)
;                                                   (fcn-for-tree (coursenode-right a-tree)

(define (drop-student a-tree course-number email)
  (cond [(boolean? a-tree) a-tree]
        [(coursenode? a-tree) (if (> (coursenode-course-id a-tree) course-number)
                                  (make-coursenode (coursenode-course-id a-tree)
                                                   (coursenode-title a-tree)
                                                   (coursenode-instructor a-tree)
                                                   (remove-student (coursenode-students a-tree) email)
                                                   (drop-student (coursenode-left a-tree) course-number email)
                                                   (coursenode-right a-tree))
                                  (make-coursenode (coursenode-course-id a-tree)
                                                   (coursenode-title a-tree)
                                                   (coursenode-instructor a-tree)
                                                   (remove-student (coursenode-students a-tree) email)
                                                   (coursenode-left a-tree)
                                                   (drop-student (coursenode-right a-tree) course-number email)))]))


(check-expect (drop-student COURSETREE 90.1101 "bob@gmail.edu") (make-coursenode 90.1101 "cs1101" "mike" (cons ROB (cons JOB (cons ROBJOBS empty))) false false))
(check-expect (drop-student COURSETREE1 90.4404 "robjobs@apple.edu") (make-coursenode 90.4404 "cs4404" "mike" (cons BOB (cons ROB (cons JOB empty))) 
                                     (make-coursenode 90.3301 "cs3301" "gabe" LISTOFSTUDENT2
                                                      (make-coursenode 90.2201 "cs2201" "sarah" LISTOFSTUDENT3 false false)
                                                      (make-coursenode 90.3302 "cs3302" "james" LISTOFSTUDENT2 false false))
                                     (make-coursenode 90.5505 "cs5505" "glen" LISTOFSTUDENT2 false
                                                      (make-coursenode 90.6606 "cs6606" "glen2" LISTOFSTUDENT3 false false))))
(check-expect (drop-student COURSETREE 90.1101 "lookslikegabeishavingfun@usa.edu") (make-coursenode 90.1101 "cs1101" "mike" LISTOFSTUDENT false false))



;Question 6

;#;(define (fcn-for-tree a-tree)
;    (cond [(boolean? a-tree) (... )];base case
;          [(coursenose?    a-tree) ...
;                                  (fcn-for-tree (coursenode-left  a-tree))
;                                  (fcn-for-tree (coursenode-right a-tree)))]))

;signature: BST -> AListOfStrings
;purpose: consume a BST and return a list of string of all courses in ascending order of that BST

(define (list-titles-in-order-by-coursenum a-tree)
  (cond [(boolean? a-tree) empty]
        [(coursenode? a-tree)
         (append (list-titles-in-order-by-coursenum (coursenode-left a-tree))
                 (cons (coursenode-title a-tree) empty)
                 (list-titles-in-order-by-coursenum (coursenode-right a-tree)))]))



(check-expect (list-titles-in-order-by-coursenum COURSETREE) (cons "cs1101" empty))
(check-expect (list-titles-in-order-by-coursenum COURSETREE1) (cons "cs2201" (cons "cs3301" (cons "cs3302" (cons "cs4404" (cons "cs5505" (cons "cs6606" empty)))))))
(check-expect (list-titles-in-order-by-coursenum COURSETREE2) (cons "ma1101" (cons "ma2202" (cons "ma2203" empty))))
(check-expect (list-titles-in-order-by-coursenum #false) empty)

;Question 7


;signature: BST Natural String String -> BST
;purpose: consumes a BST, a course number, a course title, and the name of the instructor and produces a BST with a new course added with the given information


;#;(define (fcn-for-tree a-tree)
;    (cond [(boolean? a-tree) (... )];base case
;          [(coursenose?    a-tree)...
;                                  ... (coursenode-course-id a-tree)
;                                                   (coursenode-title a-tree)
;                                                   (coursenode-instructor a-tree)
;                                                   (coursenode-students a-tree)
;                                                   (fcn-for-tree (coursenode-left a-tree)
;                                                   (fcn-for-tree (coursenode-right a-tree)
;                                                   

(define (add-course a-tree course-number course-title instructor-name)
  (cond [(boolean? a-tree) (make-coursenode course-number course-title empty instructor-name false false)]
        [(coursenode? a-tree) (if (> (coursenode-course-id a-tree) course-number)
                                  (make-coursenode (coursenode-course-id a-tree)
                                                   (coursenode-title a-tree)
                                                   (coursenode-instructor a-tree)
                                                   (coursenode-students a-tree)
                                                   (add-course (coursenode-left a-tree) course-number course-title instructor-name)
                                                   (coursenode-right a-tree))
                                  (make-coursenode (coursenode-course-id a-tree)
                                                   (coursenode-title a-tree)
                                                   (coursenode-instructor a-tree)
                                                   (coursenode-students a-tree)
                                                   (coursenode-left a-tree)
                                                   (add-course (coursenode-right a-tree) course-number course-title instructor-name)))]))



(check-expect (add-course COURSETREE 70.544 "ss554" "mack") (make-coursenode 90.1101 "cs1101" "mike" LISTOFSTUDENT (make-coursenode 70.544 "ss554" empty "mack" false false) false
                                                                             ))
(check-expect (add-course COURSETREE2 99.999 "mu999" "glen") (make-coursenode 91.2202 "ma2202" "ben" LISTOFSTUDENT2
                                     (make-coursenode 91.1101 "ma1101" "burt" LISTOFSTUDENT false
                                                      false)
                                     (make-coursenode 91.2203 "ma2203" "bill" LISTOFSTUDENT3 false (make-coursenode 99.999 "mu999" empty "glen" false false))))
(check-expect (add-course COURSETREE1 00.000 "pe000" "um") (make-coursenode 90.4404 "cs4404" "mike" LISTOFSTUDENT
                                     (make-coursenode 90.3301 "cs3301" "gabe" LISTOFSTUDENT2
                                                      (make-coursenode 90.2201 "cs2201" "sarah" LISTOFSTUDENT3 (make-coursenode 00.000 "pe000" empty "um" false false) false)
                                                      (make-coursenode 90.3302 "cs3302" "james" LISTOFSTUDENT2 false false))
                                     (make-coursenode 90.5505 "cs5505" "glen" LISTOFSTUDENT2 false
                                                      (make-coursenode 90.6606 "cs6606" "glen2" LISTOFSTUDENT3 false false))))

