(ns slack-name-day.core-test
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [slack-name-day.core :refer :all]))


(fact "today returns day in expected format (MM-dd)"
      (today) => #"\d{2}-\d{2}")

(fact "today for 01.01.2015 returns 01-01"
      (with-redefs [clj-time.local/local-now (fn [] (clj-time.core/date-time 2015 01 01))]
      (today) => #"01-01"))

(fact "today for 31.12.2015 returns 12-31"
      (with-redefs [clj-time.local/local-now (fn [] (clj-time.core/date-time 2015 12 31))]
        (today) => #"12-31"))

(fact "create-name-day-message works with one user, capitalicing name"
      (create-name-day-message [{:name "foo"}]) => "Hyvää nimipäivää Foo :cake:")

(fact "create-name-day-message works with many users, capitalicing all names"
      (create-name-day-message [{:name "foo"} {:name "bar"} {:name "foobar"}]) => "Hyvää nimipäivää Foo, Bar, Foobar :cake:")

(fact "having-name-day-on 10-03 returns Raimo"
      (having-name-day-on "10-03") => #{"Raimo"})

(fact "having-name-day-on 09-30 returns Sirja and Sorja"
      (having-name-day-on "09-30") => #{"Sirja" "Sorja"})

(fact "having-name-day-on 03-01 returns Alpo, Alvi, Alpi"
      (having-name-day-on "03-01") => #{"Alpi" "Alpo" "Alvi"})

(fact "users-having-name-day ville having name day returns only ville"
      (def slack-users [{:profile {:first_name "ville":last_name "Foo"}}])
      (users-having-name-day (set ["ville" "pena"]) slack-users) => (list {:profile {:first_name "ville" :last_name "Foo"}}))

(fact "users-having-name-day ville and pena having name day filters expected list"
      (def slack-users [{:profile {:first_name "ville" :last_name "Foo"}}
                        {:profile {:first_name "matti" :last_name "Foo"}}
                        {:profile {:first_name "pena" :last_name "Foo"}}])
      (users-having-name-day (set ["ville" "pena"]) slack-users) => (list {:profile {:first_name "ville" :last_name "Foo"}}
                                                                          {:profile {:first_name "pena" :last_name "Foo"}}))


