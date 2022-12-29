;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname kornitsky-r-hw2) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; name: Ryan Kornitsky , username: rtkornitsky


;Question 1


;hurricane...

; purpose -> consume a hurricane and produce various information about it (name, category, max winds, velocity, heading)
; signature -> hurricane -> string, natural, natural, natural, string


; template :
; (define-struct hurricane (hurricane-name hurricane-category hurricane-maximum-sustained-winds hurricane-velocity hurricane-heading))

(define-struct hurricane (name category maximum-sustained-winds velocity heading))

; name -> name of the hurricane (string)
; category -> category of hurricane between 1 and 5 (natural)
; maximum-sustained-winds -> the maximum sustained winds in miles per hour (natural)
; velocity -> the velocity of the storm in miles per hour (natural)
; heading -> the storm's heading ... for example, NNW (string)

(define JIM (make-hurricane "jim" 1 70 50 "NNE"))



;thunderstorm...

; purpose ... consume a thunderstorm and produce various types of information about it (amount of rainfall, max wind gust, velocity, heading)
; signature ... thunderstorm -> natural, natural, natural, string

; template :
;; (define-struct thunderstorm (thunderstorm-rainfaill thunderstorm-wind-gust thunderstorm-velocity thunderstorm-heading))

(define-struct thunderstorm (rainfall wind-gust velocity heading))

; rainfall -> number of inches of rainfall (natural)
; wind-gust -> the maximum wind gust in miles per hour (natural)
; velocity -> the velocity of the storm in miles per hour (natural)
; heading -> the storm's heading ... for example, NNW (string)

(define THUNDER (make-thunderstorm 7 35 30 "NNW"))


;fire...

; purpose ... consume a fire and produce various types of information about it (coverage, number of days, displacement of people)
; signature ... fire -> natural, natural, natural

; template :
; (define-struct fire (fire-coverage fire-days fire-displacement))

(define-struct fire (coverage days displacement))

; coverage -> number of square miles it covers (natural)
; days -> the number of days it has been raging (natural)
; displacement -> the number of people displaced by the fire (natural)

(define FUEGO (make-fire 400 10 3000))



;Question 3

; storm (hurricane or thunderstorm or fire) -> boolean
; true if category 4 or 5
; true if thunderstorm with more than 3 in of rainfall AND winds exceeding 60mph
; true if fire coverage is at least 50 square miles


; purpose ... consume a storm and produce true if the storm is a high impact storm
; signature ... storm -> boolean

;; A Storm is one of
;; Hurricane
;; Thunderstorm
;; Fire

; template
; (define (high-impact? storm)
;         (cond [(huricane? storm) (...) ]
;               [(thunderstorm? storm) (...) ]
;               [(fire? storm) (...) ]
;               [else #false]))

(define (high-impact? storm)
  (cond [(hurricane? storm) (>= (hurricane-category storm) 4)]
        [(thunderstorm? storm) (and (> (thunderstorm-rainfall storm) 3) (> (thunderstorm-wind-gust storm) 60))]
        [(fire? storm) (>= (fire-coverage storm) 50)]
        [else #false]))

(check-expect (high-impact? JIM) #false)
(check-expect (high-impact? (make-hurricane "lol" 4 50 2 "NNW")) #true)
(check-expect (high-impact? (make-hurricane "lol2" 5 50 2 "NNW")) #true)
(check-expect (high-impact? THUNDER) #false)
(check-expect (high-impact? (make-thunderstorm 3 61 2 "NNW")) #false)
(check-expect (high-impact? (make-thunderstorm 4 61 2 "NNW")) #true)
(check-expect (high-impact? (make-thunderstorm 2 61 2 "NNW")) #false)
(check-expect (high-impact? (make-thunderstorm 4 60 2 "NNW")) #false)
(check-expect (high-impact? (make-thunderstorm 4 61 2 "NNW")) #true)
(check-expect (high-impact? (make-thunderstorm 4 59 2 "NNW")) #false)
(check-expect (high-impact? (make-thunderstorm 2 59 2 "NNW")) #false)
(check-expect (high-impact? FUEGO) #true)
(check-expect (high-impact? (make-fire 50 4 100)) #true)
(check-expect (high-impact? (make-fire 40 4 100)) #false)


; Question 4

; purpose ... consume a storm and a heading a produces a storm with a changed heading unless the storm is a fire
; signature ... storm, string -> storm

(define (change-heading storm storm-heading)
  (cond [(hurricane? storm) (make-hurricane
                              (hurricane-name storm)
                              (hurricane-category storm)
                              (hurricane-maximum-sustained-winds storm)
                              (hurricane-velocity storm)
                               storm-heading)]
        [(thunderstorm? storm) (make-thunderstorm
                                 (thunderstorm-rainfall storm)
                                 (thunderstorm-wind-gust storm)
                                 (thunderstorm-velocity storm)
                                  storm-heading)]
        [(fire? storm) storm]))


(check-expect (change-heading JIM "NWE") (make-hurricane "jim" 1 70 50 "NWE"))
(check-expect (change-heading THUNDER "NWE") (make-thunderstorm 7 35 30 "NWE"))
(check-expect (change-heading FUEGO "NWE") (make-fire 400 10 3000))


; Question 5

; purpose ... consume a ListOfStrings and produce the count of the total amount of characters in the strings of the list
; signature ... aListOfStrings -> natural


(define (character-count alos)
  (cond [(empty? alos) 0]
        [(cons? alos)
         (+ (string-length (first alos))
            (character-count (rest alos)))]))


(define GLEN
  (cons "listylist" (cons "glen" (cons "cards" empty))))

(define GLEN2
  (cons "oof" empty))

(define APPLES
  (cons "appppppp" (cons "fsdfsdf" (cons "f'dfsdfsdfsd" (cons "fdf" (cons "fdsgdf" empty))))))

(define EMPTY empty)


(check-expect (character-count GLEN) 18)
(check-expect (character-count GLEN2) 3)
(check-expect (character-count EMPTY) 0)
(check-expect (character-count APPLES) 36)


; Question 6

; purpose ... consume aListOfStrings and produce a list with only numeric string
; signature ... aListOfStrings -> aListOfStrings

(define (numeric-strings alos)
  (cond [(empty? alos) empty]
        [(cons? alos)
         (cond
           [(string-numeric? (first alos))
            (cons (first alos)
                  (numeric-strings (rest alos)))]
           [else empty])]))


(check-expect (numeric-strings (cons "56" (cons "4" (cons "apples" empty)))) (cons "56" (cons "4" empty)))
(check-expect (numeric-strings GLEN) empty)
(check-expect (numeric-strings GLEN2) empty)
(check-expect (numeric-strings APPLES) empty)


; Question 7

; purpose ... consume aListOfStrings and produce list of the lengths of each of the strings in the given aListOfStrings
; signature ... aListOfStrings -> aListOfNaturals


(define (lengths-of-strings alos)
  (cond [(empty? alos) empty]
        [(cons? alos)
         (cons (string-length (first alos))
               (lengths-of-strings (rest alos)))]))


(check-expect (lengths-of-strings GLEN) (cons 9 (cons 4 (cons 5 empty))))
(check-expect (lengths-of-strings GLEN2) (cons 3 empty))
(check-expect (lengths-of-strings APPLES) (cons 8 (cons 7 (cons 12 (cons 3 (cons 6 empty))))))
