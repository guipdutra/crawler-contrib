# crawler-contrib [![Build Status](https://snap-ci.com/guipdutra/crawler-contrib/branch/master/build_image)](https://snap-ci.com/guipdutra/crawler-contrib/branch/master)

It searches in Github for Brazilian contributors in all public repositories.

# How it works

Crawler-contrib works in 3 steps:

1. Scans through every Github public repo and fetches all contributors.
2. Sums up every commit for each user across all projects.
3. Shows the usernames.

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
Make sure you set the number of repositories you wish to fetch from github before running Crawler-contrib.

You can use `take x` in `github_api_wrapper.clj#get-all-repositories` to load just a few repos. Should you leave it without a limit, it will fetch about 12 million github repos (this could take a long time).

###Example: 
```clojure
(take 10 (repos/all-repos (merge (auth) options)))
```

## Benchmark
It can take up to 6 hours to fetch 80000 repos from github on a Macbook Pro 2.6 i5 with 8GB of ram.

Steps:
- Run app:
   ```bash
    $ lein trampoline run
   ```
-  http://www.flyingmachinestudios.com/programming/lein-trampoline/

## License

Copyright © 2015 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
