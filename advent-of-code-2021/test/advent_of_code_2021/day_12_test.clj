(ns advent-of-code-2021.day-12-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.day-12 :refer :all]))

(def example-1-edges (->> "resources/day_12_example_1.txt" (slurp) (parse-input)))

(deftest parse-input-line-test
  (testing "Check that an input line is parsed"
    (is (= (parse-input-line "start-A\n") ["start" "A"]))))

(deftest is-small-cave?-test
  (testing "Check that small caves can be detected"
    (is (= (is-small-cave? "a") true)))
  (testing "Check that big caves are not detected"
    (is (= (is-small-cave? "A") false))))

(deftest is-big-cave?-test
  (testing "Check that big caves can be detected"
    (is (= (is-big-cave? "a") false)))
  (testing "Check that small caves are not detected"
    (is (= (is-big-cave? "A") true))))

(deftest find-paths-test
  (testing "Check the paths for the first example"
    (is (= (find-paths example-1-edges) #{["start" "A"] ["start" "b"] ["A" "c"] ["A" "b"] ["b" "d"] ["A" "end"] ["b" "end"]}))))

(deftest get-part-one-test
  (testing "Check the answer for the first example"
    (is (= (get-part-one "resources/day_12_example_1.txt") 10)))
  (testing "Check the answer for the second example"
    (is (= (get-part-one "resources/day_12_example_2.txt") 19)))
  (testing "Check the answer for the third example"
    (is (= (get-part-one "resources/day_12_example_3.txt") 226))))

