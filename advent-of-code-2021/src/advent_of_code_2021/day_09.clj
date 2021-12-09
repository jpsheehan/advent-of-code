(ns advent-of-code-2021.day-09
  (:require [clojure.string :as string]))

(defn parse-input-line
  "Gets the height map of a single line of input."
  [line]
  (->> line
       (string/trim)
       (seq)
       (map str)
       (map #(Integer/parseInt %))))

(defn parse-input
  "Gets the height map from an input."
  [input]
  (->> input
       (string/split-lines)
       (map parse-input-line)
       (map #(apply vector %))
       (apply vector)))

(defn safely-get-height
  "Returns the height of a value or 10 if no value exists."
  [height-map [x y]]
  (if (or (< x 0) (< y 0) (>= x (count (first height-map))) (>= y (count height-map)))
    10
    (nth (nth height-map y) x)))

(defn is-low-point?
  "Returns true if the point is lower than its adjacent points."
  [height-map [x y]]
  (let [height (safely-get-height height-map [x y])]
    (and
      (< height (safely-get-height height-map [(dec x) y]))
      (< height (safely-get-height height-map [(inc x) y]))
      (< height (safely-get-height height-map [x (dec y)]))
      (< height (safely-get-height height-map [x (inc y)])))))

(defn get-low-points
  "Gets a list of low points in the height map."
  ([height-map]
   (let [width (count (first height-map))
         height (count height-map)]
     (for [x (range width)
           y (range height)
           :let [point [x y]]
           :when (is-low-point? height-map point)]
       (safely-get-height height-map point)))))

(defn get-risk-level
  "Gets the risk level based on the height."
  [height]
  (inc height))

(defn pp [x] (println x) x)

(defn get-part-one
  [filename]
  (->> filename
       (slurp)
       (parse-input)
       (get-low-points)
       (map get-risk-level)
       (reduce +)))

(defn get-part-two
  [filename]
  (->> filename
       (slurp)
       (parse-input)
       (get-basins)
       (map #(reduce count %))
       (sort)
       (take-last 3)
       (reduce *)))