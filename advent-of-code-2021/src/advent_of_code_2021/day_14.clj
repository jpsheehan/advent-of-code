(ns advent-of-code-2021.day-14
  (:require [clojure.string :as str]))

(defn pp [& args] (apply println args) (last args))

(defn parse-rule-line
  [line]
  (let [[pair output] (str/split line #" -> ")]
    [pair output]))

(defn parse-input-line
  [line]
  (let [line (str/trim line)]
    (if (empty? line)
      nil
      (if (str/includes? line " -> ")
        (parse-rule-line line)
        line))))

(defn parse-input
  [input]
  (let [[template & rules] (->> input
                                (str/split-lines)
                                (map parse-input-line)
                                (filter #(not (nil? %))))]
    [template (apply hash-map (flatten rules))]))

(defn get-template-combos
  [template]
  (for [i (range (dec (count template)))]
    (str/join (take 2 (drop i template)))))

(def enumerate (partial map-indexed list))

(defn expand-once-non-memoized
  [template rules is-first?]
  (->> template
       (get-template-combos)
       (enumerate)
       (map (fn [[i c]] (str
                          (if (and is-first? (zero? i)) (first c) "")
                          (get rules c)
                          (second c))))
       (str/join)))

(def expand-once (memoize expand-once-non-memoized))

(def partition-size 16)

;(defn between
;  [f coll]
;  (->> (for [[a b] (->> coll
;                        (partition-all 2 1)
;                        (pp "Prefilter")
;                        (filter #(= (count %) 2))
;                        (pp "Partitioned"))
;             :let [result (f a b)]]
;         (if (nil? b) (if (nil? a) nil a) (str a result)))
;       (filter #(not (nil? %)))
;       (reduce concat)
;       (pp "Oof")))

(defn expand
  [template rules steps]
  (loop [template template
         steps steps]
    (println steps (count template))
    (if (zero? steps)
      template
      (let
        [template (->> template
                       (partition-all partition-size (dec partition-size))
                       (enumerate)
                       (map (fn [[i template]] (expand-once template rules (zero? i))))
                       ;(pp "Processed")
                       ; (between (fn [a b] (pp a b) (get rules (str (last a) (first b)))))
                       (str/join))]
        (recur template (dec steps))))))                    ; TODO: rethink this approach?


(defn get-part-one
  [filename]
  (->> filename
       (slurp)
       (parse-input)
       (apply (fn [template rules] (expand template rules 100)))
       (println)))