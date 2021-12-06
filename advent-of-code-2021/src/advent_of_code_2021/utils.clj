(ns advent-of-code-2021.utils)

(defn collect-until-nil
  "Repeatedly calls func until it returns nil.
  Returns a sequence containing all the return values (except the final nil)."
  [func]
  (reverse (loop [xs '()]
             (let [x (func)]
               (if (nil? x)
                 xs
                 (recur (conj xs x))))))
  )

(defn sum
  "Sums a collection of numbers."
  [coll]
  (reduce + coll))


(defn transform-input
  "Calls func on each line until the standard-input is exhausted.
  The line passed to func is trimmed of whitespace characters.
  Returns a sequence containing the return values of each call to func."
  [func]
  (collect-until-nil (fn []
                         (let [line (read-line)]
                           (if (nil? line)
                             nil
                             (func (clojure.string/trim line)))
                           )))
  )

(defn transpose
  "Transposes a matrix of longs."
  [xs]
  (let [xs (into-array xs)
        xs-height (count xs)
        xs-width (count (first xs))
        ys (make-array Long/TYPE xs-width xs-height)]
    (doseq [j (range xs-height)
            i (range xs-width)]
      (let [value (nth (nth xs j) i)]
        (aset ys i j value)))
    ys))