(ns advent-of-code-2021.day_03)
(require '[advent-of-code-2021.utils :all true :as utils])

(defn read-matrix
  "Reads all binary numbers from the standard-input.
  Each line in the input is expected to be a valid binary number.
  Returns a matrix of binary integers as a two-dimensional sequence."
  []
  (utils/transform-input
    (fn
      [line]
      (reverse
        (loop [xs '()
               letters line]
          (if (empty? letters) xs
                               (recur (conj xs (
                                                 case (first letters)
                                                 \0 0
                                                 \1 1
                                                 )) (rest letters))))))))

(defn count-zeros-ones
  "Counts the number of zeros and ones in the sequence.
  Returns a vector in the format [o z] where 'o' is the number of ones and 'z' is the number of zeros."
  [coll]
  (loop [zeros 0
         ones 0
         coll coll]
    (if (empty? coll)
      [zeros ones]
      (let [number (first coll)]
        (recur (if (= number 0) (inc zeros) zeros)
               (if (= number 1) (inc ones) ones)
               (rest coll))))))

(defn invert-binary-array
  "Inverts an array of binary integers.
  Expects each integer to be either 0 or 1."
  [coll]
  (map (fn [x] (case x
                 0 1
                 1 0)) coll))

(defn calculate-gamma-rate
  "Calculates the gamma rate based on the two-dimensional array of binary integers.
  Returns a sequence of binary integers that represents the gamma rate."
  [coll]
  (let [gamma (make-array Long/TYPE (count coll))]
    (doseq [i (range (count gamma))]
      (let [[zeros ones] (count-zeros-ones (aget coll i))]
        (aset gamma i (if (> zeros ones) 0 1)))
      )
    gamma))

(defn calculate-epsilon-rate
  "Calculates the epsilon rate given the gamma rate as a sequence of binary integers."
  [gamma]
  (invert-binary-array gamma))

(defn convert-binary-array-to-integer
  "Converts a sequence of binary integers into a single decimal integer."
  [coll]
  (Integer/parseInt (reduce str "" coll) 2))

(defn whittle-down-list
  "Takes a two-dimensional array of binary integers and reduces it to a single sequence based upon the filter-func.
  The remaining sequence of binary integers is returned."
  [matrix filter-func]
  (loop [matrix matrix
         i 0]
    (if (= 1 (count matrix))
      (first matrix)
      (let [keep (filter-func (nth (utils/transpose matrix) i))]
        (recur (filter (fn [line] (= keep (nth line i))) matrix) (inc i))))))

(defn get-oxygen-generator-rating
  "Gets the oxygen generator rating based on the two-dimensional array of binary integers.
  Returns the oxygen generator rating as an integer."
  [matrix]
  (whittle-down-list matrix (fn [line]
                             (let [[zeros ones] (count-zeros-ones line)] (if (> zeros ones) 0 1)))))

(defn get-co2-scrubber-rating
  "Gets the CO2 scrubber rating based on the two-dimensional array of binary integers.
  Returns the CO2 scrubber rating as an integer."
  [matrix]
  (whittle-down-list matrix (fn [line]
                             (let [[zeros ones] (count-zeros-ones line)] (if (<= zeros ones) 0 1)))))

(defn get-part-one
  []
  (let [matrix (read-matrix)
        matrix (utils/transpose matrix)
        gamma (calculate-gamma-rate matrix)
        epsilon (calculate-epsilon-rate gamma)
        gamma (convert-binary-array-to-integer gamma)
        epsilon (convert-binary-array-to-integer epsilon)]
    (* gamma epsilon)))

(defn get-part-two
  []
  (let [matrix (read-matrix)
        oxygen (get-oxygen-generator-rating matrix)
        oxygen (convert-binary-array-to-integer oxygen)
        co2 (get-co2-scrubber-rating matrix)
        co2 (convert-binary-array-to-integer co2)]
    (* oxygen co2)))
