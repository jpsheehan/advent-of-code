(ns advent-of-code-2021.day_04-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.day_04 :refer :all]
            [advent-of-code-2021.test-helpers :refer :all]))

(deftest part-one-example
  (testing "Check part one's example answer is correct"
    (bind-stdin-to-resource "day_04_example.txt"
                            #(is (= (get-part-one) 4512)))
    ))

(deftest part-one
  (testing "Check part one's answer is correct"
    (bind-stdin-to-resource "day_04_input.txt"
                            #(is (= (get-part-one) 82440)))))

(deftest part-two-example
  (testing "Check part two's example answer is correct"
    (bind-stdin-to-resource "day_04_example.txt"
                            #(is (= (get-part-two) 1924)))))

;(deftest part-two
;  (testing "Check part two answer is correct"
;    (bind-stdin-to-resource "day_03_input.txt"
;                            #(is (= (get-part-two) 4672151)))
;    ))
