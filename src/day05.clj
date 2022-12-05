(ns day05
  (:require [utils :refer [input-for-day test-ns submit-answer]]
            [clojure.repl :refer [doc pst]]
            [clojure.set :as set]
            [clojure.string :refer [split-lines split]]
            [clojure.test :refer [deftest testing is]]))

(def day 5)

;;; Implementation

(defn transpose [m]
  (apply mapv vector m))

(defn input->stacks [lines]
  (let [rows    (for [stack lines]
                  (for [column (partition 4 (str stack " "))]
                    (second column)))
        columns (map (fn [row]
                       (drop-while #(= % \space) row))
                     (transpose (butlast rows)))]
    columns))

(defn input->moves [lines]
  (map (fn [line]
         (->> (re-matches #"move (\d+) from (\d+) to (\d+)" line)
              (rest)
              (map parse-long)))
       lines))

(defn input->data [input]
  (let [lines                    (split-lines input)
        [stack-lines move-lines] (split-with #(not= % "") lines)
        stacks                   (vec (input->stacks stack-lines))
        moves                    (input->moves (rest move-lines))]
    [stacks moves]))

(defn move [f stacks [quantity from to]]
  (let [pickup (take quantity (get stacks (dec from)))]
    (-> stacks
        (update (dec from) #(drop quantity %))
        (update (dec to) f pickup))))

(defn solve1 [input]
  (let [[stacks moves] (input->data input)
        moved          (reduce (partial move into) stacks moves)]
    (apply str (map first moved))))

(defn solve2 [input]
  (let [[stacks moves] (input->data input)
        moved          (reduce (partial move #(concat %2 %1)) stacks moves)]
    (apply str (map first moved))))

;;; Tests

(def example "    [D]   .
[N] [C]   .
[Z] [M] [P]
 1   2   3

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2")

(def input (input-for-day day))

(deftest solve1-test
  (testing "part 1"
    (is (= "CMZ" (solve1 example)))
    (is (= "HBTMTBSDC" (solve1 input)))))

(deftest solve2-test
  (testing "part 2"
    (is (= "MCD" (solve2 example)))
    (is (= "PQTJRSHWS" (solve2 input)))))

;;; Submit

(def submit1 (partial submit-answer day 1))
(def submit2 (partial submit-answer day 2))
