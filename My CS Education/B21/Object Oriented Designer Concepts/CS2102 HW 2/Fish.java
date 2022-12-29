package cs2102hw2;

	 class Fish {
		    int length;
		    double salinity; 

		    Fish (int length, double salinity) {
		      this.length = length;
		      this.salinity = salinity;
		    }

		    public boolean isNormalSize () {
		      return 1 <= this.length && this.length <= 15;
		    }
		    
		    
		    
		  }