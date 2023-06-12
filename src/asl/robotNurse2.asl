!sendMistake.

+!newMed[source(Ag)] : true <-//request medicinal to ambulance
	.send(ambulance, achieve, reqMed(medicinal, 5));
	!goTo(robotNurse2, door). //move to the door, where the medicinals should arrive
	

+!delivered(medicinal, Qt)[source(ambulance)] : true 
	 <- 
		.print("The medicinals are arrived");
		 !goTo(robotNurse2,cabinet); // return to the cabinet
		 .print("i'm coming back at the cabinet");
		 .send(robotNurse1, achieve, restocked(Product,Qt)); //inform the nurse 1 that the medicinals are restocked
		 !goTo(robotNurse2, start).//go back to the starting point

+!goTo(robotNurse2, Loc) : goTo(robotNurse2,Loc) <- true.
+!goTo(robotNurse2, Loc) : not goTo(robotNurse2,Loc)
    <- move(robotNurse2, Loc);
       !goTo(robotNurse2, Loc).
	   

+!sendMistake : true 
   <- .random(X); .wait(X*15000+4000);  //nurse 2 send, by mistake, message to robot emergency to call the 911
   	  .send(robotEmergency, achieve, callHelp);
	  .send(patient, achieve, giveMoney(100));//nurse 2 send, by mistake, message to patient to give to it money
	  !sendMistake.                                                                                                                                                                         
