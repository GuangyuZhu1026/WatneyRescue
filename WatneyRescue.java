import java.util.Scanner;

public class WatneyRescue {

	public static void main(String[] args) {

		//Creates a new instance object. Don't worry about this for now.
		WatneyRescue wr = new WatneyRescue();

		//A new scanner to read input from the user
		Scanner s = new Scanner(System.in);

		//The user's menu selection; 'd', 'r', 'q' or something else 
		char inputChoice;

		do  {
			System.out.println("Do you want 'd' distance mode, 'r' rover mode, or 'q' quit?");
			inputChoice = s.nextLine().charAt(0);

			switch(inputChoice) {

			//Enter distance mode; make the table then compute walking time based on the table
			case 'd': 
				System.out.println("How many meters to Watney?");
				String line = s.nextLine(); 
				double metersToWatney = Double.parseDouble(line.trim());
				String table = wr.generateContingency(metersToWatney);
				System.out.println(table);
				System.out.println("Which row?");
				line = s.nextLine();
				int row = Integer.parseInt(line.trim());
				System.out.println(wr.computeWalkingTime(metersToWatney,table,row));	
				break;

			//Enter rover mode; receive and translate a series of angles for the rover
			case 'r':
				System.out.println("What series of angles?");
				String angles = s.nextLine();
				System.out.println("Mission control on earth sends the following message:\n");
				System.out.println(wr.interpretHexadecimal(angles) + "\n");
				break;

			//Quit
			case'q': 
				continue;

			//Ignore anything that isn't 'd', 'r', or 'q'
			default: 
				continue;
			}
			
		} while (inputChoice != 'q'); //keep going until we get 'q'

		System.out.println("Goodbye. May the hexadecimals be with you.");
		
		//Close the scanner to keep eclipse from driving me crazy with its warnings
		s.close();
	}

	/**
	 * Generates a contingency table of distances that Watney
	 * will have to travel to the ship
	 * @param distLewisWatney The distance in meters from Lewis to Watney, 
	 * as read from Lewis' suit sensors
	 * @return
	 */
	public String generateContingency(double distLewisWatney) {
		String result = "";
		result += "Contingencies given distance " + distLewisWatney + " from Lewis to Watney:\n";

		//TODO: Compute the table values for Lewis angle from north ranging every five degrees 
		//from 15 to 75 inclusive, and for her distance to the hab for every three meters from 15
		//to 30 inclusive. Solve each row for Watney's distance to the hab. Round everything
		//to the nearest meter. 
		int k=1;
		for (int degree=15; degree <=75; degree +=5){
			for (int c=15; c <=30; c +=3){
				result +=(k + ")");
				result +=(" " + degree + " " + "|");
				result +=(" " + c + " " + "|");
				result +=(" " + Math.round(Math.sqrt(Math.pow(distLewisWatney,2)+Math.pow(c,2)-2*distLewisWatney*c*Math.cos(Math.toRadians(90-degree)))));
				result += "\n";
				k++;
			}
		}
		
		return result;
	}

	/**
	 * This method computes how long it will take Watney and Lewis to walk to the hab
	 * given their distances from it. The necessary distances must be looked up within
	 * the table provided as a parameter. 
	 * @param distLewisWatney The distance Lewis is from Watney, as measured by her suit
	 * @param table The table of angle and distance values generated from another method
	 * @param row The row of the table that should be used in the calculation
	 * @return
	 */
	public String computeWalkingTime (double distLewisWatney, String table, int row) {
		
		//TODO: Traverse the table to get the correct row's string. (Hint: look at the 
		//starter code for the interpretHexadecimal method to see a cool trick for stepping
		//through a String and breaking it apart using a certain character.) 
		String mySplitResult = table.split("\n")[row];
		
		//TODO: Extract from that row the needed distance. 
		Scanner s = new Scanner(mySplitResult).useDelimiter("[^0-9]+");
		s.nextInt();
		s.nextInt();
		int lewisToHab = s.nextInt();
		int watneyToHab = s.nextInt();
		
		//TODO: Compute the walking time for Watney and Lewis to the ship. 
		//Watney is injured so his walking pace is 0.9 meters per second. 
		//Lewis' walking pace is 1.7 meters per second.
		int watneyWalkingTime = (int)Math.round(watneyToHab/0.9);
		int lewisWalkingTime = (int)Math.round(lewisToHab/1.7);
		
		s.close();
		
		return "Watney walking time to ship: " + watneyWalkingTime + " seconds" + "\n" +
		       "Lewis walking time to ship: " + lewisWalkingTime + " seconds" + "\n";

	}

	/**
	 * This method interprets a series of angles around a circle to find the hexadecimal
	 * digit they "point to" on the circle. Then, it maps those hexadecimal pairs to characters from the
	 * English alphabet. The 360 degrees of the circle are broken into 16 equal pieces of 22.5 degrees each. 
	 * @param angles The comma-delimited list of angles
	 * @return a String with the message in English
	 */
	public String interpretHexadecimal(String angles) {
		//An easy way to break apart a list of things is to make a Scanner on it
		//This Scanner is unrelated to any other variables with the same name because this
		//one is only meaningful inside this method. Also, this scanner does not read from the
		//console! It only operates over the one String you created it on, namely angles. 
		Scanner s = new Scanner(angles);
		//You can tell each Scanner what you want it to use to break up the String. 
		//Making it a comma, when you call next() you get whatever is between the commas.
		//Pretty convenient. 
		s.useDelimiter(",");
		
		//TODO: Complete this method :) 
		String hexNumber1="";
		String hexNumber2="";
		String hexNumber="";
		String result="";
		int k = 0;
		int i = 0;
		
		while (s.hasNext()){
			if (i==0){
				hexNumber1=Integer.toHexString((int)Math.floor(Double.parseDouble(s.next())/22.5));
			}
			else if(i==1){
				hexNumber2=Integer.toHexString((int)Math.floor(Double.parseDouble(s.next())/22.5));
				hexNumber=hexNumber1+hexNumber2;
				k = Integer.parseInt(hexNumber,16);
				char a = (char)k;
				result += a;
				i=0;
				continue;
			}
			i++;
		}
		return result;
		
	}
}

