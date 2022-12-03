(ns dayXX
  (:require [utils :refer [input-for-day test-ns]]
            [clojure.set :as set]
            [clojure.string :refer [split-lines split]]
            [clojure.test :refer [deftest testing is]]))

;;; Implementation

(defn solve1 [input])

(defn solve2 [input])

;;; Tests

(def example "")

(def input (input-for-day X))

(deftest solve1-test
  (testing "part 1"
    (is (= 0 (solve1 example)))
    (is (= 0 (solve1 input)))))

(deftest solve2-test
  (testing "part 2"
    (is (= 0 (solve2 example)))
    (is (= 0 (solve2 input)))))
