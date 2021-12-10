(ns advent-of-code-2021.day-10
  (:require [clojure.string :as string]))

(defn lookup-points-for-character-part-one
  [character]
  (case character
    \) 3
    \] 57
    \} 1197
    \> 25137
    0))

(defn lookup-points-for-character-part-two
  [character]
  (case character
    \( 1
    \[ 2
    \{ 3
    \< 4
    0))

(defn get-score-part-two
  "Sums up the character scores for part two, multiplying the sum by 5 at every step."
  [chars]
  (reduce #(+ (* 5 %1) %2) 0 chars))

(defn parse-input
  [input]
  (->> input
       (string/split-lines)
       (map string/trim)
       (map seq)))

(defn should-push?
  [character]
  (or (= character \() (= character \[) (= character \{) (= character \<)))

(defn should-check?
  [character]
  (or (= character \)) (= character \]) (= character \}) (= character \>)))

(defn do-characters-match?
  [a b]
  (or (and (= a \()
           (= b \)))
      (and (= a \[)
           (= b \]))
      (and (= a \{)
           (= b \}))
      (and (= a \<)
           (= b \>))))

(defn parse-line
  "Parses the line, the return value depends on the result of the parsing.
  If the line is syntactically good, it will return (:ok).
  If the line contains an illegal character it will return (:illegal character).
  If the line is incomplete it will return (:incomplete ()) where the second element is a list of characters required to complete the expression."
  ([line] (parse-line line '()))
  ([line stack]
   (if (empty? line)
     (if (empty? stack)
       (list :ok)
       (list :incomplete stack))
     (let [character (first line)
           line (rest line)]
       (if (should-check? character)
         (if (do-characters-match? (first stack) character)
           (parse-line line (rest stack))
           (list :illegal character))
         (if (should-push? character)
           (parse-line line (conj stack character))
           (parse-line line stack)))))))

(defn is-incomplete?
  [x]
  (= (first x) :incomplete))

(defn is-illegal?
  [x]
  (= (first x) :illegal))

(defn median
  [xs]
  (let [index (/ (dec (count xs)) 2)]
    (nth (sort xs) index)))

(defn get-part-one
  [filename]
  (->> filename
       (slurp)
       (parse-input)

       ; get a list of all illegal characters
       (map parse-line)
       (filter is-illegal?)
       (map second)

       ; assign points to each character and sum
       (map lookup-points-for-character-part-one)
       (reduce +)))

(defn map-in [f coll] (map #(map f %) coll))

(defn get-part-two
  [filename]
  (->> filename
       (slurp)
       (parse-input)

       ; get a list of characters required to complete the incomplete lines
       (map parse-line)
       (filter is-incomplete?)
       (map second)

       ; get a list of autocomplete scores for each line
       (map-in lookup-points-for-character-part-two)
       (map get-score-part-two)

       (median)))