# java-game-box-project-2-programandcontrolman
java-game-box-project-2-programandcontrolman created by GitHub Classroom
# Program & Control Man 
Developed in house, Program & Control Man is a clone of the apple II game TaxMan developed by Hals Labs, which is a clone of the arcade game PacMan developed Bandai Namco. 
## Description
 In the land of Tanstaafl the citizens are in revolt, denying the government of its rightful venue, and rioting in the streets. You are the Taxman, self-appointed cham-pion of the government coffers. The silly citizens don't know what's good for them, and have developed rebellious tendencies. They are armed and dangerous. To help you, there are tax centers in each precinct. Passing through one gives you temporary power to 'pacify' the angry rebels. But, alas, they are soon back on the streets, causing more damage and destruction. Occasionally, government bonuses will appear. Quickly grab these to assure max-imum profits, before the feds take back what they offer. These 'fruit' taste de-licious, but be careful; the citizens resent this and will do their utmost to prevent your collecting these bonuses.
 
 ![alt text](https://github.com/uprm-ciic4010-s20/java-game-box-project-2-programandcontrolman/blob/master/taxman.png)

 
## Recursion used for the bullet pathfinding algorithm 

```java
 public void checker ( int pixelX, int pixelY, int tpixelX, int tpixelY){
            
            if (pixelX > 1) {
                if (nodos[pixelY][pixelX - 1] ) {//left
                    if (1 + Queue[pixelY][pixelX][0] < Queue[pixelY][pixelX - 1][0]) {
                        Queue[pixelY][pixelX - 1][0] = 1 + Queue[pixelY][pixelX][0];
                        Queue[pixelY][pixelX - 1][1] = pixelX;
                        Queue[pixelY][pixelX - 1][2] = pixelY;
                        Visited[pixelY][pixelX] = true;
                        checker(pixelX - 1, pixelY, tpixelX, tpixelY);
                    }
                }
            }
            if (pixelX < xmapsize - 1) {//right
                if (nodos[pixelY][pixelX + 1]) {
                    if (1 + Queue[pixelY][pixelX][0] < Queue[pixelY][pixelX + 1][0]) {
                        Queue[pixelY][pixelX + 1][0] = 1 + Queue[pixelY][pixelX][0];
                        Queue[pixelY][pixelX + 1][1] = pixelX;
                        Queue[pixelY][pixelX + 1][2] = pixelY;
                        Visited[pixelY][pixelX] = true;
                        checker(pixelX + 1, pixelY, tpixelX, tpixelY);
                    }
                   
                }
            }
            if (pixelY > 1) {//up
                if (nodos[pixelY - 1][pixelX]) {
                    if (1 + Queue[pixelY][pixelX][0] < Queue[pixelY - 1][pixelX][0]) {
                        Queue[pixelY - 1][pixelX][0] = 1 + Queue[pixelY][pixelX][0];
                        Queue[pixelY - 1][pixelX][1] = pixelX;
                        Queue[pixelY - 1][pixelX][2] = pixelY;
                        Visited[pixelY][pixelX] = true;
                        checker(pixelX, pixelY - 1, tpixelX, tpixelY);
                    }
                }
            }
            if (pixelY < 30 && nodos[pixelY][pixelX]) {//dn
                if (nodos[pixelY + 1][pixelX]) {
                    if (1 + Queue[pixelY][pixelX][0] < Queue[pixelY + 1][pixelX][0]) {
                        Queue[pixelY + 1][pixelX][0] = 1 + Queue[pixelY][pixelX][0];
                        Queue[pixelY + 1][pixelX][1] = pixelX;
                        Queue[pixelY + 1][pixelX][2] = pixelY;
                        Visited[pixelY][pixelX] = true;
                        checker(pixelX, pixelY + 1, tpixelX, tpixelY);
                    }
                }
            }
        }
    public void Backtracker ( int pixlX, int pixlY, int startX, int startY){
            int[] prevcoor = new int[2];
            prevcoor[0] = Queue[pixlY][pixlX][1];
            prevcoor[1] = Queue[pixlY][pixlX][2];
            if (prevcoor[0] == startX && prevcoor[1] == startY) {
                prevX = pixlX;
                prevY = pixlY;
            } else {
                Backtracker(prevcoor[0], prevcoor[1], startX, startY);
            }
        }
```
## Authors ✒️

_All the people that have helped to develop this game._

* **Josian Velez** - *Intitial work* - [Josian Velez](https://github.com/ElementalWizard)
* **Edwin Vega** - *Final work* - [Edwin vega](https://github.com/heckio)
* **Juan Fontanez** - *Final work & Documentation* - [Juan Fontanez](#fulanito-de-tal)

You can also look at the list of contributors at [contributors](https://github.com/uprm-ciic4010-s20/java-game-box-project-2-programandcontrolman/graphs/contributors) . 


FAQ
---

Q: The game launches but the map is bigger than my screen, is this intended behaviour?

A: Yes, this is the intended behavior. the game is designed to be played on 1920x1080 or higher with windows scaling at 100%, changing the map multiplier will probably break the game.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[UPRM](https://www.uprm.edu/cti/usu/license-and-software-agreements/)
