(ns advent-of-code-2021.day_05-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.day-05 :refer :all]
            [advent-of-code-2021.test-helpers :refer :all]))


(deftest get-segment-points-test
  (testing "Check that a vertical line is correct"
    (is (= (get-segment-points [[1 1] [1 3]]) '([1 1] [1 2] [1 3]))))
  (testing "Check that a horizontal line is correct"
    (is (= (get-segment-points [[9 7] [7 7]]) '([7 7] [8 7] [9 7]))))
  (testing "Check that a diagonal line is correct"
    (is (= (get-segment-points [[1 1] [3 3]]) '([1 1] [2 2] [3 3])))))

(deftest parse-point-test
  (testing "Check that a simple point can be parsed"
    (is (= (parse-point "1,2") '(1 2)))))

(deftest parse-input-line-test
  (testing "Check that a simple line can be parsed"
    (is (= (parse-input-line "0,9 -> 2,9") '([0 9] [2 9])))))

(deftest parse-input-string-test
  (testing "Check that multiple lines can be parsed"
    (is (= (parse-input-string "0,9 -> 5,9\n8,0 -> 0,8") '(([0 9] [5 9]) ([8 0] [0 8]))))))

(deftest valid-segment?-test
  (testing "Check that a vertical line is correct"
    (is (= (valid-segment? [[1 1] [1 3]]) true)))
  (testing "Check that a horizontal line is correct"
    (is (= (valid-segment? [[9 7] [7 7]]) true)))
  (testing "Check that a non-vertical and non-horizontal line is correct"
    (is (= (valid-segment? [[1 1] [3 3]]) false))))

(deftest part-one-example-answer
  (testing "Check that the example answer given for part one is correct"
    (is (= (get-part-one "resources/day_05_example.txt") 5))))

(deftest part-one-answer
  (testing "Check that the answer for part one is correct"
    (is (= (get-part-one "resources/day_05_input.txt") 7297))))

(deftest part-two-example-answer
  (testing "Check that the example answer given for part two is correct"
    (is (= (get-part-two "resources/day_05_example.txt") 12))))

(deftest part-two-example
  (testing "Check that the answer given for part two is correct"
    (is (= (get-part-two "resources/day_05_input.txt") 21038))))