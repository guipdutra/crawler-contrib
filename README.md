# crawler-contrib [![Build Status](https://snap-ci.com/guipdutra/crawler-contrib/branch/master/build_image)](https://snap-ci.com/guipdutra/crawler-contrib/branch/master)

It works like a daemon to process repositories fetched from [crawler-fecther](https://github.com/guipdutra/crawler-fetcher) the goal is to search for Brazilian contributors in all public repositories.

# How it works

Crawler-contrib works in 4 steps:

1. [crawler-fecther](https://github.com/guipdutra/crawler-fetcher) fetch all the public repositories form Github.
2. crawler-contrib receive the repositories and scans through every Github public repo and fetches all contributors. 
3. Sums up every commit for each user across all projects.
4. Return the usernames to crawler-fetcher.

## Setup the crawler-fetcher address
In `core.clj#master-address` put the address where the crawler-fecther is running.

## Usage
- Create your personal access token on Github and put it in an env variable named `GITHUB_ACCESS_TOKEN_1`.

- Install Leiningen
- Install the dependencies:
```bash
       $ lein deps
```
- Run tests:
```bash
       $ lein midje
```
- Run the crawler contrib:
```bash
       $ lein trampoline run
```

## License

Copyright © 2015 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
