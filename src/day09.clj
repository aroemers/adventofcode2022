(ns day09
  (:require [utils :refer [input-for-day test-ns submit-answer]]
            [clojure.repl :refer [doc pst]]
            [clojure.set :as set]
            [clojure.string :refer [split-lines split starts-with?]]
            [clojure.test :refer [deftest testing is]]))

(def day 9)

;;; Implementation

(defn move-head [head dir]
  (case dir
    "U" (update head 1 inc)
    "D" (update head 1 dec)
    "L" (update head 0 dec)
    "R" (update head 0 inc)))

(defn move-tail [head tail]
  (let [[tx ty] tail
        [hx hy] head]
    (if (or (< 1 (abs (- hx tx)))
            (< 1 (abs (- hy ty))))
      (-> tail
          (update 0 + (min 1 (max -1 (- hx tx))))
          (update 1 + (min 1 (max -1 (- hy ty)))))
      tail)))

(defn solve [input tail-count]
  (loop [lines   (split-lines input)
         head    [0 0]
         tails   (repeat tail-count head)
         visited #{head}]
    (if-let [line (first lines)]
      (let [[_ dir steps-str] (re-matches #"(\w) (\d+)" line)
            steps             (parse-long steps-str)

            [new-head new-tails new-visited]
            (loop [head    head
                   tails   tails
                   visited visited
                   steps   steps]
              (if (< 0 steps)
                (let [new-head  (move-head head dir)
                      new-tails (next (reductions move-tail new-head tails))]
                  (recur new-head new-tails (conj visited (last new-tails)) (dec steps)))
                [head tails visited]))]
        (recur (rest lines) new-head new-tails new-visited))
      (count visited))))

;;; Tests

(def example "R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2")

(def example2 "R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20")

(def input (input-for-day day))

(deftest solve1-test
  (testing "part 1"
    (is (= 13 (solve example 1)))
    (is (= 6087 (solve input 1)))))

(deftest solve2-test
  (testing "part 2"
    (is (= 36 (solve example2 9)))
    (is (= 2493 (solve input 9)))))

;;; Submit

(def submit1 (partial submit-answer day 1))
(def submit2 (partial submit-answer day 2))
