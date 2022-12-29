package cs2102hw2;

class Shark {
    int length;
    double salinity;
    int attacks;

    Shark (int length, int attacks) {
      this.length = length;
      this.salinity = 3.75;
      this.attacks = attacks;
    }

    public boolean isNormalSize () {
      return 1 <= this.length && this.length <= 15;
    }
    
    public boolean isDangerToPeople() {
    	if (this.attacks >= 1){
    		return true;}
    	else {
    		return false;
    	}
    }
    
  }