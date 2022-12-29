

export function moveNinjase(model, direction){

    var rowPosition = model.ninjase.row + direction.deltar;
    var columnPosition = model.ninjase.column + direction.deltac;

    model.unlockDoor(rowPosition, columnPosition)
    
   if((!model.outOfBounds(rowPosition, columnPosition)) && !model.isWall(rowPosition, columnPosition)
   && !model.isDoor(rowPosition, columnPosition)){

    //may or may not unlock door
    model.ninjase.move(direction);
    model.updateMoveCount(+1);
    }

    else{
        return
    }


}

