slack-name-day
==============

[![Build Status](https://travis-ci.org/vkomulai/clj-slack-name-day.svg?branch=master)](https://travis-ci.org/vkomulai/clj-slack-name-day)


- Simple Slack bot sending a greeting message to slack channel on user's [name day](https://en.wikipedia.org/wiki/Name_day)
- Uses Finnish almanac for name days, source: https://gist.github.com/zokier/1951412
- Instructions for deploying to Heroku as a Scheduled job

Running locally
---------------
- Get your Slack Web API token from here: https://api.slack.com/web
- Post greeting message to slack channel #general for users with name day today

```sh
SLACK_TOKEN=your-slacktoken-comes-right-here
SLACK_CHANNEL=#general
SLACK_SERNAME=NameDayBot
SLACK_USER_ICON_URL=http://www.example.com/bot-icon.png
lein run
```

- For testing purposes you can override current day with param

```sh
SLACK_TOKEN=your-slacktoken-comes-right-here
SLACK_CHANNEL=#general
SLACK_USERNAME=NameDayBot
SLACK_USER_ICON_URL=http://www.example.com/bot-icon.png
lein run 30-01
```

Run tests
---------

```sh
# Run tests
lein midje

# Run tests and watch for changes
lein midje :autotest
```

Deploy to Heroku
----------------

```sh
# Create application
heroku create

#Set config vars`
heroku config:set SLACK_TOKEN=your-slacktoken-comes-right-here
heroku config:set SLACK_CHANNEL=#general
heroku config:set SLACK_USERNAME=NameDayBot
heroku config:set SLACK_USER_ICON_URL=http://www.example.com/bot-icon.pg

# Enable scheduler
heroku addons:create scheduler:standard

# Deploy app to Heroku
git push heroku master

# Scheduler once per day (configure in browser)
heroku addons:create scheduler:standard
heroku addons:open scheduler

# Add job that runs once per day, at 0600 UTC, 0800 EET
# command ($ = lein run)
# schedule (Dyno size=free, frequency=daily, next due=0600 UTC)

# Disable web dyno because this is only for scheduled tasks
heroku ps:scale web=0

# test by targeting an instance deployed to Heroku
heroku run lein run
```

License
-------

Copyright © 2015 Ville Komulainen

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
