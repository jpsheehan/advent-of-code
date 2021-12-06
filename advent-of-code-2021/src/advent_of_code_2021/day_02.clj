(ns advent-of-code-2021.day_02)
(require '[advent-of-code-2021.utils :all true :as utils])
(require '[clojure.string :as string])

(defn get-instructions
  "Gets the instructions from a file. It expects one instruction on each line. An instruction is in the format 'direction distance' where direction is either 'forward', 'down', or 'up' and distance is a natural number."
  []
  (utils/transform-input
    (fn
      [line]
      (let [[direction distance] (string/split (string/trim line) #" ")
            direction (case direction
                        "forward" :forward
                        "down" :down
                        "up" :up)
            distance (Integer/parseInt distance)
            ]
        [direction distance]))))

(defn get-coordinates [instructions]
  "Gets the final (cartesian) coordinates as a vector of integers."
  (loop [[x y] [0 0]
         instructions instructions]
    (if (empty? instructions) [x y]
                              (let [
                                    [direction distance] (first instructions)
                                    [x y] (case direction
                                            :down [x (+ y distance)]
                                            :up [x (- y distance)]
                                            :forward [(+ x distance) y]
                                            )
                                    ]
                                (recur [x y] (rest instructions))))))

(defn get-coordinates-with-aim [instructions]
  "Gets the final (cartesian) coordinates as a vector of integers. This function makes use of the 'aim'."
  (loop [[x y aim] [0 0 0]
         instructions instructions]
    (if (empty? instructions) [x y]
                              (let [
                                    [direction distance] (first instructions)
                                    [x y aim] (case direction
                                                :down [x y (+ aim distance)]
                                                :up [x y (- aim distance)]
                                                :forward [(+ x distance) (+ y (* aim distance)) aim]
                                                )
                                    ]
                                (recur [x y aim] (rest instructions))))))

(defn get-part-one
  []
  (let [instructions (get-instructions)
        [x y] (get-coordinates instructions)]
  (* x y)))

(defn get-part-two
  []
  (let [instructions (get-instructions)
        [x y] (get-coordinates-with-aim instructions)]
    (* x y)))
