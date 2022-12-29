import React from 'react';
import { level1, level2, level3 } from './model/Levels.js'
import { moveUp, redrawCanvas} from './boundary/Boundary.js'
import { Model } from './model/Model.js'
import { moveNinjase } from './controller/Controller.js';
import { Up, Down, Left, Right } from './model/Model.js';

function App() {


var gameLevel = level1

  const [model, setModel] = React.useState(new Model(gameLevel));
  const [redraw, forceRedraw] = React.useState(0);       // used to conveniently request redraw after model change
  const canvasRef = React.useRef(null);   // need to be able to refer to Canvas

  /** Ensures initial rendering is performed, and that whenever model changes, it is re-rendered. */
  React.useEffect (() => {

    redrawCanvas(model, canvasRef.current)
    
  }, [model, redraw])   // arguments that determine when to refresh
  
  const moveNinjaseHandler = (direction) => {
    moveNinjase(model, direction);

    if(model.puzzle.doors.length === 0){
      // alert("you have beaten this level!")
      model.victory = true
    }

    forceRedraw(redraw+1);
  }

  const level1ClickHandler = (level1) => {
    gameLevel = level1
    let newModel = new Model(gameLevel)
    setModel(newModel)
  }

  const level2ClickHandler = (level2) => {
    gameLevel = level2
    let newModel = new Model(gameLevel)
    setModel(newModel)
  }

  const level3ClickHandler = (level3) => {
    gameLevel = level3
    let newModel = new Model(gameLevel)
    setModel(newModel)
  }

  const resetGameHandler = () => {
    let currLevel = model.level
    setModel(new Model(currLevel))
  }

  const pickUpKeyHandler = () => {
    model.pickUpKey();
    forceRedraw(redraw+1)
  }


  return (
    <main style = {appmain}>
      <canvas tabIndex="1"  
        className="App-canvas"
        ref={canvasRef}
        width  = "800"
        height = "800" 
         />
      
      <label style = {numMovesText}> {"number moves: " + model.numMoves}
    </label>
    <label style = {currentKeyText}> {"Current Key: " + model.currentKey}
    </label>
    {model.victory ? <label style = {victoryText}> {"You've Beaten The Level!!!"}</label> : null}

      <div>
      <button style = {upbutton} onClick = {(e) => moveNinjaseHandler(Up)}>^</button>
      <button style  = {leftbutton} onClick = {(e) => moveNinjaseHandler(Left)}>&lt;</button>
      <button style  = {rightbutton} onClick = {(e) => moveNinjaseHandler(Right)}>&gt;</button>
      <button style  = {downbutton} onClick = {(e) => moveNinjaseHandler(Down)}>v</button>
      <button style = {resetbutton} onClick = {(e) => resetGameHandler()}>reset</button>
      <button style = {level1button} onClick = {(e) => level1ClickHandler(level1)}>level 1</button>
      <button style = {level2button} onClick = {(e) => level2ClickHandler(level2)}>level 2</button>
      <button style = {level3button} onClick = {(e) => level3ClickHandler(level3)}>level 3</button>
      <button style = {pickupkeybutton} onClick = {(e) => pickUpKeyHandler()}>pick up key</button>

    </div>

    </main>
  );
}

// you might try this quick and dirty way to position buttons where you want (and other elements)
const appmain = {
  backgroundColor: "#00FFFF"
}

const upbutton = {
  position: "absolute",
  left: 615,
  top: 80,
}

const leftbutton = {
  position: "absolute",
  left: 575,
  top: 100,
}

const rightbutton = {
  position: "absolute",
  top: 100,
  left: 650,
}

const downbutton = {
  position: "absolute",
  left : 615,
  top : 120,
}

const numMovesText = {
    position: "absolute",
    left: 600,
    top: 10,
    color: "black",
    //backgroundColor: "#e8ccd7",
    width:80,

}

const currentKeyText = {
  position: "absolute",
  left: 730,
  top: 10,
  color: "black",
  //backgroundColor: "#e8ccd7",
  width:80,

}

const resetbutton = {
  position: "absolute",
  left : 605,
  top : 250,
}

const level1button = {
  position: "absolute",
  left : 500,
  top : 200,
}
const level2button = {
  position: "absolute",
  left : 600,
  top : 200,
}
const level3button = {
  position: "absolute",
  left : 700,
  top : 200,
}
const pickupkeybutton = {
  position: "absolute",
  left : 700,
  top : 100,
}

const victoryText = {
  position: "absolute",
  left: 100,
  top: 400,
  fontSize: "100px",
  fontWeight: "bold",
  color: "red",
  //backgroundColor: "#e8ccd7",
  width:2000,
}

export default App;
