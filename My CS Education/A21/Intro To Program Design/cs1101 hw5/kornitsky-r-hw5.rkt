;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname kornitsky-r-hw5) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #t)))
; name: Ryan Kornitsky , username: rtkornitsky


;Question 1

(define-struct river (name pH DO tributaries))

; River is a (make-river String Natural Natural ListOfRiver)
; name is name of river
; pH is its pH of river
; DO is its DO level of river (mg/L)
; triburatries is a list of river tributaries

; a ListOfRiver is one of
; empty
; (cons River ListOfRiver)


;Question 2

(define ELON (make-river "elon" 7.5 20 (list (make-river "glen" 1 7 empty)
                                             (make-river "gabe" 10 4 empty))))
(define GIAI (make-river "giai" 0 1 empty)) 
(define GOAT (make-river "goat" 14 19 (list (make-river "hi" 5 8
                                                        (list (make-river "high" 3 6.7 empty)
                                                              (make-river "inthesky" 5 6.8 empty))))))
(define HEALTHY (make-river "healthy" 7  7 (list (make-river "healthy2" 7.5 7.8 empty)
                                                 (make-river "healthy3" 7.9 7.99 empty))))

(define ACIDIC (make-river "acid" 0.01 0.01 (list (make-river "acid2" 1 1 empty)
                                                  (make-river "acid3" 2 2 empty))))
;Question 3

; River template:
; (define (fn-for-river a-river)
;   (...)(river-name a-river)
;        (river-pH a-river)
;        (river-DO a-river) 
;        (fn-for-lor (river-tributaries a-river))) 



; ListOfRiver template:
; (define (fn-for-lor lor)
;   (cond [(empty? lor) (...)]
;         [(cons? lor) (... (fn-for-river (first lor))
;                             (fn-for-lor (rest lor)))]))

;Question 4

;signature: River -> AListOfString
;purpose: consume a river and produce AListOfString which includes the names of the rivers in that list which have a pH less than 6.5

(define (list-acidic-rivers a-river)
  (if (< (river-pH a-river) 6.5)
      (cons (river-name a-river)
            (acidic-river? (river-tributaries a-river)))
      (acidic-river? (river-tributaries a-river))))

(check-expect (list-acidic-rivers GIAI) (list "giai"))
(check-expect (list-acidic-rivers ACIDIC) (list "acid" "acid2" "acid3"))
(check-expect (list-acidic-rivers HEALTHY) empty)
(check-expect (list-acidic-rivers (make-river "hello" 6.5 6.5 empty)) empty)

; signature: AListOfRivers -> AListOfString
; purpose: consumes a ListOfRiver and produces a list of river names that have pH less than 6.5

(define (acidic-river? alor)
  (cond [(empty? alor) empty]
        [(cons? alor) (append (list-acidic-rivers (first alor))
                              (acidic-river? (rest alor)))]))

(check-expect (acidic-river? empty) empty)
(check-expect (acidic-river? (river-tributaries ELON)) (list "glen"))
(check-expect (acidic-river? (river-tributaries (make-river "hello" 6.5 6.5 empty))) empty)
(check-expect (acidic-river? (river-tributaries (make-river "hello" 7.5 6.5 empty))) empty)



;Question 5

;template
;(define (unhealthy? a-river)
;     (...(river-pH a-river)
;     (...(river-DO a-river)))

;signature: River -> Boolean
;purpose: consumes a river and produces true if each river in the system has a pH less than 6.5 or greater than 8.5 or a DO of less than 6ppm. Produces false otherwise.

(define (unhealthy? river)
  (or  (<  (river-pH river) 6.5)         
       (>  (river-pH river) 8.5)
       (< (river-DO river) 6)
       (all-unhealthy? (river-tributaries river))))


(check-expect (unhealthy? ELON) #false)
(check-expect (unhealthy? HEALTHY) #false)
(check-expect (unhealthy? GIAI) #true)
(check-expect (unhealthy? GOAT) #true)
(check-expect (unhealthy? (make-river "oof" 6.5 6 empty)) #false)
(check-expect (unhealthy? (make-river "oof1" 6.5 7 empty)) #false)
(check-expect (unhealthy? (make-river "oof2" 6.5 5 empty)) #true)
(check-expect (unhealthy? (make-river "oof3" 8.5 6 empty)) #false)
(check-expect (unhealthy? (make-river "oof4" 8.5 5 empty)) #true)
(check-expect (unhealthy? (make-river "oof5" 8.5 7 empty)) #false)
(check-expect (unhealthy? (make-river "oof6" 2 3 empty)) #true)

;signature: AListOfRiver -> Boolean
;purpose: consumes AListOfRiver and produces true if each river in the system has a pH less than 6.5 or greater than 8.5 or a DO of less than 6ppm. Produces false otherwise.

(define (all-unhealthy? alor)
  (cond [(empty? alor) #false]
        [(cons? alor) (and (unhealthy? (first alor))
                           (all-unhealthy? (rest alor)))]))

(check-expect (all-unhealthy? empty) #false)
(check-expect (all-unhealthy? (list (make-river "glen" 1 7 empty)
                                    (make-river "gabe" 10 4 empty))) #false)
(check-expect (all-unhealthy? (list (make-river "healthy2" 7.5 7.8 empty)
                                    (make-river "healthy3" 7.9 7.99 empty))) #false)

(check-expect (all-unhealthy? (list (make-river "acid2" 1 1 empty)
                                    (make-river "acid3" 2 2 empty))) #false)



;Question 6

;(define (lower-all-pH a-river)
;     (make-river (river-name a-river)
;                 (...(river-pH a-river) ...)
;                 (river-DO a-river)
;                 (lower-all-pH-list (... a-river))))

;signature: River -> AListOfRiver
;purpose: consumes a river and produces the original river system but the pH of all of the rivers are lowered by 0.3

(define (lower-all-pH a-river)
  (make-river (river-name a-river) 
              (- (river-pH a-river) 0.3) 
              (river-DO a-river)
              (lower-all-pH-list (river-tributaries a-river))))

(check-expect (lower-all-pH GIAI) (make-river "giai" -0.3 1 empty))
(check-expect (lower-all-pH HEALTHY) (make-river "healthy" 6.7  7 (list (make-river "healthy2" 7.2 7.8 empty)
                                                                        (make-river "healthy3" 7.6 7.99 empty))))
(check-expect (lower-all-pH GOAT) (make-river "goat" 13.7 19 (list (make-river "hi" 4.7 8
                                                                               (list (make-river "high" 2.7 6.7 empty)
                                                                                     (make-river "inthesky" 4.7 6.8 empty))))))

;signature: AListOfRiver -> AListOfRiver
;purpose: consumes AListOfRiver and produces the original ListOfRiver but the pH of all of the rivers are lowered by 0.3

(define (lower-all-pH-list alor)
  (cond [(empty? alor) empty]
        [(cons? alor) (cons (lower-all-pH (first alor))
                            (lower-all-pH-list (rest alor)))]))

(check-expect (lower-all-pH-list empty) empty)
(check-expect (lower-all-pH-list (list (make-river "glen" 1 7 empty)
                                    (make-river "gabe" 10 4 empty))) (list (make-river "glen" 0.7 7 empty)
                                                                           (make-river "gabe" 9.7 4 empty)))
(check-expect (lower-all-pH-list (list (make-river "healthy2" 7.5 7.8 empty)
                                    (make-river "healthy3" 7.9 7.99 empty))) (list (make-river "healthy2" 7.2 7.8 empty)
                                                                                   (make-river "healthy3" 7.6 7.99 empty)))

(check-expect (lower-all-pH-list (list (make-river "acid2" 1 1 empty)
                                    (make-river "acid3" 2 2 empty))) (list (make-river "acid2" 0.7 1 empty)
                                                                           (make-river "acid3" 1.7 2 empty)))

;Question 7

;signature: String River -> AListOfRiver or False
;purpose: consumes a name of a River and a River system and produces 

(define(find-subsystem nameriv rivsys)
  (if (string=? (river-name rivsys) nameriv)
      rivsys
      (find nameriv (river-tributaries rivsys))))

(check-expect (find-subsystem "giai" GIAI) (make-river "giai" 0 1 empty))
(check-expect (find-subsystem "hi" GOAT) (make-river "hi" 5 8 (list (make-river "high" 3 6.7 empty) (make-river "inthesky" 5 6.8 empty))))
(check-expect (find-subsystem "healthy" GOAT) false)

;String ListOfRiver -> River or false
; interp: consumes a nameriv of the river and ListOfRiver and produces the portion of river

(define (find nameriv alor)
  (cond [(empty? alor) false]
        [(cons? alor) (if (not (false? (find-subsystem nameriv (first alor))))
                          (find-subsystem nameriv (first alor))
                          (find nameriv (rest alor)))]))

;Part2

(define-struct menu-item (name kind vegetarian? quantity price))

;; a MenuItem is a (make-menu-item String String Boolean Natural Number)
;; interp:
;; MenuItem represents an item for an electronic menu system in a restaurant, where
;;name is the name of the menu item
;;kind indicates whether the item is a beverage, entree, appetizer, dessert
;;vegetarian?  is true if the item is vegetarian
;;qty is the number of that item that has been ordered
;;price is the cost of a single item

;; an Order (ListOfMenuItem) is either
;; empty, or
;; (cons MenuItem Order)

(define CHEESEBURGER (make-menu-item "cheeseburger" "entree" #false 1 12))
(define SODA (make-menu-item "sprite" "beverage" #true 2 2))
(define ICE (make-menu-item "ice cream" "dessert" #true 10 5))
(define FRIES (make-menu-item "french fries" "appetizer" #true 2 5))
(define DUCK (make-menu-item "duck" "entree" #false 5 100))
(define DOLLAR (make-menu-item "dollar" "entree" #false 100 1))
(define DOLLAR1 (make-menu-item "dollar1" "entree" #false 100 0.50))

(define ORDER (cons SODA (cons ICE (cons FRIES empty))))
(define ORDER1 (cons ICE (cons CHEESEBURGER empty)))
(define ORDER2 (cons CHEESEBURGER (cons SODA (cons ICE (cons FRIES empty)))))
(define ORDER3 (cons FRIES (cons DUCK empty)))
(define ORDER4 (cons FRIES (cons FRIES (cons FRIES (cons FRIES empty)))))
(define ORDER5 (cons SODA (cons SODA (cons SODA empty))))
(define ORDER6 (cons DOLLAR (cons DOLLAR1 empty)))
(define ORDER7 (cons SODA (cons DOLLAR (cons DOLLAR (cons ICE (cons CHEESEBURGER empty))))))


;Question 8

;; dollar-menu-items:  ListOfMenu-items -> ListOfString
;; consumes a list of menu items and produces a list of the names of all the items with prices of $1 or less

(define (dollar-menu-items alom)
  (local [(define (dollar? menu)
            (<= (menu-item-price menu) 1))]
    (map menu-item-name (filter dollar? alom))))

(check-expect (dollar-menu-items ORDER6) (list "dollar" "dollar1"))
(check-expect (dollar-menu-items ORDER7) (list "dollar" "dollar"))
(check-expect (dollar-menu-items ORDER3) empty)

;Question 9

;; all-same-kind?: ListOfMenu-item String -> Boolean
;; consumes a ListOfMenu-items and a kind of food and produces true if every item is of that kind

(define (all-same-kind? lomi kind)
  (local [(define (kind=? item)
            (string=? (menu-item-kind item) kind))]
    (andmap kind=? lomi)))

(check-expect (all-same-kind? ORDER "beverage") #false)
(check-expect (all-same-kind? ORDER4 "appetizer") #true)
(check-expect (all-same-kind? ORDER3 "dessert") #false)


;Question 10

;; list-expensive-vegetarian: ListOfMenu-item Number -> ListOfMenu-item
;; consumes a list of menu items and returns a list of those vegetarian items that exceed the given amount

(define (list-expensive-vegetarian alom number)
  (local [(define (expensive-vegetarian? item)
            (and (> (menu-item-price item) number)
                 (boolean=? (menu-item-vegetarian? item) #true)))]
    (map menu-item-name (filter expensive-vegetarian? alom))))

(check-expect (list-expensive-vegetarian ORDER 5) empty)
(check-expect (list-expensive-vegetarian ORDER2 1) (list "sprite" "ice cream" "french fries"))
(check-expect (list-expensive-vegetarian ORDER3 2) (list "french fries"))
