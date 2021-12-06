(ns advent-of-code-2021.day_04
  (:require [advent-of-code-2021.utils :as utils]
            [advent-of-code-2021.test-helpers :as helpers]
            [clojure.string :as string]
            [clojure.set]))

(defn prepare-string-as-integer
  [s]
  (Integer/parseInt (string/trim s)))

(defn bingo-line-reader
  "Reads a single line from the input source.
  If the line contains the list of guesses, the line is split by commas and each guess is parsed as an integer. The list of numbers is returned.
  If the line is blank, an empty list is returned.
  If the line contains a board row, the numbers are split by whitespace and parsed as integers and returned as a list.
  "
  [line]
  (if (string/blank? line)
    '()
    (if (string/includes? line ",")
      (seq (map prepare-string-as-integer (string/split line #",")))
      (seq (map prepare-string-as-integer (string/split line #" +")))
      )))

(defn read-bingo-lines
  "Reads bingo lines from the input source.
  It expects the first line to contain a comma-separated list of integers that will be called out.
  It expects the remaining lines to contain the bingo boards, or to be blank.
  It will return a sequence containing:
  - a sequence with the numbers to be called
  - a sequence for each row of each board"
  []
  (let [rows (utils/transform-input bingo-line-reader)
        rows (filter #(not (empty? %)) rows)
        numbers (first rows)
        rows (rest rows)
        boards (partition 5 rows)
        ] [numbers boards]))

(defn get-winning-line
  "Returns the winning line on the board or nil if there is no win."
  [numbers board]
  (let [rows board
        cols (seq (utils/transpose rows))
        combos (concat rows cols)]
    (loop [combos combos]
      (if (empty? combos)
        nil
        (let [combo (first combos)]
          (if (every? (set numbers) combo)
            combo
            (recur (rest combos))))))))

(defn get-winning-board
  "Returns the winning board index or nil if there is no win."
  [numbers boards-with-indices]
  (loop [boards boards-with-indices]
    (if (empty? boards)
      nil
      (let [index (nth (first boards) 0)
            board (nth (first boards) 1)
            winning-line (get-winning-line numbers board)]
        (if (nil? winning-line)
          (recur (rest boards))
          index)))))

(defn find-winning-board
  "Finds out which board would win first.
  Returns the board's index and the sequence of numbers leading to the win."
  [numbers boards]
  (loop [called-numbers '()
         other-numbers numbers]
    (if (empty? other-numbers)
      nil
      (let [called-numbers (conj called-numbers (first other-numbers))
            other-numbers (rest other-numbers)
            winning-board (get-winning-board called-numbers boards)]
        (if (nil? winning-board)
          (recur called-numbers other-numbers)
          (list winning-board (reverse called-numbers)))))))

;(defn all-except
;  "Returns all the elements from a collection except the nth one."
;  [n xs]
;  (concat (take n xs) (drop (inc n) xs)))

(defn decorate-with-index
  "Returns the sequence with the indices of each item."
  [coll]
  (map-indexed (fn [idx itm] [idx itm]) coll))

(defn find-losing-board
  "Finds out which board would win last.
  Returns the board's index and the sequence of numbers leading to the win."
  [numbers all-boards]
  (loop [called-numbers '()
         other-numbers numbers
         boards all-boards
         previous-winning-index nil]
    (if (= 1 (count boards))
      (find-winning-board numbers boards)
      (if (empty? other-numbers)
        (list previous-winning-index called-numbers)
        (let [called-numbers (conj called-numbers (first other-numbers))
              other-numbers (rest other-numbers)
              winning-board-index (get-winning-board called-numbers boards)]
          (recur called-numbers other-numbers
                 (if (nil? winning-board-index)
                   boards
                   (filter (fn [[idx _]] (not= idx winning-board-index)) boards))
                 (if (nil? winning-board-index) previous-winning-index winning-board-index)))))))

(defn calculate-board-points
  "Calculates the number of points a winning board is worth."
  [board called-numbers]
  (let [last-called-number (last called-numbers)
        unmarked-board-numbers (clojure.set/difference (set (flatten board)) (set called-numbers))
        sum-of-unmarked-numbers (utils/sum unmarked-board-numbers)]
    (* sum-of-unmarked-numbers last-called-number)))

(defn play-bingo
  "Plays a game of bingo, making a decision about which board to select based upon a strategy function."
  [strategy-func]
  (let [[numbers boards] (read-bingo-lines)
        boards (decorate-with-index boards)
        board-data (strategy-func numbers boards)]
    (if (nil? board-data)
      nil
      (let [[board-index called-numbers] board-data]
        (calculate-board-points (nth boards board-index) called-numbers)))))

(defn get-part-one []
  (play-bingo find-winning-board))

(defn get-part-two []
  (play-bingo find-losing-board))

(defn -main
  []
  (helpers/bind-stdin-to-resource "day_04_input.txt"
                                  #(println (get-part-two))))