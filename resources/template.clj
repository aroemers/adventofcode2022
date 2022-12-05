(ns dayXX
  (:require [utils :refer [input-for-day test-ns submit-answer]]
            [clojure.repl :refer [doc pst]]
            [clojure.set :as set]
            [clojure.string :refer [split-lines split]]
            [clojure.test :refer [deftest testing is]]))

(def day XX)

;;; Implementation

(defn solve1 [input])

(defn solve2 [input])

;;; Tests

(def example "")

(def input (input-for-day day))

(deftest solve1-test
  (testing "part 1"
    (is (= "FIXME" (solve1 example)))
    (is (= "FIXME" (solve1 input)))))

(deftest solve2-test
  (testing "part 2"
    (is (= "FIXME" (solve2 example)))
    (is (= "FIXME" (solve2 input)))))

;;; Submit

(def submit1 (partial submit-answer day 1))
(def submit2 (partial submit-answer day 2))
