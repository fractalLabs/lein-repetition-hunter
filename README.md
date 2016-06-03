# lein-repetition-hunter

A Leiningen plugin for using [repetition-hunter](https://github.com/mynomoto/repetition-hunter).

Repetition-hunter is a tool to find repetitions in clojure code.

## Usage

Put `[lein-repetition-hunter "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your `:user`
profile in `~/.lein/profiles.clj`

And `[repetition-hunter "1.0.0"]` into  `:dependencies`

To get all recommendations:

    $ lein repetition-hunter

To get recommendations for specific namespaces:

    $ lein repetition-hunter namespace1 namespace2

If you prepend `DEBUG=y` you will be able to see the list of namespaces that will be analized

like this: `Analyzing (repetition.hunter)`

## Disclaimer

This is a work in progress, please post issues, share PRs or contact @nes on clojure's slack.

## License

Copyright © 2016 Andrés Gómez Urquiza

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
