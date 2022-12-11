(ns day11
  (:require [utils :refer [input-for-day test-ns submit-answer]]
            [clojure.repl :refer [doc pst]]
            [clojure.set :as set]
            [clojure.string :refer [split-lines split starts-with?]]
            [clojure.test :refer [deftest testing is]]))

(def day 11)

;;; Implementation

(def re-starting #"Starting items: (([\d, ])*)")
(def re-operation #"Operation: new = old ([*+]) (old|\d+)")
(def re-test #"Test: divisible by (\d+)")
(def re-true #"If true: throw to monkey (\d)")
(def re-false #"If false: throw to monkey (\d)")

(defn input->monkeys [input]
  (mapv (fn [starting operation test on-true on-false]
          {:items     (read-string (str "[" (second starting) "]"))
           :operation (let [op (case (nth operation 1) "*" * "+" +)
                            nr (parse-long (nth operation 2))]
                        (fn [old]
                          (if nr (op old nr) (op old old))))
           :test      (parse-long (second test))
           :on-true   (parse-long (second on-true))
           :on-false  (parse-long (second on-false))})
       (re-seq re-starting input)
       (re-seq re-operation input)
       (re-seq re-test input)
       (re-seq re-true input)
       (re-seq re-false input)))


(defn solve [monkeys relief rounds]
  (loop [monkeys monkeys
         round   0
         index   0]
    (if (< round rounds)
      (if (< index (count monkeys))
        (let [monkey (nth monkeys index)]
          (if-let [item (first (:items monkey))]
            (let [level  (relief ((:operation monkey) item))
                  target (if (= 0 (mod level (:test monkey)))
                           (:on-true monkey)
                           (:on-false monkey))]
              (recur (-> monkeys
                         (update-in [index :items] subvec 1)
                         (update-in [index :inspected] (fnil inc 0))
                         (update-in [target :items] conj level))
                     round
                     index))
            (recur monkeys round (inc index))))
        (recur monkeys (inc round) 0))
      (->> monkeys (map :inspected) (sort >) (take 2) (apply *)))))

(defn solve1 [input]
  (let [monkeys (input->monkeys input)]
    (solve monkeys #(long (/ % 3)) 20)))

(defn solve2 [input]
  (let [monkeys  (input->monkeys input)
        divisors (apply * (map :test monkeys))]
    (solve monkeys #(mod % divisors) 10000)))

;;; Tests

(def example "Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1")

(def input (input-for-day day))

(deftest solve1-test
  (testing "part 1"
    (is (= 10605 (solve1 example)))
    (is (= 57348 (solve1 input)))))

(deftest solve2-test
  (testing "part 2"
    (is (= 2713310158 (solve2 example)))
    (is (= 14106266886 (solve2 input)))))

;;; Submit

(def submit1 (partial submit-answer day 1))
(def submit2 (partial submit-answer day 2))
