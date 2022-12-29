;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname kornitsky-r-hw1) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; name: Ryan Kornitsky , username: rtkornitsky

; CS 1101 HW 1


;Question 1 and Question 2 (signatures)

; purpose = comsume Date and produce it's year, month and day
; signature = Date -> natural, natural, natural

(define-struct Date (year month day))

; year = the year of the date (natural)
; month = the month of the date (natural)
; day = the day of the year (natural)


; all the following signatures for date definitions are: Date -> natural, natural, natural

(define POTTER (make-Date 2020 12 26))
(define WARS (make-Date 2000 06 24))
(define FF9 (make-Date 2021 05 10))


(define CCC (make-Date 2021 09 05))
(define philm (make-Date 1999 04 25))

; purpose = consume Film and produce the title, genre, rating, running-time, opening-date, and receipts collected
; signature = Film -> string, string, string, natural, Date, natural

(define-struct Film (title genre rating running-time opening-date receipts-collected))

; title = name of the film (string)
; genre = genre of the film (string)
; rating = rating of the movie (string)
; running-time = how long the movie is in minutes (natural)
; opening-date = when the movie first released in theaters in year, month, day order (date)
; receipts-collected = total box office receipts collected in millions of dollars (natural)


; signature ... all of the following make-Films definitions are: Film -> string, string, string, natural, Date, natural

(define ABC (make-Film "harry potter" "fantasy" "PG-13" 120 POTTER 300))
(define PIE (make-Film "Star Wars" "fantasy" "R" 110 WARS 350))
(define APP (make-Film "Fast and Furious 9" "action" "G" 200 FF9 10))
(define CAT (make-Film "Apples" "comedy" "PG" 60 CCC 1000))



;Question 3

; purpose ... consume Film and Date and produces true if the rating of the movie is G, PG, or PG-13 and false if none of those
; signature ... film -> boolean

(define (suitable-for-children? film)
  (cond [(string=? (Film-rating film) "G") #true]
        [(string=? (Film-rating film) "PG") #true]
        [(string=? (Film-rating film) "PG-13") #true]
        [else #false]))

(check-expect (suitable-for-children? ABC) true)
(check-expect (suitable-for-children? PIE) false)
(check-expect (suitable-for-children? APP) true)
(check-expect (suitable-for-children? CAT) true)




;Question 4

; purpose ... consume film1 and film2 to produce a number that is the difference in box office receipts
; signature ... Film Film -> natural

(define (difference-in-receipts film1 film2)
   (cond [(< (Film-receipts-collected film1) (Film-receipts-collected film2)) (- (Film-receipts-collected film2) (Film-receipts-collected film1))]
         [(> (Film-receipts-collected film1) (Film-receipts-collected film2)) (- (Film-receipts-collected film1) (Film-receipts-collected film2))]
         [else 0]))


(check-expect (difference-in-receipts ABC PIE) 50)
(check-expect (difference-in-receipts PIE APP) 340)
(check-expect (difference-in-receipts CAT APP) 990)
(check-expect (difference-in-receipts CAT CAT) 0)


;Question 5

; purpose ... consume a Film and a string that represents a rating to produce a Film that has a replaced/different rating
; signature ... Film, string -> string

(define CBA (make-Film "harry potter" "fantasy" "R" 120 POTTER 300))
(define EIP (make-Film "Star Wars" "fantasy" "PG-13" 110 WARS 350))
(define AAP (make-Film "Fast and Furious 9" "action" "PG" 200 FF9 10))
(define TAC (make-Film "Apples" "comedy" "G" 60 CCC 1000))

(define (modify-rating film film-rating)
  (make-Film (Film-title film) (Film-genre film) film-rating (Film-running-time film) (Film-opening-date film) (Film-receipts-collected film)))
   
(check-expect (modify-rating ABC "R") CBA)
(check-expect (modify-rating PIE "PG-13") EIP)
(check-expect (modify-rating CAT "G") TAC)
(check-expect (modify-rating ABC "PG-13") ABC)

;Question 6

; purpose ... consume a Film and a Date and produce true if the Film opens before the Date and false if it opens after the Date
; signature ... Film, Date -> Boolean

(define (opens-before? film Date)
  (cond [(< (Date-year (Film-opening-date film)) (Date-year Date)) true]
        [(> (Date-year (Film-opening-date film)) (Date-year Date)) false]
        [else
         (cond [(< (Date-month (Film-opening-date film)) (Date-month Date)) true]
               [(> (Date-month (Film-opening-date film)) (Date-month Date)) false]
               [else
                (cond
                  [(< (Date-day (Film-opening-date film)) (Date-day Date)) true]
                  [(> (Date-day (Film-opening-date film)) (Date-day Date)) false]
                  [else false])])]))

(check-expect (opens-before? ABC POTTER) false)
(check-expect (opens-before? ABC WARS) false)
(check-expect (opens-before? PIE POTTER) true)

(check-expect (opens-before? ABC (make-Date 2020 06 26)) false)
(check-expect (opens-before? PIE (make-Date 2000 08 26)) true)

(check-expect (opens-before? ABC (make-Date 2020 12 21)) false)
(check-expect (opens-before? PIE (make-Date 2020 12 25)) true)