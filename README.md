# crawler-contrib

It searches in Github for Brazilians contributors in all public repositories.

# How it works

It gets all the public repositories in Github, for each repository it retrieves the contributors.

With all the contributors in hand, it sums the commits in all projects for each user. 

After that it shows the username

## Usage
- Create your personal access token in Github and put in your env variables with name GITHUB_ACCESS_TOKEN_1.

- Install Leiningen
- Run Tests:
    lein midje

Before run, change how many repositories you will retrieve from github. Use take x in github_api_wrapper.clj#get-all-repositories to get just few, if you leave it without a take it will fetch 12 millions repositories in Github, and it can take a long time.
Example: 
```
(take 10 (repos/all-repos (merge (auth) options)))
```

- Run app:
   lein run

## License

Copyright © 2015 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
