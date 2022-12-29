;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname kornitsky-r-hw3) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; name: Ryan Kornitsky , username: rtkornitsky

; Question 1

; name of the item, the kind (appetizer, entree, dessert, or beverage), whether or not the item is vegetarian, the quantity of the items ordered, and the cost of a single item


; template :
; (define-struct Menu-item (Menu-item-name Menu-item-kind Menu-item-vegetarian? Menu-item-quantity Menu-item-cost))

(define-struct Menu-item (name kind vegetarian? quantity cost))

; name ... name of the item (string)
; kind ... appetizer, entree, dessert, or beverage (string)
; vegetarian? ... is the item vegetarian (boolean)
; quality ... quantity of items ordered (natural)
; cost ... cost of a single item (natural)

(define CHEESEBURGER (make-Menu-item "cheeseburger" "entree" #false 1 12))
(define SODA (make-Menu-item "sprite" "beverage" #true 2 2))
(define ICE (make-Menu-item "ice cream" "dessert" #true 10 5))
(define FRIES (make-Menu-item "french fries" "appetizer" #true 2 5))
(define DUCK (make-Menu-item "duck" "entree" #false 5 100))


; Question 2

(define ORDER (cons SODA (cons ICE (cons FRIES empty))))
(define ORDER1 (cons ICE (cons CHEESEBURGER empty)))
(define ORDER2 (cons CHEESEBURGER (cons SODA (cons ICE (cons FRIES empty)))))
(define ORDER3 (cons FRIES (cons DUCK empty)))
(define ORDER4 (cons FRIES (cons FRIES (cons FRIES (cons FRIES empty)))))
(define ORDER5 (cons SODA (cons SODA (cons SODA empty))))

; an Order is one of
;; a Menu-item
;; or Empty

; template :
; (define ORDER (cons Menu-item (cons Menu-item ... empty)))


; Question 5


;signature: Order -> Boolean
;purpose: consume an order and return true if the menu item kind of the first menu item in the list is an appetizer

(define (is-appetizer? order)
  (string=? "appetizer" (Menu-item-kind (first order))))

(check-expect (is-appetizer? ORDER) #false)
(check-expect (is-appetizer? ORDER3) #true)

;signature: Order -> Natural
;purpose: consume an order and produce quantity of items that are appetizers

(define (count-appetizers order)
  (cond [(empty? order) 0]
        [(cons? order)
         (cond
           [(is-appetizer? order)
            (+ 1 (count-appetizers (rest order)))]
           (else (count-appetizers (rest order))))]
        [else 0]))

(check-expect (count-appetizers ORDER) 1)
(check-expect (count-appetizers ORDER1) 0)
(check-expect (count-appetizers empty) 0)
(check-expect (count-appetizers ORDER4) 4)



; Question 6

;signature: Menu-item, Natural -> Boolean
;purpose: consume an order and produce true if the menu item is vegetarian and if the cost of the menu item is more than a certain amount

(define (is-expensive-vegetarian? item amount)
  (and (Menu-item-vegetarian? item)
       (> (Menu-item-cost item) amount)))

(check-expect (is-expensive-vegetarian? SODA 10) #false)
(check-expect (is-expensive-vegetarian? SODA 0) #true)
(check-expect (is-expensive-vegetarian? SODA 2) #false)
(check-expect (is-expensive-vegetarian? CHEESEBURGER 5) #false)



;signature: Order, Natural -> Order
;purpose: consume and order and a number and produce an order which contains a list of only menu items are are vegetarian and cost over a certain amount
     
(define (list-expensive-vegetarian order amount)
  (cond [(empty? order) empty]
        [(cons? order)
         (cond
           [(is-expensive-vegetarian? (first order) amount)
            (cons (first order)
                  (list-expensive-vegetarian (rest order) amount))]
           (else (list-expensive-vegetarian (rest order) amount)))]
        (else empty)))

(check-expect (list-expensive-vegetarian ORDER3 10) empty)
(check-expect (list-expensive-vegetarian empty 10) empty)
(check-expect (list-expensive-vegetarian ORDER 5) empty)
(check-expect (list-expensive-vegetarian ORDER 2) (cons ICE (cons FRIES empty)))


; Question 7

;signature: Menu-item -> Natural
;purpose: consume a menu item and produce its cost

(define (cost item)
  (* (Menu-item-quantity item) (Menu-item-cost item)))

(check-expect (cost SODA) 4)
(check-expect (cost ICE) 50)

;signature: Order -> Natural
;purpose: consume an order and produce the total cost of the order

(define (order-total order)
  (cond [(empty? order) 0]
        [(cons? order)
         (+ (cost (first order))
            (order-total (rest order)))]
        (else 0)))

(check-expect (order-total ORDER) 64)
(check-expect (order-total empty) 0)
(check-expect (order-total ORDER5) 12)

; Question 8

;signature: Order -> Boolean
;purpose: consume an order and produce true if the first menu item in the order is a beverage

(define (is-beverage? item)
  (string=? "beverage" (Menu-item-kind item)))

(check-expect (is-beverage? SODA) #true)
(check-expect (is-beverage? CHEESEBURGER) #false)

;signature: Order -> Natural
;purpose: consume an order and produce the total cost of the order of all beverage items

(define (beverage-total order)
  (cond [(empty? order) 0]
        [(cons? order)
         (cond
           [(is-beverage? (first order))
            (+ (cost (first order))
               (beverage-total (rest order)))]
           (else 0))]
        (else 0)))

(check-expect (beverage-total ORDER5) 12)
(check-expect (beverage-total ORDER) 4)
(check-expect (beverage-total empty) 0)
(check-expect (beverage-total ORDER3) 0)


; Question 9


;signature: Order, Natural -> Natural
;purpose: consume an order and produce its total tip

(define (total-tip order tip)
  (cond [(empty? order) 0]
        [(cons? order)
         (cond
           [(boolean=? (is-beverage? (first order)) #true)
            (total-tip (rest order) tip)]
           [else
            (+ (* tip (cost (first order))) (total-tip (rest order) tip))])]
         [else 0]))


(check-expect (total-tip ORDER 0.10) 6)
(check-expect (total-tip ORDER5 0.10) 0)
(check-expect (total-tip empty 0.10) 0)

;signature: Order, Natural -> Natural
;purpose: consume an order and produce the total cost with a tip

(define (cost-with-tip order tip)
  (+ (total-tip order tip) (order-total order)))

(check-expect (cost-with-tip ORDER 0.10) 70)
(check-expect (cost-with-tip empty 0.10) 0)
(check-expect (cost-with-tip ORDER5 0.10) 12)
(check-expect (cost-with-tip ORDER 0) 64)
(check-expect (cost-with-tip empty 0) 0)
