;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname kornitsky-r-hw6) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #t)))
; name: Ryan Kornitsky , username: rtkornitsky

;Question 1

(define-struct message (username text read?))

; a message is a (make-message String String Boolean)
;username is the username of the sender
;text is the text in the email
;read? is whether or not the email has been read, true if it has been read, false if not


(define-struct user (username mailbox))

;a user is a (make-user String ListOfMessage)
;username is the username of the user
;mailbox is a ListOfMessage

;a ListOfMessage is one of
;empty
;(cons message ListOfMessage)

;; a ListOfUser is one of
; empty
; (cons User ListOfUser)

;Question 2

(define mailsys empty)
(define newuser (make-user "Newuser" empty))

; a MailSys is one of
; empty
; ListOfUser

;Question 3

;signature: String -> Void
;purpose: consumes a username and produces void to add them to the mail system
;EFFECT:: changes the number of users in mailsys

(define (add-user username)
  (set! mailsys (append (list (make-user username empty)) mailsys)))


;Question 4

;signature: String String String -> void
;purpose: consumes the name of the sender of the email, the name of the recipient of the email, and the text of an email message, and produces void or store a new unread message in the recipient's mailbox
;EFFECT: changes the mail in mailsys

(define (send-email sender recipient text)
  (set! mailsys (list-send-email sender recipient text mailsys)))

;signature: String String String ListOfUser -> ListOfUser
;purpose: consumes the username of a sender, receiver, the contents of the message and aListOfUsers and produces the same list of users with unread message in their mailbox

(define (list-send-email sender-name receiver-name text lou)
  (cond [(empty? lou) empty]
        [(cons? lou) (if (string=? (user-username (first lou)) receiver-name)
                         (cons (make-user (user-username (first lou))
                                          (append (user-mailbox (first lou))
                                                  (list (make-message sender-name
                                                                      text false)))) (rest lou))
                         (list-send-email
                          sender-name
                          receiver-name
                          text (rest lou)))]))

(check-expect (list-send-email "hi1" "hi2" "hi3" empty) empty)
(check-expect (list-send-email "unicorn" "person" "hi23"
                               (list
                                (make-user "person" empty)
                                (make-user "unicorn" empty)))
              (list
               (make-user "person" (list
                                    (make-message "unicorn" "hi23" false)))
               (make-user "unicorn" empty)))

(check-expect (list-send-email "oooof" "glen" "hi22"
                               (list
                                (make-user "glen" empty)
                                (make-user "oooof" empty)))
              (list
               (make-user "glen" (list
                                  (make-message "oooof" "hi22" false)))
               (make-user "oooof" empty)))

;Question 5

;signature: String -> ListOfMessage
;purpose: consumes a username and produces a list of messages that contains the unread messages in the mailbox of the user with the given name.
;EFFECT: changes messages in mailsys

(define (get-unread-messages username)
  (set! mailsys (list-read username mailsys)))

;signature: String ListOfUser -> ListOfUser
;purpose: consumes a user's username and a list of users and produces a ListOfUsers with messages in the user's mailbox being marked as read

(define (list-read username lou)
  (cond [(empty? lou) empty]
        [(cons? lou) (cons (make-user (user-username (first lou))
                                      (if (string=? (user-username (first lou))
                                                    username)
                                          (list-unread
                                           (user-mailbox (first lou)))
                                          (user-mailbox (first lou))))
                           (list-read username (rest lou)))]))

(check-expect (list-read "unicorn"
                         (list
                          (make-user "apples" empty)
                          (make-user "coraline"
                                     (list
                                      (make-message "apples" "orange" false)))
                          (make-user "unicorn" (list
                                                (make-message "apples" "last homework o.o" false)))))
              (list
               (make-user "apples" empty)
               (make-user "coraline" (list
                                      (make-message "apples" "orange" false)))
               (make-user "unicorn" (list
                                     (make-message "apples" "last homework o.o" true)))))


(define (list-unread lom)
  (cond [(empty? lom) empty]
        [(cons? lom)
         (cons (make-message (message-username (first lom))
                             (message-text (first lom))
                             true)
               (list-unread (rest lom)))]))

;Question 6

;signature: -> user
;purpose: produces user in mailsystem with most messages in their mailbox, and if no users in the mailsystem then return an error

(define (most-messages)
  (local [(define (most-messages a-mailsys accumulator)
            (cond [(empty? a-mailsys) accumulator]
                  [(cons? a-mailsys) (if (> (length (user-mailbox (first a-mailsys)))
                                            (length (user-mailbox accumulator)))
                                         (most-messages (rest a-mailsys)
                                                        (first a-mailsys))
                                         (most-messages (rest a-mailsys) accumulator))]))]
    (if (empty? mailsys)
        (error "i'm sorry, there aren't any users in this mail system at our disposal. try again later.")
        (most-messages mailsys (first mailsys)))))


;Question 7

(add-user "imaqtpie")
(add-user "hi")
(add-user "bye")
mailsys
;add-user...here we add 3 users into mailsys and when we call mailsys, we see that there is a big list of users consisting of "imaqtpie", "hi" and "bye"
(send-email "hi" "imaqtpie" "goodbye")
(send-email "bye" "imaqtpie" "hello")
(send-email "hi" "imaqtpie" "oof")
mailsys
;send-email...here various users are sending emails to "imaqtpie" and we can see this when we call mailsys
(get-unread-messages "imaqtpie")
(get-unread-messages "hi")
(get-unread-messages "bye")
mailsys
;get-unread-messages...here we test to see which messages are unread and produce a list of the unread ones
(most-messages)
mailsys
;most-message...here we see which user has the most emails in the mailsystem, in this case "imaqtpie" does


;Question 8

;signature: ListOfString -> Natural
;purpose: consumes a ListOfString and produces the sum of the lengths of the strings in the list

(define (total-string-length los)
  (local
    [(define sum 0)
     (define (length-of-sum los)
       (cond [(empty? los) sum]
             [(cons? los) (begin
                            (set! sum (+ sum (string-length (first los))))
                            (length-of-sum (rest los)))]))]
    (length-of-sum los)))

(check-expect (total-string-length (list "hi" "bye" "sigh")) 9)
(check-expect (total-string-length (list "tree" "four" "five" "six")) 15)
(check-expect (total-string-length empty) 0)


;Question 9

;signature: ListOfString -> String
;purpose: consumes a ListOfString and produces the concatenation of strings in the order they appear in the list

(define (one-giant-string los)
  (local
    [(define long-string "")
     (define (list-order-string los)
       (cond [(empty? los) long-string]
             [(cons? los) (begin
                            (set! long-string (string-append long-string (first los)))
                            (list-order-string (rest los)))]))]
    (list-order-string los)))

(check-expect (one-giant-string (list "abc" "def" "ghi")) "abcdefghi")
(check-expect (one-giant-string (list "one" "two" "three" "four")) "onetwothreefour")
(check-expect (one-giant-string empty) "")