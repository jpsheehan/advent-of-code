(ns advent-of-code-2021.day_06-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.day-06 :refer :all]
            [advent-of-code-2021.test-helpers :refer :all]))

(deftest next-state-test
  (testing "Check that a fish with 2 days left is correct"
    (is (= (next-state 2) '(1))))
  (testing "Check that a fish with 0 days left is correct"
    (is (= (next-state 0) '(6 8)))))

(deftest simulation-step-test
  (testing "Check that the simulation steps correctly"
    (is (= (sort (simulation-step [2 3 2 0 1])) (sort '(1 2 1 6 0 8))))))

(deftest get-part-one-example-test
  (testing "Check that the example for part one is correct"
    (is (= (count-fish "resources/day_06_example.txt" 18) 26))))

(deftest get-part-one-test
  (testing "Check that the answer for part one is correct"
    (is (= (count-fish "resources/day_06_input.txt" 80) 353079))))

(deftest get-part-one-mt-test
  (testing "Check that the answer for part one is correct"
    (is (= (count-fish-analytical "resources/day_06_input.txt" 80) 353079))))

(deftest get-part-two-example-test
  (testing "Check that the example for part two is correct"
    (is (= (count-fish-analytical "resources/day_06_example.txt" 256) 26984457539))))
