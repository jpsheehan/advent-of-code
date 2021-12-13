(ns advent-of-code-2021.day-11-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.day-11 :refer :all]))

(def octopus-that-must-flash [10 :no-flash])
(def octopus-that-has-flashed [11 :flash])
(def octopus-that-has-reset [0 :no-flash])
(def octopus-that-must-not-flash [9 :no-flash])

(def octopus-rows-that-need-to-flash
  [[octopus-that-has-reset octopus-that-must-not-flash]
   [octopus-that-must-flash octopus-that-has-flashed]])

(def octopus-rows-that-do-not-need-to-flash
  [[octopus-that-has-reset octopus-that-must-not-flash]
   [octopus-that-must-not-flash octopus-that-has-flashed]])

(def octopus-rows-that-no-not-need-to-flash-increment-first
  [[[1 :no-flash] octopus-that-must-not-flash]
   [octopus-that-must-not-flash octopus-that-has-flashed]])

(deftest parse-input-test
  (testing "Check that parsing input works"
    (is (= (parse-input "12345\n54321") '(( 1 2 3 4 5) (5 4 3 2 1))))))

(deftest reset-energy-levels-test
  (testing "Test an octopus that hasn't flashed"
    (is (= (reset-energy-levels octopus-that-must-not-flash) octopus-that-must-not-flash)))
  (testing "Test an octopus that has flashed"
    (is (= (reset-energy-levels octopus-that-has-flashed) octopus-that-has-reset))))

(deftest do-any-octopus-need-to-flash?-test
  (testing "Test when no octopus need to flash"
    (is (= (do-any-octopus-need-to-flash? octopus-rows-that-do-not-need-to-flash) false)))
  (testing "Test when some octopus need to flash"
    (is (= (do-any-octopus-need-to-flash? octopus-rows-that-need-to-flash) true))))

(deftest find-flash-point-test
  (testing "Test when no octopus need to flash"
    (is (= (find-flash-point octopus-rows-that-do-not-need-to-flash) nil)))
  (testing "Test when octopus need to flash"
    (is (= (find-flash-point octopus-rows-that-need-to-flash) [0 1]))))

(deftest needs-to-flash?-test
  (testing "Test an octopus that needs to flash"
    (is (= (needs-to-flash? octopus-that-must-flash) true)))
  (testing "Test an octopus that has already flashed"
    (is (= (needs-to-flash? octopus-that-has-flashed) false)))
  (testing "Test an octopus that does not need to flash"
    (is (= (needs-to-flash? octopus-that-must-not-flash) false))))

(deftest safely-increment-point-test
  (testing "Test incrementing an invalid point"
    (= (safely-increment-point [2 4] octopus-rows-that-need-to-flash) octopus-rows-that-need-to-flash))
  (testing "Test incrementing top-left point"
    (= (safely-increment-point [0 0] octopus-rows-that-do-not-need-to-flash) octopus-rows-that-no-not-need-to-flash-increment-first)))

(deftest get-part-one-test
  (testing "Check the example answer for part one"
    (is (= (get-part-one "resources/day_11_example.txt") 1656))))