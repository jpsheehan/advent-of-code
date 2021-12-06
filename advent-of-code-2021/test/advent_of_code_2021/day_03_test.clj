(ns advent-of-code-2021.day_03-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.day_03 :refer :all]
            [advent-of-code-2021.test-helpers :refer :all]))

(deftest part-one
  (testing "Check part one answer is correct"
    (bind-stdin-to-resource "day_03_input.txt"
                            #(is (= (get-part-one) 1092896)))
    ))

(deftest part-two
  (testing "Check part two answer is correct"
    (bind-stdin-to-resource "day_03_input.txt"
                            #(is (= (get-part-two) 4672151)))
    ))
