

var BOXSIZE = 100;
const OFFSET = 8;

const ninjaseImage = new Image()
ninjaseImage.src = "data:image/png;base64,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"

export class Square {
    constructor(x, y, size) {
      this.x = x
      this.y = y
      this.size = size
    }
}
export function computeSquare(cell) {
   // let c = cell.copy()
    return new Square(BOXSIZE*cell.column + OFFSET, BOXSIZE*cell.row + OFFSET, BOXSIZE - 2*OFFSET, BOXSIZE-2*OFFSET)
}

export function redrawCanvas(model, canvasObj) {
    const ctx = canvasObj.getContext('2d');
    ctx.clearRect( 0,0, canvasObj.width, canvasObj.height);  
   
    // showing the outermost information
    let nr = model.puzzle.nr
    let nc = model.puzzle.nc

 

    for (let r = 0; r < nr; r++) {
        for (let c = 0; c < nc; c++) {
            let cell = model.puzzle.cells[r][c]

            //you don't need class wall, all you need to know is that
            //you can't move to a wall
            let sq = computeSquare(cell)


            //just do model.level.walls when you get the button functionality
            for(let w of model.puzzle.walls){
            if(w.row === r && w.column === c)
            {

                ctx.fillStyle = 'black'
                ctx.fillRect(sq.x, sq.y, sq.size, sq.size)
                
            }
        }

            for(let k of model.puzzle.keys){
            if(k.row === r && k.column === c){

                let color = k.color
                ctx.fillStyle = color
                ctx.fillRect(sq.x+30, sq.y+30, sq.size-60, sq.size-60)

            }
        }


            for(let d of model.puzzle.doors){
            if(d.row === r && d.column === c){

                ctx.fillStyle = 'black'
                ctx.fillRect(sq.x, sq.y, sq.size, sq.size)
                let color = d.color
                ctx.fillStyle = color
                ctx.fillRect(sq.x+5, sq.y+5, sq.size-10, sq.size-10)
                ctx.fillStyle = 'white'
                ctx.fillRect(sq.x+30, sq.y+30, sq.size-60, sq.size-60)
                

            }
        }

            if(model.ninjase.row === r && model.ninjase.column === c){
                
                ctx.drawImage(ninjaseImage, sq.x, sq.y, sq.size, sq.size)

                // ctx.fillStyle = 'purple'
                // ctx.beginPath()
                // ctx.rect(sq.x, sq.y, sq.size, sq.size)
                // ctx.stroke()
                // ctx.fillRect(sq.x+5, sq.y+5, sq.size-10, sq.size-10)
                
            }
            
            else{
            ctx.beginPath()
            ctx.rect(sq.x, sq.y, sq.size, sq.size)
            ctx.stroke()
            }
        
        
        
            
            }
        }
    }   