!want(medicinal).

!check_health.

trusted(paymentManager).


+!want(medicinal): true <- .send(robotNurse1, achieve, hasMed(patient, medicinal)). //patient ask the nurse 1 for medicinals

+hasMed(patient, medicinal) : true <- !take(medicinal). //when the patient has some medicinals can take them

-hasMed(patient, medicinal) : true <-  .wait(2000); !want(medicinal). //when the patient doesn't have some medicinals he asks for them

+!take(medicinal) : hasMed(patient, medicinal) <- sipWater; //if there are medicinals the patient sip water for taking them
							!take(medicinal).
							
+!take(medicinal) : not hasMed(patient, medicinal) <- true. //if there aren't medicinals the patiente do nothing

+!giveMoney(Amount)[source(S)] : trusted(S) <- giveMoney(Amount).

+!giveMoney(Amount)[source(S)] : not trusted(S) <-  .print(S, "is not the payment manager, I cannot give money to it!").

	  
+!check_health : true <- .random(X); .wait(X*20000+5000);  // patient calls emergency randomly
   	  .send(robotEmergency, achieve, callHelp); //patient asks the emergency robot to call help
	  !check_health. 
