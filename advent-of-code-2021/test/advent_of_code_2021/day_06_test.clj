(ns advent-of-code-2021.day_06-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.day-06 :refer :all]
            [advent-of-code-2021.test-helpers :refer :all]))

(deftest get-part-one-example-test
  (testing "Check that the example for part one is correct"
    (is (= (count-fish "resources/day_06_example.txt" 18) 26))))

(deftest get-part-one-test
  (testing "Check that the answer for part one is correct"
    (is (= (count-fish "resources/day_06_input.txt" 80) 353079))))

(deftest get-part-two-example-test
  (testing "Check that the example for part two is correct"
    (is (= (count-fish "resources/day_06_example.txt" 256) 26984457539))))

(deftest get-part-two-test
  (testing "Check that the answer for part two is correct"
    (is (= (count-fish "resources/day_06_input.txt" 256) 1605400130036))))
