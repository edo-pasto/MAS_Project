import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;
import java.util.Random;
import java.util.*;
import java.util.Arrays;
import java.util.List;

public class RoomModel extends GridWorldModel {


    public static final int CABINET = 16;
    public static final int PATIENT  = 32;
    public static final int DOOR  = 64;
	public static final int PHONE  = 128;
    public static final int GridSize = 12;

	// Initialization of useful variable
    boolean isCabinetOpen = false; 
    boolean isGiveMed = false;
	boolean hasMoney = false;
    int sipCount  = 0; 
    int availableMeds = 2;
	String call = " ";
	String untrust = " ";
	int money = 100;
	int price  = 0;
	boolean hasObstacle = false;
	int mark = 0;
	
	//Initialization of the location of the agents and objects
    Location locCabinet = new Location(0,0);
    Location locPatient  = new Location(GridSize-1,GridSize-1);
    Location locDoor  = new Location(0,GridSize-1);
	Location locPhone  = new Location(GridSize-1,GridSize/2);
	Location locNurse1 = new Location(GridSize/2,GridSize/2);
	Location locRobotEmerg = new Location(GridSize-1, 0);
    Location locNurse2 = new Location(GridSize/2,0);
	Location locPayManager = new Location(GridSize/2,GridSize-1);
	Location table = new Location(7,7);
	//Location couch = new Location(5,1);

    public RoomModel() {
        super(GridSize, GridSize, 4);
        // Nurse 1
        setAgPos(0, locNurse1);
        // Robot Emergency
        setAgPos(1, locRobotEmerg);
        // Nurse 2
        setAgPos(2, locNurse2);
		// Payment Manager
		setAgPos(3, locPayManager);

        add(CABINET, locCabinet);
        add(PATIENT, locPatient);
        add(DOOR, locDoor);
		add(PHONE, locPhone);
		
    }


    boolean openCabinet() {
        if (!isCabinetOpen) {
            isCabinetOpen = true;
            return true;
        } else {
            return false;
        }
    }

    boolean closeCabinet() {
        if (isCabinetOpen) {
            isCabinetOpen = false;
            return true;
        } else {
            return false;
        }
    }

    boolean moveNurse1(Location dest) {  
        Location position = getAgPos(0);
		//hasObstacle = false;
		Location pos = new Location (table.x -1, table.y -1);
		Location pos2 = new Location (table.x +1, table.y +1);
		
		System.out.println(position.x);
		System.out.println(position.y);
	//position.equals(table)
		if( position.equals(pos) || position.equals(pos2) ){
			mark = 1;
			hasObstacle = true;
			return true;
			
		}
		 if (position.x < dest.x)        position.x++;
        else if (position.x > dest.x)   position.x--;
        if (position.y < dest.y)        position.y++;
        else if (position.y > dest.y)   position.y--;
		
        setAgPos(0, position); 
        if (view != null) {
            view.update(locCabinet.x,locCabinet.y);
            view.update(locPatient.x,locPatient.y);
        }
        return true;
    }
	boolean moveNurse2(Location dest) {
		
        Location position = getAgPos(2);
        if (position.x < dest.x)        position.x++;
        else if (position.x > dest.x)   position.x--;
        if (position.y < dest.y)        position.y++;
        else if (position.y > dest.y)   position.y--;

        setAgPos(2, position); 

        if (view != null) {
            view.update(locCabinet.x,locCabinet.y);
            view.update(locDoor.x,locDoor.y);
        }
        return true;
    }
	
	boolean avoidObst(Location dest){
		Location position = getAgPos(0);
        position.x = position.x - 2;
        setAgPos(0, position); 
		mark = 0;
		hasObstacle = false;

        return true;
	}
	
	boolean moveEmerg(Location dest) {
        Location position = getAgPos(1);
        if (position.x < dest.x)        position.x++;
        else if (position.x > dest.x)   position.x--;
        if (position.y < dest.y)        position.y++;
        else if (position.y > dest.y)   position.y--;
        setAgPos(1, position); 

        if (view != null) {
			view.update(locPhone.x,locPhone.y);
        }
        return true;
    }
	
	boolean movePayManager(Location dest) {
        Location position = getAgPos(3);
        if (position.x < dest.x)        position.x++;
        else if (position.x > dest.x)   position.x--;
        if (position.y < dest.y)        position.y++;
        else if (position.y > dest.y)   position.y--;
        setAgPos(3, position); 

        if (view != null) {
			view.update(locPatient.x,locPatient.y);			
        }
        return true;
    }



    boolean takeMedicinal() {
        Random rn = new Random();
        if (isCabinetOpen && availableMeds > 0 && !isGiveMed) {
            availableMeds--;
            isGiveMed = true;
            if (view != null)
                view.update(locCabinet.x,locCabinet.y);
            return true;
        } else {
            return false;
        }
    }

    boolean addMedicinal(int n) {
        availableMeds += n;
        if (view != null)
            view.update(locCabinet.x,locCabinet.y);
        return true;
    }

    boolean giveMedicinal() {
        if (isGiveMed) {
            sipCount = 10;
            isGiveMed = false;
            if (view != null)
                view.update(locPatient.x,locPatient.y);
            return true;
        } else {
            return false;
        }
    }
	
	boolean pay(int amount) {

        money -= amount;
		price = amount;
		hasMoney = true;
        if (view != null)
            view.update(locPayManager.x,locPayManager.y);
        return true;
    }
	
	boolean giveMoney(int amount){
		money += amount;
		hasMoney  = true;
		if (view != null)
            view.update(locPayManager.x,locPayManager.y);
        return true;	
	}
    boolean sipWater() {
        if (sipCount > 0) {
            sipCount--;
            if (view != null)
                view.update(locPatient.x,locPatient.y);
            return true;
        } else {
            return false;
        }
    }
	boolean callEmerg(){
		call = "Calling 911";
		return true;
	}
	boolean endCallEmerg(){
		call = " ";
		return true;
	}
	boolean untrustAg(){
		untrust = "AG untrusted";
		return true;
	}
	boolean endUntrustAg(){
		untrust = " ";
		return true;
	}
}
