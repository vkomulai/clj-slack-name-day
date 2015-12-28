(ns slack-name-day.core
  (:require [clj-slack.users :as usr]
            [clj-slack.chat :as chat]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [clj-time.local :as time]
            [clj-time.format :as fmt]))

(def slack-token (System/getenv "SLACK_TOKEN"))
(def slack-channel (System/getenv "SLACK_CHANNEL"))
(def slack-username (System/getenv "SLACK_USERNAME"))
(def slack-username-icon-url (System/getenv "SLACK_USER_ICON_URL"))

(def slack-connection
  {:api-url "https://slack.com/api"
   :token slack-token})

(defn read-finnish-name-days []
  (json/read-json
    (slurp "./resources/finnish-name-days.json")))

(defn today []
  (fmt/unparse
    (fmt/formatter "MM-dd") (time/local-now)))

(defn having-name-day-on [day]
  (set ((read-finnish-name-days) (keyword day))))

(defn create-name-day-message [slack-users]
  (str "Hyvää nimipäivää " (str/join ", " (map :name slack-users)) " :cake:"))

(defn fetch-slack-users []
  ((usr/list slack-connection) :members))

(defn users-having-name-day [names slack-users]
  (filter #(contains? names ((% :profile) :first_name)) slack-users))

(defn post-to-slack! [message]
  (println (format "Posting greeting message=(%s) to #%s!" message slack-channel))
  (chat/post-message
    slack-connection slack-channel message
    {:username slack-username
     :icon_url slack-username-icon-url}))

; day-and-month format is MM-dd, for example "01-30"
(defn -main
  [& args]
  (let [day-and-month (or (first args) (today))
        names (having-name-day-on day-and-month)
        name-day-today (users-having-name-day names (fetch-slack-users))]
    (if (not-empty name-day-today)
       (post-to-slack! (create-name-day-message name-day-today))
       (println (format "No slack users with name day on %s..." day-and-month)))))