(ns advent-of-code-2021.day-13
  (:require [clojure.string :as str]))

(defn parse-coordinate-line
  [line]
  (let [[x y] (str/split line #",")
        x (Integer/parseInt x)
        y (Integer/parseInt y)]
    [x y]))

(defn parse-fold-line
  [line]
  (let [[_ n] (str/split line #"=")
        n (Integer/parseInt n)]
    (cond
      (str/includes? line "x=") {:x n}
      (str/includes? line "y=") {:y n})))

(defn parse-input-line
  [line]
  (if (str/includes? line ",")
    (parse-coordinate-line line)
    (if (str/includes? line "=")
      (parse-fold-line line)
      nil)))

(defn parse-input
  [input]
  (let [lines (->> input
                   (str/split-lines)
                   (filter #(not (nil? %)))
                   (map parse-input-line))
        points (take-while vector? lines)
        folds (drop-while #(not (map? %)) lines)]
    [points folds]))

(defn fold-up
  [points fold-y]
  (map
    (fn [[x y]]
      (if (> y fold-y)
        [x (- fold-y (- y fold-y))]
        [x y]))
    points))

(defn fold-left
  [points fold-x]
  (map
    (fn [[x y]]
      (if (> x fold-x)
        [(- fold-x (- x fold-x)) y]
        [x y]))
    points))

(defn fold-any
  [points fold]
  (cond
    (not (nil? (:x fold))) (fold-left points (:x fold))
    (not (nil? (:y fold))) (fold-up points (:y fold))))

(defn remove-duplicates
  [coll]
  (into [] (set coll)))

(defn fold-all
  [points folds]
  (loop [points points
         folds folds]
    (if (empty? folds)
      (remove-duplicates points)
      (let [[fold & folds] folds
            points (fold-any points fold)]
        (recur points folds)))))

(defn pp [x] (println x) x)

(defn get-part-one
  [filename]
  (->> filename
       (slurp)
       (parse-input)
       (apply (fn [points [fold & _]] (fold-any points fold)))
       (remove-duplicates)
       (count)))

(defn print-points!
  [points]
  (let [width (first (apply max-key first points))
        height (second (apply max-key second points))]
    (doseq [y (range (inc height))
            x (range (inc width))
            :let [point [x y]]]
      (do (if (some #(= point %) points)
            (print "#")
            (print "."))
          (if (= x width)
            (println))))))

(defn print-part-two
  [filename]
  (->> filename
       (slurp)
       (parse-input)
       (apply fold-all)
       (print-points!)))

(defn -main
  []
  (println "Example:")
  (print-part-two "resources/day_13_example.txt")
  (println)

  (println "Real:")
  (print-part-two "resources/day_13_input.txt")
  (println))