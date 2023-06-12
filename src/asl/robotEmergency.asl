
trusted(patient).

+!callHelp[source(S)]: trusted(S) <- //if it receives the message from the patient
									!goTo(robotEmergency, phone);// it will go to the phone 
									.print("Ok, I'm calling the 911!");
									call; //and it will call the 911
									.wait(2000);
									endCall;
									!goTo(robotEmergency, start).// it comes back to the initial position
									
									
+!callHelp[source(S)]: not trusted(S) <- 
									.print(S, " is not the patient i cannot trust it!");//if it receives the message from the others agents, it won't call the 911
									untrust;
									.wait(1000);
									untrustEnd.
										
						
+!goTo(robotEmergency,Loc) : goTo(robotEmergency, Loc) <- true.
+!goTo(robotEmergency,Loc) : not goTo(robotEmergency, Loc)
    <- move(robotEmergency, Loc);
       !goTo(robotEmergency, Loc).
