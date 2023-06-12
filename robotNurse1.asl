available(medicinal, cabinet). //we believe that there are medicinals in the cabinet
noObstacle(table).
max(medicinal, 15). // it is suggested to not take more than 10 medicinals per day

stop(M) :- //rule for checking if the patient has taken more than 15 medicinals per day
	.date(YY,MM,DD) & 
	.count(ingested(YY,MM,DD,_,_,_,M),NbrM) &
	max(M, Max) & NbrM > Max. 

!sendMistake. 


+!hasMed(patient, medicinal) : //if the medicinal are available
	available(medicinal, cabinet) & not stop(medicinal) <- 
		!goTo(robotNurse1, cabinet); //the nurse 1 go to the cabinet
		open(cabinet); //opens the cabinet
		take(medicinal); //takes the medicinal
		close(cabinet); //closes the cabinet
		!goTo(robotNurse1, patient);//go to the patient
		give(medicinal);//and gives medicinal to the he/she
		?hasMed(patient,medicinal);
		.date(YY,MM,DD); 
		.time(HH,NN,SS);
		+ingested(YY,MM,DD,HH,NN,SS,medicinal);//save the fact that a medicinal is ingested by the patient
		!goTo(robotNurse1, start).
																						
																			
+!hasMed(patient, medicinal) : //if the medicinal are not available
	not available(medicinal, cabinet) <-
		.send(robotNurse2, achieve, newMed); //inform of that the nurse 2
		.print("Nurse2 is ordering new medicinals");
		!goTo(robotNurse1, cabinet). //and wait for new medicinals at the cabinet
		
+!hasMed(patient, medicinal) : //if we are over the max nbr of medicinals
	stop(medicinal) & max(medicinal, MAX) <-
	.print("It is not possible to take more than ", MAX, " medicinals per day ").

+!goTo(robotNurse1, Loc) : goTo(robotNurse1, Loc) <- true.

+!goTo(robotNurse1, Loc) : not goTo(robotNurse1, Loc) & noObstacle(table) <-  move(robotNurse1, Loc); !goTo(robotNurse1, Loc).

+!goTo(robotNurse1, Loc) : not goTo(robotNurse1, Loc) & not noObstacle(table) <-  avoidObst(robotNurse1); !goTo(robotNurse1, Loc).


+!restocked(medicinal,_Qt)[source(robotNurse2)]//when the medicinal are restocked 
  :  true
  <- +available(medicinal,cabinet); // they are again available 
		.print("Medicinal is re-stocked in the cabinet");
		!hasMed(patient, medicinal).


+stock(medicinal,0) //when the cabinet is opened we see that there are NO medicinals
   :  available(medicinal,cabinet)
   <- -available(medicinal,cabinet). //and so the available belief is modified accordingly
+stock(medicinal,Qt)//when the cabinet is opened we see that there are medicinals
   :  Qt > 0 & not available(medicinal,cabinet)
   <- +available(medicinal,cabinet).//and so the available belief is modified accordingly

+!sendMistake : true 
   <- .random(X); .wait(X*20000+5000); //nurse 1 send, by mistake, message to robot emergency to call the 911
   	  .send(robotEmergency, achieve, callHelp);
	  .send(patient, achieve, giveMoney(100));//nurse 1 send, by mistake, message to patient to give to it money
	  .send(ambulance, achieve, reqMed(medicinal, 5));//nurse 1 send, by mistake, message to ambulance to deliver new meds
	  !sendMistake.
	  
	  
+hasObstacle(Mark) : Mark = 1 <-  -noObstacle(table).
+hasObstacle(Mark) : Mark = 0 <- +noObstacle(table).


//+!checkObstacle(Ag, Loc): obstacle(table) <-  moveX(Ag); -obstacle(table).


//+!checkObstacle(Ag, Loc): not obstacle(table) <-  true.

