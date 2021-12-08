(ns advent-of-code-2021.day-06
  (:require [clojure.string :as string]))

; Memoization saves the day here. To run the simulation for part one, it takes:
; - 68 ms without memoization
; - 7 ms with memoization
;
; That's one order of magnitude difference and the delta will only increase as the number of days increases.
(def fish-memoized
  (memoize
    (fn
      [days-until-split days-until-end starting-num]
      (loop [s days-until-split
             e days-until-end
             n starting-num]
        (if (zero? e)
          n                                                 ; the base case
          (let [next-s (if (zero? s) 6 (dec s))             ; decrement the number of days until splitting, unless we should wrap around to 6
                next-e (dec e)
                next-n (if (zero? s) (+ (inc n) (fish-memoized 8 next-e 0)) n) ; recursively call this fn if we are supposed to be splitting
                ]
            (recur next-s next-e next-n)))))))

(defn simulation-run
  "Runs the simulation for n days. Returns the number of fish."
  [initial-state n]
  (->> (map (fn [num-days-until-split] (fish-memoized num-days-until-split n 1)) initial-state)
       (reduce +)))

(defn parse-input
  "Parses a comma-separated line of numbers from a string.
  Returns a list of initial states for fish."
  [s]
  (->> (string/split s #",")
       (map string/trim)
       (map #(Integer/parseInt %))))

(defn count-fish
  "Counts the number of fish that would exist after a certain number of days.
  The initial states for the fish are read from a file and are comma-separated."
  [filename days]
  (-> filename
      (slurp)
      (parse-input)
      (simulation-run days)))