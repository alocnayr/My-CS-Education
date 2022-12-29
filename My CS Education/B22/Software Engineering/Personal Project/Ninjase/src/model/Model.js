
   export class Coordinate{
        constructor(x,y){
            this.x = x;
            this.y = y;
        }
    }

    export class MoveType {
    constructor(deltar, deltac){
        this.deltar = deltar;
        this.deltac = deltac;
    }

    static parse(s) {
        if ((s === "down")  || (s === "Down"))   { return Down; }
        if ((s === "up")    || (s === "Up"))     { return Up; }
        if ((s === "left")  || (s === "Left"))   { return Left; }
        if ((s === "right") || (s === "Right"))  { return Right; }
        
        return NoMove;
    }

    }

    export const Down = new MoveType(1, 0, "down");
    export const Up = new MoveType(-1, 0, "up");
    export const Left = new MoveType(0, -1, "left");
    export const Right = new MoveType(0, 1, "right");
    export const NoMove = new MoveType(0, 0, "*");  // no move is possible


    export class Cell {
        constructor(r, c) {
            this.row = r
            this.column = c
            this.isWall = false;
        }
    }   

    export class Puzzle {
        constructor(nr, nc, keys, doors, walls) {
            this.nr = nr
            this.nc = nc

            this.cells = []
            this.walls = walls // update
            this.doors = doors // update ...
            this.keys = keys // keys


            for (let r = 0; r < nr; r++) { 
                this.cells[r] = [];
                for (let c = 0; c < 5; c++) {
                    this.cells[r][c] = new Cell(r, c)
                }
            }
        }

    }

    export class Key{
        constructor(row,column, color){
            this.row=row;
            this.column = column;
            this.color = color;

        }

        place(row, column){
            this.row = row
            this.column = column
        }
    }

    export class Wall{
        constructor(row, column){
            this.row = row
            this.column = column
        }
    }

    export class Door{
        constructor(row, column, color){
            this.row=row;
            this.column = column;
            this.color = color;
        }
    }

    export class NinjaSE{
        constructor(row, column){
            this.row = row;
            this.column = column;
            this.key = null;
        }

        move(direction){
            this.row += direction.deltar;
            this.column += direction.deltac;
        }

    }


    // Model knows the level (you need 3). Knows the Puzzle
    export class Model {

        constructor(level) {
            this.level = level
            
            let nr = level.rows
            let nc = level.columns
            
            this.numMoves = 0;
            this.victory = false;
            this.ninjase = new NinjaSE(level.ninjase.row, level.ninjase.column);
            let theKeys = []
            let theDoors = []
            let theWalls = []
            this.currentKey = ""

            //methods
            this.isTheWall = isTheWall
            this.isDoor = isDoor
            this.pickUpTheKey = pickUpTheKey
            this.unlockTheDoor = unlockTheDoor

            for(let k of level.keys){
                let newKey = new Key(k.row, k.column, k.color)
                theKeys.push(newKey)

            }

            for(let d of level.doors){
                let newDoor = new Door(d.row, d.column, d.color)
                theDoors.push(newDoor)

            }

            for(let w of level.walls){
                let newWall = new Wall(w.row, w.column)
                theWalls.push(newWall)

            }

            this.puzzle = new Puzzle(nr, nc, theKeys, theDoors, theWalls)

           function isTheWall(x,y){
            for(let w=0; w<theWalls.length; w++){
                if(theWalls[w].row === x && theWalls[w].column === y){
                    return true
                }
            }
            return false
           }

           function isDoor(x,y){
            for(let d=0; d<theDoors.length; d++){
                if(theDoors[d].row === x && theDoors[d].column === y){
                    return true
                }
            }
            return false
           }

           function pickUpTheKey(x,y){
            for(let k=0; k<theKeys.length; k++){
                if(theKeys[k].row === x && theKeys[k].column === y){
                    if(this.ninjase.key) {
                        
                        // drop this key and pick up new one
                        this.ninjase.key.row = this.ninjase.row
                        this.ninjase.key.column = this.ninjase.column
                        
                    }
                    this.ninjase.key = theKeys[k]
                    this.currentKey = this.ninjase.key.color
                    this.ninjase.key.row  = -1
                    this.ninjase.key.column = -1
                    break
                }
                
            }
        }

        function unlockTheDoor(x,y){
            for(let d=0; d<theDoors.length; d++){
                if(theDoors[d].row === x && theDoors[d].column === y && 
                    (this.ninjase.key.color === theDoors[d].color)){
                        theDoors.splice(d, 1)
                        this.ninjase.key = null
                        this.currentKey = ""
                        return
                    }
            }
            return
           }
           
           /*
           function pickUpTheKey(x,y){
            for(let k=0; k<theKeys.length; k++){
                if(theKeys[k].row === x && theKeys[k].column === y){
                    if(this.ninjase.keyInventory.length === 0){
                        this.ninjase.keyInventory.push(theKeys[k]);
                        console.log("added key")
                        this.currentKey = this.ninjase.keyInventory[0].color
                        theKeys.splice(k, 1)
                    }
                    else{
                        console.log("inventory already contained key")
                        //add new key (periodically going to carry 2 keys)
                        this.ninjase.keyInventory.push(theKeys[k]);
                        let oldKeyInfo = this.ninjase.keyInventory[0];
                        this.currentKey = this.ninjase.keyInventory[1].color;
                        //remove new key from cell
                        //add old key from cell
                        //get rid of old key from inventory (which should be 0th index)
                        let newKey = new Key(this.ninjase.row, this.ninjase.column, oldKeyInfo.color)
                        theKeys.push(newKey)
                        this.ninjase.keyInventory.splice(0, 1);
                        theKeys.splice(k, 1);
                    }
                    console.log("done adding key")
                }
                console.log("iterating")
            }
            console.log("about to return")
            return
           }
           */
           

        /*
           function unlockTheDoor(x,y){
            for(let d=0; d<theDoors.length; d++){
                if(theDoors[d].row === x && theDoors[d].column === y && 
                    (this.ninjase.keyInventory[0].color === theDoors[d].color)){
                        theDoors.splice(d, 1)
                        this.ninjase.keyInventory.splice(0, 1)
                        this.currentKey = ""
                        return
                    }
            }
            return
           }

           */
           



            // do the hard work to identify walls doors kjeys from the level and TELL this.puzzle about them

        }
        
        outOfBounds(x,y){

            return x < 0 || x >= this.level.rows || y >= this.level.columns || y < 0;
        
        }

        
        isWall(x,y){
            return this.isTheWall(x,y)
            
        }

        isDoor(x,y){
            return this.isDoor(x,y)
        }

        isKey(x,y){
            return this.isKey(x,y)
        }

        pickUpKey(){
            let x = this.ninjase.row
            let y = this.ninjase.column
            this.pickUpTheKey(x,y)
            console.log("pick up key returns")
            return

                    //add key to ninjase inventory
                    //array.splice(idx, 1) from theKeys
                    //add it back to keys if you're trying to pick up a new one
             
        }
        
        
        unlockDoor(x,y){
            this.unlockTheDoor(x,y)
            return
            }
            

        updateMoveCount(delta){
            this.numMoves += delta;
        }

        numberOfMoves(){
            return this.numMoves;
        }
    }
