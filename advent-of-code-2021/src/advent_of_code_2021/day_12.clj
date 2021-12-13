(ns advent-of-code-2021.day-12
  (:require [clojure.string :as string]
            [clojure.set :as cset]))

(defn pp
  ([x] (println x) x)
  ([msg x] (println msg x) x))

(defn is-start? [edge] (contains? edge "start"))
(defn is-end? [edge] (contains? edge "end"))
(defn is-small-cave? [cave] (= (string/lower-case cave) cave))
(defn is-big-cave? [cave] (= (string/upper-case cave) cave))

(defn parse-input-line
  [line]
  (-> line
      (string/trim-newline)
      (string/split #"-")
      (set)))

(defn parse-input
  [input]
  (->> input
       (string/split-lines)
       (map parse-input-line)))

(defn is-next-hop-valid?
  [path edge]
  (let [current (last path)
        to (first (seq (cset/difference edge #{current})))]
    (and
      (contains? edge current)
      (and (is-small-cave? to) (not (contains? path to))))))

(defn traverse-path
  [path edges]
  (println "Inside" path)
  (let [cave (last path)
        possible-edges (filter (partial is-next-hop-valid? path) edges)]
    (if (empty? possible-edges)
      (if (is-end? #{cave})
        (do (println "-Returning" [path]) [path]  )                                            ; we made it to the end!
        (do (println "-Dead end") []))                                                 ; dead-end :(
      (->> possible-edges
           (pp "Possibilities are")
           (map #(traverse-path (conj path (second %)) edges))
           (reduce concat)
           (pp "Also found:")))))

(defn find-paths
  [edges]
  (->> edges
       (pp "Edges are:")
       (filter is-start?)
       (pp "Starting with:")
       (map #(traverse-path % edges))
       (pp "Ending with:")
       (set)))

(defn get-part-one
  [filename]
  (->> filename
       (slurp)
       (parse-input)
       (find-paths)
       (count)))