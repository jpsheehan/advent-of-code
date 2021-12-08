(ns advent-of-code-2021.day-07)

(defn parse-input
  [input]
  (->> (clojure.string/split input #",")
       (map #(Integer/parseInt %))))

(defn abs [x] (if (neg? x) (* x -1) x))

(defn fuel-usage
  "Calculates the fuel usage for part 1.
  This is simply the unsigned difference between the two values."
  [from to]
  (abs (- from to)))

(defn fuel-usage-part-two
  "Calculates the fuel usage for part 2.
  We can do this easily by using Gauss' trick."
  [from to]
  (let [n (fuel-usage from to)]
    (/ (* n (+ n 1)) 2)))

(defn get-x-axis-values
  [xs]
  (range (apply min xs) (inc (apply max xs))))

(defn get-total-fuel-usage
  "Returns the total amount of fuel required for all crabs to move to each crab's position."
  [func xs]
  (let [x-axis (get-x-axis-values xs)]
    (map (fn [i] (reduce + (map (fn [j] (func j i)) xs))) x-axis)))

(defn get-part-one
  [filename]
  (->> filename
       (slurp)
       (parse-input)
       (get-total-fuel-usage fuel-usage)
       (apply min)))

(defn get-part-two
  [filename]
  (->> filename
       (slurp)
       (parse-input)
       (get-total-fuel-usage fuel-usage-part-two)
       (apply min)))