(defproject slack-name-day "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
  :main slack-name-day.core
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.julienxx/clj-slack "0.5.1"]
                 [clj-time "0.8.0"]]

  :profiles
  {:uberjar {:aot :all}
   :dev {:dependencies [[midje "1.6.0" :exclusions [org.clojure/clojure]]]
         :plugins [[lein-midje "3.1.3"]
                   [lein-kibit "0.1.2"]
                   [lein-auto "0.1.2"]]}})
