# KappaLang - Code Literally Indistinguible from Twitch Chat

![HelloWorld in KappaLang](http://i.imgur.com/xlJvQJn.png)

Prints "Hello, World!" (yes, the interpreter reads the image, not the text representation)

## Seriously?  You're reading this?

Why are you here?  This is a dumb project.  It's basically the language [Brainfuck](https://en.wikipedia.org/wiki/Brainfuck) but with [Twitch](http://twitch.tv) emotes.  Why was this a good idea?  Why did I stay up so late writing this?  Two reasons:  1) more Scala practice, and 2) it's funny.  Well, point 2 isn't exactly true, this is more dumb than funny, but it was cool to try something with image recognition (which I knew basically nothing about going in to this project).  But hey it works (more or less), so that's kind of cool.  

## Howto

This language is essentially Brainfuck with the following translations:

| Brainfuck Symbol | Twitch Emote |
|------------------|--------------|
|+|![Kappa](http://i.imgur.com/nGhCNyd.png)|
|-|![BibleThump](http://i.imgur.com/PqZUXr6.png)|
|<|![RitzMitz](http://i.imgur.com/7qAp0od.png)|
|>|![FrankerZ](http://i.imgur.com/cormtsO.png)|
|.|![KAPOW](http://i.imgur.com/ZFhkK7W.png)|
|,|![NotLikeThis](http://i.imgur.com/OHEKmuU.png)|
|\[|![riPepperoni](http://i.imgur.com/z9w9l0I.png)|
|]|![twitchRaid](http://i.imgur.com/pFidsIR.png)|
|NOP| ![NOP](http://i.imgur.com/gW0lEXn.png) |

Each symbol is 40x30 pixels.  Code is read left-to-right, then top-to-bottom.  If you don't want to go through the pain of actually making your own images (because it's kind of a pain), I've included a small app that takes in a Brainfuck string (among other things) and builds a valid image out of it.

## Samples

Samples are included in the samples directory.  The original brainfuck versions were shamelessly stolen from [Rosetta Code](http://rosettacode.org/) and credit to their original authors is due.  I have also included the normalized (proper background color as well as properly sized) emotes for building your own KappaLang programs!

## TODO

Lots.  The code is in a pretty early state, and while it works, it's fairly fragile.  Lots of error handling needs to be done (as well as some unit tests!), but I wanted to put this out there because it was an idea I had last night and thought it would be pretty fun to write (and it was!).  I also need to actually fill out the build.sbt file so this can be packaged and distributed in the proper way (I did hear that Twitch was making their new HTML5 player in KappaLang ... Kappa).

## Special Thanks

* [aforgottentune](https://twitter.com/aforgottentune) -- For listening to me ramble about this
* Jeff Galper -- for putting up with my dumb Scala questions
* kohdrak -- for making fun of me only a little bit when I told him about this project
* [texelot](https://twitter.com/jpeddicord) -- for making fun of me a lot when I told him about this project
* [Twitch.tv](http://twitch.tv) -- For not suing me

This project is not affiliated with Twitch in any way.  At all.  Seriously, why would you even think that?

