---
layout: page
title: How to contribute to the project Wiki
---

* [Install Docker](https://docs.docker.com/engine/installation/)
* Modify `docs/Gemfile` as  follows
    * uncomment `gem "jekyll", "3.3.0"`
    * comment `gem "github-pages", group: :jekyll_plugins`
* Run jekyll server in Docker container using the following command

```bash
cd docs && docker run --rm --label=jekyll --volume=$(pwd):/srv/jekyll \
    -it -p 127.0.0.1:4000:4000 jekyll/jekyll
```

* Open [http://127.0.0.1:4000](http://127.0.0.1:4000/fi-muni-PA165-project) in your browser
* Modify, create or remove the files in `docs/_wiki` and refresh the browser
* Make sure you did not commit the changes in `docs/Gemfile`, otherwise Github Pages will break
