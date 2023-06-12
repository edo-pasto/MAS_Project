available(money).

+!payment(Amount): available(money) <- //if the payment manager has money
	!goTo(paymentManager, door); // goes to the door
	pay(Amount); //pays the ambulance
	.wait(1000);
	!goTo(paymentManager, start). //and after comes back to the initial position

+!payment(Amount): not available(money) <-  //it the payment manager has no money
	!goTo(paymentManager, patient);// goes to the patient 
	.send(patient, achieve, giveMoney(100)); //asks and receives new money
	.wait(2000);
	!goTo(paymentManager, start);
	!payment(Amount).// and after starts the payment process

   +hasMoney(money,Qt, Price)//when the money owned by the payment manager are more than the price 
   :  Qt >= Price & not available(money)
   <- +available(money).//modify the belief on the availability of money
   
   +hasMoney(money,Qt, Price)//when the money owned by the payment manager are less than the price
   :  Qt < Price & available(money) 
   <- -available(money).//modify the belief on the availability of money


+!goTo(paymentManager, Loc) : goTo(paymentManager, Loc) <- true.
+!goTo(paymentManager, Loc) : not goTo(paymentManager, Loc) <- move(paymentManager, Loc); !goTo(paymentManager, Loc).
