
trusted(robotNurse2).

+!reqMed(medicinal,Qt)[source(S)] : trusted(S) //deliver medicinals when it is requested from trusted source
  <- 
     deliver(medicinal,Qt);
     .send(S, achieve, delivered(medicinal,Qt));
	 .send(paymentManager, achieve, payment(100)).
	 
	 
+!reqMed(medicinal,Qt)[source(S)] : not trusted(S) //print error message when the request comes from untrusted source
  <- 
     .print(S, " is not the Nurse2 agent, I cannot obey you!").

