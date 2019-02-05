Game of life
============

JavaFx implementation of the famous Conway's "Game of life".

### What is "Game of life"
It's an infinite grid with only 4 rules
1.    Any live cell with fewer than two live neighbors dies, as if by underpopulation.
2.    Any live cell with two or three live neighbors lives on to the next generation.
3.    Any live cell with more than three live neighbors dies, as if by overpopulation.
4.    Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.


### File formats
There are many resource file for game of life. All are ascii format, here are the main one:

 - life
 - cell
 - rle
 - gol (My own variation of life & cell)

Here are the references :http://www.conwaylife.com/wiki/Run_Length_Encoded

### usage
You can download any life file form a reference here:
http://www.conwaylife.com/wiki/Main_Page

Use "file/open" to open any of the file previously downloaded
Then run it step by step, or simply using "play"

### build
The build uses maven:

    $ mvn install
    $ java -jar

### Evolution
- [x] visual edition
- [x] file saving
- [ ] dynamic load pattern with buttons
- [ ] other automat rules, reading file comments&headers

### References
Here are some links to explain the "magic" of this "game".

- [Le Jeu de la Vie — Science étonnante #49](https://www.youtube.com/watch?v=S-W0NX97DB0)
- [epic conway's game of life](https://www.youtube.com/watch?v=C2vgICfQawE&t=311s)
- [Wiki](http://www.conwaylife.com/wiki/Main_Page)
                                       
