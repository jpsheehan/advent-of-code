(ns advent-of-code-2021.day-09-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.day-09 :refer :all]))

(deftest parse-input-line-test
  (testing "Check parsing a line works correctly"
    (is (= (parse-input-line "2199943210") '(2 1 9 9 9 4 3 2 1 0)))))

(deftest get-part-one-test
  (testing "Check the example for part one is correct"
    (is (= (get-part-one "resources/day_09_example.txt") 15)))
  (testing "Check the real answer is correct"
    (is (= (get-part-one "resources/day_09_input.txt") 468))))

(deftest get-part-two-test
  (testing "Check the example for part is correct"
    (is (= (get-part-two "resources/day_09_example.txt") 1134))))