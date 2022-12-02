(ns day02
  (:require [utils :refer [input-for-day test-ns]]
            [clojure.string :refer [split-lines split]]
            [clojure.test :refer [deftest testing is]]))

;;; Implementation

(defn input->moves [input]
  (->> input split-lines (map #(split % #" "))))

(defn solve1 [input]
  (loop [moves (input->moves input)
         score 0]
    (if-let [move (first moves)]
      (let [round (condp = move
                    ["A" "X"] (+ 1 3)
                    ["A" "Y"] (+ 2 6)
                    ["A" "Z"] (+ 3 0)
                    ["B" "X"] (+ 1 0)
                    ["B" "Y"] (+ 2 3)
                    ["B" "Z"] (+ 3 6)
                    ["C" "X"] (+ 1 6)
                    ["C" "Y"] (+ 2 0)
                    ["C" "Z"] (+ 3 3))]
        (recur (rest moves) (+ score round)))
      score)))

(defn solve2 [input]
  (loop [moves (input->moves input)
         score 0]
    (if-let [move (first moves)]
      (let [round (condp = move
                    ["A" "X"] (+ 3 0)
                    ["A" "Y"] (+ 1 3)
                    ["A" "Z"] (+ 2 6)
                    ["B" "X"] (+ 1 0)
                    ["B" "Y"] (+ 2 3)
                    ["B" "Z"] (+ 3 6)
                    ["C" "X"] (+ 2 0)
                    ["C" "Y"] (+ 3 3)
                    ["C" "Z"] (+ 1 6))]
        (recur (rest moves) (+ score round)))
      score)))

;;; Tests

(def example "A Y
B X
C Z")

(def input (input-for-day 2))

(deftest solve1-test
  (testing "part 1"
    (is (= 15 (solve1 example)))
    (is (= 11150 (solve1 input)))))

(deftest solve2-test
  (testing "part 2"
    (is (= 12 (solve2 example)))
    (is (= 8295 (solve2 input)))))
