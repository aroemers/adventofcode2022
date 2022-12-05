(ns utils
  (:require [clj-http.client :as http]
            [clojure.java.io :as io]))

;;; Downloading input

(defn- input-file-name [day]
  (format "inputs/day%02d.txt" day))

(defn- load-input-for-day [day]
  (when-let [resource (io/resource (input-file-name day))]
    (slurp resource)))

(defn- download-input-for-day [day]
  (if-let [session (System/getenv "AOC_SESSION")]
    (let [response (http/request
                    {:method  :get
                     :url     (str "https://adventofcode.com/2022/day/" day "/input")
                     :headers {"cookie" (str "session=" session)}})
          input (:body response)]
      (spit (str "resources/" (input-file-name day)) input)
      input)
    (throw (ex-info "No AOC_SESSION environment variable set" {}))))

(defn input-for-day [day]
  (or (load-input-for-day day)
      (download-input-for-day day)))


;;; Submitting answers

(defn submit-answer [day part answer]
  (if-let [session (System/getenv "AOC_SESSION")]
    (let [response (http/request
                    {:method           :post
                     :url              (str "https://adventofcode.com/2022/day/" day "/answer")
                     :headers          {"cookie" (str "session=" session)}
                     :form-params      {:level part :answer answer}
                     :follow-redirects true})]
      (->> response :body (re-find #"<article>(.*?)</article>") second))
    (throw (ex-info "No AOC_SESSION environment variable set" {}))))


;;; Testing

(defn test-ns []
  (clojure.test/test-ns *ns*))
