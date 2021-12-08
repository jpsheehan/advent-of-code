(ns advent-of-code-2021.day-06
  (:require [clojure.string :as string]))

(def fish-reproductive-period 7)
(def new-fish-extra-days 2)

(defn next-state
  "Returns a list containing the next state(s) for the fish."
  [fish]
  (if (zero? fish)
    (list (dec fish-reproductive-period) (+ (dec fish-reproductive-period) new-fish-extra-days))
    (list (dec fish))))

;(def next-state-memo (memoize next-state))

(defn simulation-step
  "Runs a single step of the simulation."
  [fishes]
  (->> fishes
       (map next-state)
       (flatten)))

(defn simulation-run
  "Runs the simulation for n days."
  [initial-state n]
  (loop [state initial-state
         n n]
    (if (zero? n)
      state
      (recur (simulation-step state)
             (dec n)))))

;(defn get-partition-size
;  "Returns an appropriate partitions size."
;  [number-fish thread-count]
;  (if (< number-fish thread-count)
;    1
;    (long (Math/ceil (/ number-fish thread-count)))))

;(defn mt-simulation-run
;  "Runs the simulation for n days in t threads."
;  [initial-state n t]
;  (let [partition-size (get-partition-size (count initial-state) t)
;        partitioned-state (partition-all partition-size initial-state)
;        futures (map (fn [fishes] (future (simulation-run fishes n))) partitioned-state)
;        _ (map #(deref % 100 :timeout) futures)
;        completed-futures (map #(deref %) futures)]
;    (flatten completed-futures)))

(defn parse-input
  "Parses a comma-separated line of numbers from a string."
  [s]
  (->> (string/split s #",")
       (map string/trim)
       (map #(Integer/parseInt %))))

;(defn mt-count-fish
;  [filename days thread-count]
;  (-> filename
;      (slurp)
;      (parse-input)
;      (mt-simulation-run days thread-count)
;      (count)))

(defn simulation-run-analytical
  [initial-state days]
  '())

(defn count-fish-analytical
  [filename days]
  (-> filename
      (slurp)
      (parse-input)
      (simulation-run-analytical days)
      (count)))

(defn count-fish
  [filename days]
  (-> filename
      (slurp)
      (parse-input)
      (simulation-run days)
      (count)))