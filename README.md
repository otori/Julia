Julia
=====

Mandelbrot Julia Renderer
[See the Wikipedia description.](http://en.wikipedia.org/wiki/Mandelbrot_set)

features
--------

1.real time Rendering 

2.the start value of mandelbrot operatabale with the keyboard

3.can save Renderd Images as .png

German description 
==================

Allgemein
---------
Das Programm berechnet die Mandelbrotmenge und gibt sie auf dem Bildschirm aus.
Außerdem kann man zoomen und die Startwerte verändern.
Kurz: Es generiert bunte Bilder und lastet den Rechner aus.
Starten kann man das Programm indem man die .jar Datei ausführt.
Nutzt man Argumente beim Starten des Programms, wird ein PNG gerendert.
Die Mandelbrotmenge
-------------------
"Die Mandelbrot-Menge ist eine fraktal erscheinende Menge, die eine bedeutende Rolle in der Chaosforschung spielt.
Der Rand der Menge weist eine Selbstähnlichkeit auf, die jedoch nicht exakt ist, da es zu Verformungen kommt.
Die Visualisierung der Menge wird im allgemeinen Sprachgebrauch oft auch Apfelmännchen genannt.
Die ersten computergrafischen Darstellungen wurden 1978 von Robert Brooks und Peter Matelski vorgestellt.
1980 veröffentlichte Benoît Mandelbrot, nach dem die Menge benannt wurde, eine Arbeit über das Thema."[Wikipedia](http://de.wikipedia.org/wiki/Mandelbrot-Menge)
Bedienung
--------
Wenn das Programm gestartet ist, kann man durch Doppelklick Zoomen und durch Doppelrechtsklick rauszoomen.
Mit den Pfeiltasten kann man die Gestalt des Fraktals Manipulieren. Mit Escape kommt man wieder in die Ausgangsposition.

Terminal Argumente:

Ermöglicht das Erzeugen von Bildern im PNG Format:

Usage: 
[(-h|--help)[:<Help>]] [(-c|--console)[:<Console>]] (-x|--resolution-x) <Image width> (-y|--resolution-y) <Image height> [(-t|--threads) <Amount of threads used. [Default: Cores * 1.5]>] (-i|--iterations) <Iterations to check for mandelbrot> --center-x <Real part of Complex Number to zoom.> --center-y <Imag part of Complex Number to zoom.> (-z|--zoom) <factor to zoom.> <Output image file.>

  [(-h|--help)[:<Help>]]

  [(-c|--console)[:<Console>]]
        Enables Console Mode

  (-x|--resolution-x) <Image width>
        (default: 800)

  (-y|--resolution-y) <Image height>
        (default: 600)

  [(-t|--threads) <Amount of threads used. [Default: Cores * 1.5]>]

  (-i|--iterations) <Iterations to check for mandelbrot>
        (default: 200)

  --center-x <Real part of Complex Number to zoom.>
        (default: -.5)

  --center-y <Imag part of Complex Number to zoom.>
        (default: 0.0)

  (-z|--zoom) <factor to zoom.>
        (default: 1)

  <Output image file.>
        (default: fraktal_out.png)



License
=======
You may use Julia under the terms of the ISC License(See [LICENSE](LICENSE)).



